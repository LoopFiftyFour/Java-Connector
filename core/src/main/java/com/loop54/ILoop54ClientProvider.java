package com.loop54;

import java.io.Closeable;

/**
 * Interface for handling multiple instances of {@link ILoop54Client} within the same application.
 */
public interface ILoop54ClientProvider extends Closeable {
    /**
     * Get a named instance of ILoop54Client. Will throw if an instance with the same name is not found.
     * @param instanceName Name of a instance. For example 'swedish', 'english' or 'content'.
     * @return The named instance.
     */
    ILoop54Client getNamed(String instanceName);
}
