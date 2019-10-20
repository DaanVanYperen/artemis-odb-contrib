package net.mostlyoriginal.api.utils;

import com.artemis.utils.Bag;
import com.artemis.utils.IntBag;
import net.mostlyoriginal.api.utils.pooling.ObjectPool;
import net.mostlyoriginal.api.utils.pooling.Poolable;
import net.mostlyoriginal.api.utils.pooling.Pools;

/**
 * Quad tree for optimized queries in 2d space
 *
 * @author Piotr-J
 */
public class QuadTree implements Poolable {

    private static final ObjectPool<QuadTree> qtPool = Pools.getPool(QuadTree.class);
    private static final ObjectPool<Container> cPool = Pools.getPool(Container.class);

    public final static int OUTSIDE = -1;
    public final static int SW = 0;
    public final static int SE = 1;
    public final static int NW = 2;
    public final static int NE = 3;
    protected int depth;
    private Bag<Container> idToContainer;
    protected Bag<Container> containers;
    protected Container bounds;
    protected QuadTree[] nodes;
    protected QuadTree parent;
    protected long nextFlag = 1L;

    /**
     * Max count of containers in a tree before it is split
     * <p>
     * Should be tweaked for best performance
     */
    protected int maxInBucket;

    /**
     * Max count of splits, tree start at depth = 0
     * <p>
     * Should be tweaked for best performance
     */
    protected int maxDepth;

    /**
     * Public constructor for {@link ObjectPool} use only
     */
    public QuadTree() {
        this(0, 0, 0, 0);
    }

    /**
     * Public constructor for initial {@link QuadTree}
     * <p>
     * Specify max tree bounds
     */
    public QuadTree(float x, float y, float width, float height) {
        this(x, y, width, height, 16, 8);
    }

    /**
     * Public constructor for initial {@link QuadTree}
     * <p>
     * Specify max tree bounds, bucket capacity and max depth
     */
    public QuadTree(float x, float y, float width, float height, int capacity, int depth) {
        bounds = new Container();
        maxInBucket = capacity;
        maxDepth = depth;
        containers = new Bag<>(maxInBucket);
        nodes = new QuadTree[4];
        init(0, x, y, width, height, null);
    }

    protected QuadTree init(int depth, float x, float y, float width, float height, QuadTree parent) {
        this.depth = depth;
        bounds.set(x, y, width, height);
        this.parent = parent;
        this.idToContainer = parent != null ? parent.idToContainer : new Bag<>();

        return this;
    }

    private int indexOf(float x, float y, float width, float height) {
        float midX = bounds.x + bounds.width / 2;
        float midY = bounds.y + bounds.height / 2;
        boolean top = y > midY;
        boolean bottom = y < midY && y + height < midY;
        if (x < midX && x + width < midX) {
            if (top) {
                return NW;
            } else if (bottom) {
                return SW;
            }
        } else if (x > midX) {
            if (top) {
                return NE;
            } else if (bottom) {
                return SE;
            }
        }
        return OUTSIDE;
    }

    /**
     * Returns a unique flag for inserting and querying this quad tree.
     * 
     * <p>Using this flag allows for querying only for entities inserted with the same flag set.</p>
     */
    public long nextFlag() {
        long flag = nextFlag;
        nextFlag = nextFlag << 1;

        return flag;
    }

    /**
     * Upserts the given entity id to the tree with the bounds, updating its position if it's already been added, inserting it otherwise.
     * 
     * <p>Use {@link #update(int, float, float, float, float)} instead if you know the entity is already contained.</p>
     */
    public void upsert(int eid, float x, float y, float width, float height) {
        upsert(eid, 0, x, y, width, height);
    }

