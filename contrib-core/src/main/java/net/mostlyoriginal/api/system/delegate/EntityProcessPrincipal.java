package net.mostlyoriginal.api.system.delegate;

/**
 * Ability to basic as a principal is achieved
 * by implementing the EntityProcessPrincipal interface.
 *
 * @author Daan van Yperen
 */
public interface EntityProcessPrincipal {

    /**
     * Declare entity relevant for agent.
     *
     * After this is called, the principal can use the agent
     * interface to begin/end and process the given entity.
     *
     * @param entityId entity to process
     * @param agent interface to dispatch with.
     */
    public void registerAgent(int entityId, EntityProcessAgent agent);

    /**
     * Revoke relevancy of entity for agent.
     *
     * After this is called, the principal should no longer
     * attempt to process the entity with the agent.

     * @param entityId entity to process
     * @param agent interface to dispatch with.
     */
    public void unregisterAgent(int entityId, EntityProcessAgent agent);
}
