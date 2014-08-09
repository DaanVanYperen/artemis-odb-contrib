package net.mostlyoriginal.api.network.marshal.common;

import java.util.HashMap;

/**
 * Dictionary of all classes you wish to marshall over the web.
 *
 * Register all your events, components that might be marshalled.
 * ID's in the dictionary must by unique, and match on both client
 * and server.
 *
 * @author Daan van Yperen
 */
public class MarshalDictionary {

    private final HashMap<Integer, Class> items = new HashMap<>();
    public int topId;

    /** Create an empty dictionary. */
    public MarshalDictionary() { }

    /** Create a dictionary, registering them with sequential IDs. */
    public MarshalDictionary(Class... classes) {
        for (Class clazz : classes) {
            register( topId++, clazz);
        }
    }

    /**
     * Register object for network transfer.
     *
     * @param id unique unused ID, must match on server and client.
     * @param c object to register at ID.
     * @return this
     */
    public MarshalDictionary register( Integer id, Class c )
    {
        if ( id == null ) throw new NullPointerException("id cannot be null");
        if ( c == null ) throw new NullPointerException("object cannot be null");
        if ( items.containsKey(id) ) throw new RuntimeException("Network dictionary ID #"+id+" already claimed by " + items.get(id).getClass().getSimpleName() + ", cannot claim it for " + c.getClass().getSimpleName());
        items.put(id, c);
        return this;
    }

    /** Get all registered items. */
    public HashMap<Integer, Class> getItems() {
        return items;
    }
}