    /**
     * Upserts the given entity id to the tree with the bounds, updating its flags and position if it's already been added, inserting it otherwise.
     * 
     * <p>Use {@link #update(int, float, float, float, float)} instead if you know the entity is already contained and would use {@code 0} for {@code flags}.</p>
     */
    public void upsert(int eid, long flags, float x, float y, float width, float height) {
        Container c = eid < idToContainer.size() ? idToContainer.get(eid) : null;
        if (c != null) {
            c.flags |= flags;
            update(eid, x, y, width, height);
            return;
        }

        insert(eid, flags, x, y, width, height);
    }

    /**
     * Inserts given entity id to tree with given bounds
     */
    public void insert(int eid, float x, float y, float width, float height) {
        insert(eid, 0, x, y, width, height);
    }

    /**
     * Inserts given entity id to tree with given bounds
     */
    public void insert(int eid, long flags, float x, float y, float width, float height) {
        insert(cPool.obtain().set(eid, flags, x, y, width, height));
    }

    protected void insert(Container c) {
        if (nodes[0] != null) {
            int index = indexOf(c.x, c.y, c.width, c.height);
            if (index != OUTSIDE) {
                nodes[index].insert(c);
                return;
            }
        }
        c.parent = this;
        idToContainer.set(c.eid, c);
        containers.add(c);

        if (containers.size() > maxInBucket && depth < maxDepth) {
            if (nodes[0] == null) {
                float halfWidth = bounds.width / 2;
                float halfHeight = bounds.height / 2;
                nodes[SW] = qtPool.obtain().init(depth + 1, bounds.x, bounds.y, halfWidth, halfHeight, this);
                nodes[SE] = qtPool.obtain().init(depth + 1, bounds.x + halfWidth, bounds.y, halfWidth, halfHeight,
                        this);
                nodes[NW] = qtPool.obtain().init(depth + 1, bounds.x, bounds.y + halfHeight, halfWidth, halfHeight,
                        this);
                nodes[NE] = qtPool.obtain().init(depth + 1, bounds.x + halfWidth, bounds.y + halfHeight, halfWidth,
                        halfHeight, this);
            }

            Object[] items = containers.getData();
            for (int i = containers.size() - 1; i >= 0; i--) {
                Container next = (Container) items[i];
                int index = indexOf(next.x, next.y, next.width, next.height);
                if (index != OUTSIDE) {
                    nodes[index].insert(next);
                    containers.remove(i);
                }
            }
        }
    }

    /**
     * Returns entity ids of entities that are inside {@link QuadTree}s that contain given point
     * <p>
     * Returned entities must be filtered further as these results are not exact
     */
    public IntBag get(IntBag fill, float x, float y) {
        if (bounds.contains(x, y)) {
            if (nodes[0] != null) {
                int index = indexOf(x, y, 0, 0);
                if (index != OUTSIDE) {
                    nodes[index].get(fill, x, y, 0, 0);
                }
            }
            for (int i = 0; i < containers.size(); i++) {
                fill.add(containers.get(i).eid);
            }
        }
        return fill;
    }

    /**
     * Returns entity ids of entities that are inside {@link QuadTree}s that contain given point and have the given flags set
     * <p>
     * Returned entities must be filtered further as these results are not exact
     * 
     * @see #nextFlag()
     */
    public IntBag get(IntBag fill, float x, float y, long flags) {
        if (flags == 0L) {
            return get(fill, x, y);
        }

        if (bounds.contains(x, y)) {
            if (nodes[0] != null) {
                int index = indexOf(x, y, 0, 0);
                if (index != OUTSIDE) {
                    nodes[index].get(fill, x, y, 0, 0, flags);
                }
            }
            for (int i = 0; i < containers.size(); i++) {
                Container c = containers.get(i);
                if ((c.flags & flags) > 0) {
                    fill.add(c.eid);
                }
            }
        }
        return fill;
    }

