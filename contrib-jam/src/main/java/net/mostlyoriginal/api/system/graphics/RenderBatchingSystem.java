package net.mostlyoriginal.api.system.graphics;

/**
 * @author Daan van Yperen
 */

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Wire;
import com.artemis.utils.Bag;
import com.artemis.utils.IntBag;
import net.mostlyoriginal.api.component.graphics.Renderable;
import net.mostlyoriginal.api.system.delegate.EntityProcessAgent;
import net.mostlyoriginal.api.system.delegate.EntityProcessPrincipal;
import net.mostlyoriginal.api.utils.BagUtils;

/**
 * Extensible rendering system.
 * <p/>
 * Create specialized rendering systems with DeferredEntityProcessingSystems.
 * RenderBatchingSystem will take care of overarching concerns like grouping
 * and sorting, while the specialist systems take care of the actual rendering.
 * <p/>
 * Currently only supports one specialist handling system per entity.
 *
 * @author Daan van Yperen
 * @see net.mostlyoriginal.api.component.graphics.Anim
 */
@Wire
public class RenderBatchingSystem extends EntitySystem implements EntityProcessPrincipal {

    protected ComponentMapper<Renderable> mRenderable;

    protected final Bag<Job> sortedJobs = new Bag<>();
    public boolean sortedDirty = false;

    public RenderBatchingSystem() {
         // we let the agents track entities.
        super(Aspect.getEmpty());
    }

    /**
       * Declare entity relevant for agent.
       *
       * After this is called, the principal can use the agent
       * interface to begin/end and process the given entity.
       *
       * @param e entity to process
       * @param agent interface to dispatch with.
       */
    @Override
    public void registerAgent(Entity e, EntityProcessAgent agent) {
        if ( !mRenderable.has(e)) throw new RuntimeException("RenderBatchingSystem requires agents entities to have component Renderable.");
        // register new job. this will influence sorting order.
        sortedJobs.add(new Job(e, agent));
        sortedDirty = true;
    }

    /**
     * Revoke relevancy of entity for agent.
     *
     * After this is called, the principal should no longer
     * attempt to process the entity with the agent.

     * @param e entity to process
     * @param agent interface to dispatch with.
     */
    @Override
    public void unregisterAgent(Entity e, EntityProcessAgent agent) {
        // forget about the job.
        final Object[] data = sortedJobs.getData();
        for (int i = 0, s = sortedJobs.size(); i < s; i++) {
            final Job e2 = (Job) data[i];
            if (e2.entity == e && e2.agent == agent) {
                sortedJobs.remove(i);
                sortedDirty=true;
                break;
            }
        }
    }

    /**
     * Process list of entities to delegate.
     */
    @Override
    protected void processEntities(IntBag entities) {

        if (sortedDirty) {
            // sort our jobs (by layer).
            sortedDirty = false;
            BagUtils.sort(sortedJobs);
        }

        // iterate through all the jobs.
        // @todo add support for entities being deleted.
        EntityProcessAgent activeAgent = null;
        final Object[] data = sortedJobs.getData();
        for (int i=0, s=sortedJobs.size(); i<s; i++)
        {
            final Job job = (Job)data[i];
            final EntityProcessAgent agent = job.agent;

            // agent changed? end() the last agent, and begin() the next agent.
            // @todo extend this with eventual texture/viewport/etc demarcation.
            if (agent != activeAgent) {
                if (activeAgent != null) {
                    activeAgent.end();
                }
                activeAgent = agent;
                activeAgent.begin();
            }

            // process the entity!
            agent.process(job.entity);
        }

        // finished, terminate final agent.
        if ( activeAgent != null )
        {
            activeAgent.end();
        }
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    /** Rendering job wrapper. */
    public class Job implements Comparable<Job> {
        public final Entity entity;
        public final EntityProcessAgent agent;

        /**
         * @param entity entity we will process
         * @param agent agent responsible for processing.
         */
        public Job(final Entity entity, final EntityProcessAgent agent) {
            this.entity = entity;
            this.agent = agent;
        }

        @Override
        public int compareTo(Job o) {
            return mRenderable.get(this.entity).layer - mRenderable.get(o.entity).layer;
        }
    }
}
