package net.mostlyoriginal.api.utils;

import com.artemis.Entity;
import com.artemis.managers.TagManager;

/**
 * Refer to entity by tag.
 *
 * @author Daan van Yperen
 */
public class TagEntityReference implements EntityReference {

    private final transient TagManager tagManager;
    private final String tag;

    public TagEntityReference( TagManager tagManager, String tag ) {
        this.tagManager = tagManager;
        this.tag = tag;
    }

    @Override
    public boolean isActive() {
        return get() != null;
    }

    @Override
    public Entity get() {
        return tagManager.getEntity(tag);
    }
}