    /**
     * Returns entity ids of entities that bounds contain given point
     */
    public IntBag getExact(IntBag fill, float x, float y) {
        if (bounds.contains(x, y)) {
            if (nodes[0] != null) {
                int index = indexOf(x, y, 0, 0);
                if (index != OUTSIDE) {
                    nodes[index].getExact(fill, x, y, 0, 0);
                }
            }
            for (int i = 0; i < containers.size(); i++) {
                Container c = containers.get(i);
                if (c.contains(x, y)) {
                    fill.add(c.eid);
                }
            }
        }
        return fill;
    }

    /**
     * Returns entity ids of entities that bounds contain given point and have the given flags set
     * 
     * @see #nextFlag()
     */
    public IntBag getExact(IntBag fill, float x, float y, long flags) {
        if (flags == 0L) {
            return getExact(fill, x, y);
        }

        if (bounds.contains(x, y)) {
            if (nodes[0] != null) {
                int index = indexOf(x, y, 0, 0);
                if (index != OUTSIDE) {
                    nodes[index].getExact(fill, x, y, 0, 0, flags);
                }
            }
            for (int i = 0; i < containers.size(); i++) {
                Container c = containers.get(i);
                if ((c.flags & flags) > 0 && c.contains(x, y)) {
                    fill.add(c.eid);
                }
            }
        }
        return fill;
    }

    /**
     * Returns entity ids of entities that are inside {@link QuadTree}s that overlap given bounds
     * <p>
     * Returned entities must be filtered further as these results are not exact
     */
    public IntBag get(IntBag fill, float x, float y, float width, float height) {
        if (bounds.overlaps(x, y, width, height)) {
            if (nodes[0] != null) {
                int index = indexOf(x, y, width, height);
                if (index != OUTSIDE) {
                    nodes[index].get(fill, x, y, width, height);
                } else {
                    // if test bounds don't fully fit inside a node, we need to check them all
                    for (int i = 0; i < nodes.length; i++) {
                        nodes[i].get(fill, x, y, width, height);
                    }
                }
            }
            for (int i = 0; i < containers.size(); i++) {
                Container c = containers.get(i);
                fill.add(c.eid);
            }
        }
        return fill;
    }

    /**
     * Returns entity ids of entities that are inside {@link QuadTree}s that overlap given bounds and have the given flags set
     * <p>
     * Returned entities must be filtered further as these results are not exact
     * 
     * @see #nextFlag()
     */
    public IntBag get(IntBag fill, float x, float y, float width, float height, long flags) {
        if (flags == 0L) {
            return get(fill, x, y, width, height);
        }

        if (bounds.overlaps(x, y, width, height)) {
            if (nodes[0] != null) {
                int index = indexOf(x, y, width, height);
                if (index != OUTSIDE) {
                    nodes[index].get(fill, x, y, width, height, flags);
                } else {
                    // if test bounds don't fully fit inside a node, we need to check them all
                    for (int i = 0; i < nodes.length; i++) {
                        nodes[i].get(fill, x, y, width, height, flags);
                    }
                }
            }
            for (int i = 0; i < containers.size(); i++) {
                Container c = containers.get(i);
                if ((c.flags & flags) > 0) {
                    fill.add(c.eid);
                }
            }
        }
        return fill;
    }

    /**
     * Returns entity ids of entities that overlap given bounds
     */
    public IntBag getExact(IntBag fill, float x, float y, float width, float height) {
        if (bounds.overlaps(x, y, width, height)) {
            if (nodes[0] != null) {
                int index = indexOf(x, y, width, height);
                if (index != OUTSIDE) {
                    nodes[index].getExact(fill, x, y, width, height);
                } else {
                    // if test bounds don't fully fit inside a node, we need to check them all
                    for (int i = 0; i < nodes.length; i++) {
                        nodes[i].getExact(fill, x, y, width, height);
                    }
                }
            }
            for (int i = 0; i < containers.size(); i++) {
                Container c = containers.get(i);
                if (c.overlaps(x, y, width, height)) {
                    fill.add(c.eid);
                }
            }
        }
        return fill;
    }

