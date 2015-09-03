package net.mostlyoriginal.api.component;

import com.artemis.Component;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import net.mostlyoriginal.api.operation.common.Operation;
import net.mostlyoriginal.api.operation.flow.ParallelOperation;
import net.mostlyoriginal.api.system.SchedulerSystem;

/**
 * @author Daan van Yperen
 * @see SchedulerSystem
 */
public class Schedule extends Component {

    public ParallelOperation operation = new ParallelOperation();

    public Schedule() {
    }

    /**
     * Returns a new or pooled action of the specified type.
     */
    static public <T extends Operation> T prepare(Class<T> type) {
        Pool<T> pool = Pools.get(type);
        T node = pool.obtain();
        node.setPool(pool);
        return node;
    }
}
