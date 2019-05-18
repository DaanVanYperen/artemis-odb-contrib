package net.mostlyoriginal.plugin;

import com.artemis.ComponentType;

/**
 * Implement to capture artemis-odb lifecycle events.
 *
 * requires {@link com.artemis.LifecycleListenerPlugin} to be registered with your {@link com.artemis.World}.
 *
 * @see com.artemis.LifecycleListenerPlugin
 * @author Daan van Yperen
 */
public interface LifecycleListener {

    enum Type {

        /** Triggered when the entity is scheduled for deletion */
        ENTITY_DELETE_PLANNED,

        /** Triggered when the entity is actually deleted. */
        ENTITY_DELETE_FINALIZED,

        /** Triggered just after entity is created. */
        ENTITY_CREATE_POST,

        /** Triggered just before entity is fetched. */
        ENTITY_GET_PRE,

        /** Triggers just before entity component identity is checked.*/
        ENTITY_IDENTITY_PRE,

        /** Triggers just before entity components are fetched. */
        ENTITY_COMPONENTS_PRE,

        /** Triggersat the start of entity edit() function call. */
        ENTITY_EDIT_PRE,

        /** Triggers just before entity is checked for activity. */
        ENTITY_ISACTIVE_CHECK_PRE,

        /** Triggers just before component gets fetched. {@code OptionalArg=ComponentType}. */
        COMPONENT_GET_PRE,

        /** Triggers just before component check. {@code OptionalArg=ComponentType}. */
        COMPONENT_HAS_PRE,

        /** Triggers just before attempted component removal. {@code OptionalArg=ComponentType}. */
        COMPONENT_REMOVE_PRE,

        /** Triggers just before attempted component removal (called by odb internally.)  {@code OptionalArg=ComponentType}. */
        COMPONENT_INTERNAL_REMOVE_PRE,

        /** Triggers just before attempted component creation. {@code OptionalArg=ComponentType}. */
        COMPONENT_CREATE_PRE,

        /** Triggers just before component is created. (called by odb internally.).  {@code OptionalArg=ComponentType} */
        COMPONENT_INTERNAL_CREATE_PRE,

        /** Triggers at the start of componentmanager clean call.*/
        COMPONENTMANAGER_CLEAN_PRE,
        /** Triggers at the end of componentmanager clean call. */
        COMPONENTMANAGER_CLEAN_POST
    }

    /**
     *
     * @param event Type of event triggered.
     * @param entityId Optional EntityId, {@code -1} if not available.
     * @param optionalArg optional argument. See type for details.
     */
    void onLifecycleEvent(Type event, int entityId, Object optionalArg);
}