    /**
     * Returns entity ids of entities that overlap given bounds and have the given flags set
     * 
     * @see #nextFlag()
     */
    public IntBag getExact(IntBag fill, float x, float y, float width, float height, long flags) {
        if (flags == 0L) {
            return getExact(fill, x, y, width, height);
        }

        if (bounds.overlaps(x, y, width, height)) {
            if (nodes[0] != null) {
                int index = indexOf(x, y, width, height);
                if (index != OUTSIDE) {
                    nodes[index].getExact(fill, x, y, width, height, flags);
                } else {
                    // if test bounds don't fully fit inside a node, we need to check them all
                    for (int i = 0; i < nodes.length; i++) {
                        nodes[i].getExact(fill, x, y, width, height, flags);
                    }
                }
            }
            for (int i = 0; i < containers.size(); i++) {
                Container c = containers.get(i);
                if ((c.flags & flags) > 0 && c.overlaps(x, y, width, height)) {
                    fill.add(c.eid);
                }
            }
        }
        return fill;
    }

    /**
     * Update position for this id with new one
     */
    public void update(int id, float x, float y, float width, float height) {
        Container c = idToContainer.get(id);
        c.set(id, c.flags, x, y, width, height);

        QuadTree qTree = c.parent;
        qTree.containers.remove(c);
        while (qTree.parent != null && !qTree.bounds.contains(c)) {
            qTree = qTree.parent;
        }
        qTree.insert(c);
    }

    /**
     * Remove this id from the tree
     */
    public void remove(int id) {
        Container c = idToContainer.get(id);
        if (c == null)
            return;
        if (c.parent != null) {
            c.parent.containers.remove(c);
        }
        cPool.free(c);
    }

    /**
     * Reset the QuadTree by removing all nodes and stored ids
     */
    @Override
    public void reset() {
        for (int i = containers.size() - 1; i >= 0; i--) {
            cPool.free(containers.remove(i));
        }
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                qtPool.free(nodes[i]);
                nodes[i] = null;
            }
        }
    }

    /**
     * Dispose of the QuadTree by removing all nodes and stored ids
     */
    public void dispose() {
        reset();
    }

    /**
     * @return {@link QuadTree[]} with nodes of these tree, nodes may be null
     */
    public QuadTree[] getNodes() {
        return nodes;
    }

    /**
     * @return {@link Container} that represents bounds of this tree
     */
    public Container getBounds() {
        return bounds;
    }

    @Override
    public String toString() {
        return "QuadTree{" +
                "depth=" + depth + "}";
    }

    /**
     * Simple container for entity ids and their bounds
     */
    public static class Container implements Poolable {
        private int eid;
        private long flags;
        private float x;
        private float y;
        private float width;
        private float height;
        private QuadTree parent;

        public Container() {
        }

        public Container set(int eid, long flags, float x, float y, float width, float height) {
            this.eid = eid;
            this.flags = flags;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            return this;
        }

        public Container set(float x, float y, float width, float height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            return this;
        }

        public boolean contains(float x, float y) {
            return this.x <= x && this.x + this.width >= x && this.y <= y && this.y + this.height >= y;
        }

        public boolean overlaps(float x, float y, float width, float height) {
            return this.x < x + width && this.x + this.width >= x && this.y < y + height && this.y + this.height >= y;
        }

        public boolean contains(float ox, float oy, float owidth, float oheight) {
            float xmin = ox;
            float xmax = xmin + owidth;

            float ymin = oy;
            float ymax = ymin + oheight;

            return ((xmin > x && xmin < x + width) && (xmax > x && xmax < x + width))
                    && ((ymin > y && ymin < y + height) && (ymax > y && ymax < y + height));
        }

        public boolean contains(Container c) {
            return contains(c.x, c.y, c.width, c.height);
        }

        @Override
        public void reset() {
            eid = -1;
            x = 0;
            y = 0;
            width = 0;
            height = 0;
            parent = null;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getWidth() {
            return width;
        }

        public float getHeight() {
            return height;
        }
    }
}
