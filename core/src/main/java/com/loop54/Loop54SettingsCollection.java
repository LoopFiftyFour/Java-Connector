package com.loop54;

import com.loop54.exceptions.Loop54ArgumentException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class containing a named settings for the Loop54 search engine library.
 */
public class Loop54SettingsCollection implements Iterable<Map.Entry<String,Loop54Settings>> {

    private final Map<String, Loop54Settings> settings = new HashMap<>();

    private Loop54SettingsCollection() {
    }

    /**
     * Will create and return a new, empty instance of {@link Loop54SettingsCollection}.
     * @return New instance of {@link Loop54SettingsCollection}.
     */
    public static Loop54SettingsCollection create() {
        return new Loop54SettingsCollection();
    }

    /**
     * Adds a named endpoint setting to the collection.
     * @param instanceName Name of the setting instance. For example 'swedish', 'english' or 'content'.
     * @param endpoint The endpoint to affiliate with the instance.
     * @return The Loop54SettingsCollection instance. For chaining.
     */
    public Loop54SettingsCollection add(String instanceName, String endpoint) {
        if (endpoint == null)
            throw new IllegalArgumentException("endpoint is null");

        return add(instanceName, new Loop54Settings(endpoint, null));
    }

    /**
     * Adds a named setting to the collection.
     * @param instanceName Name of the setting instance. For example 'swedish', 'english' or 'content'.
     * @param settings The settings to affiliate with the instance.
     * @return The Loop54SettingsCollection instance. For chaining.
     */
    public Loop54SettingsCollection add(String instanceName, Loop54Settings settings) {
        if (settings == null)
            throw new IllegalArgumentException("settings is null");

        if (instanceName == null)
            throw new IllegalArgumentException("instanceName is null");

        if (this.settings.containsKey(instanceName))
            throw new Loop54ArgumentException("There's already a '" + instanceName + "' in the collection. Cannot add" +
                    " it again.");

        this.settings.put(instanceName, settings);
        return this;
    }


    @Override
    public Iterator<Map.Entry<String, Loop54Settings>> iterator() {
        return settings.entrySet().iterator();
    }

    /**
     * @return The number of settings in the collection
     */
    public int size() {
        return settings.size();
    }

}
