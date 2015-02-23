package net.mostlyoriginal.api.utils.reference;

import com.artemis.Entity;
import com.artemis.managers.TagManager;

/**
 * Resolve entity by tag.
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
    public boolean valid() {
        return get() != null;
    }

    @Override
    public Entity get() {
        return tagManager.getEntity(tag);
    }
}
