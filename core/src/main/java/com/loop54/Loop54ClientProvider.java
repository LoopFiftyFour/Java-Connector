package com.loop54;

import com.loop54.exceptions.Loop54Exception;
import com.loop54.exceptions.Loop54RuntimeException;
import com.loop54.http.RequestManager;
import com.loop54.user.IRemoteClientInfoProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class for handling multiple instances of ILoop54Client. Should be used if working against multiple instances of the
 * Loop54 e-commerce search engine from the same application. The class is thread safe.
 */
public class Loop54ClientProvider implements ILoop54ClientProvider{
    private final ConcurrentHashMap<String, ILoop54Client> clients = new ConcurrentHashMap<>();

    /**
     * @param remoteClientInfoProvider The client info provider to use in the clients.
     * @param settingsCollection One or more settings to setup clients for.
     */
    public Loop54ClientProvider(IRemoteClientInfoProvider remoteClientInfoProvider, Loop54SettingsCollection settingsCollection)
    {
        if (remoteClientInfoProvider == null)
            throw new IllegalArgumentException("remoteClientInfoProvider is null");

        if (settingsCollection == null)
            throw new IllegalArgumentException("settingsCollection is null");

        if (settingsCollection.size() == 0)
            throw new IllegalArgumentException("The provided settingsCollection cannot be empty.");

        createClientsForSettings(settingsCollection, remoteClientInfoProvider);
    }

    private void createClientsForSettings(Loop54SettingsCollection settingsCollection,
                                          IRemoteClientInfoProvider remoteClientInfoProvider)
    {
        for (Map.Entry<String,Loop54Settings> setting : settingsCollection)
            clients.put(setting.getKey(),
                    new Loop54Client(new RequestManager(setting.getValue()), remoteClientInfoProvider));
    }

    /**
     * Get a named instance of ILoop54Client. Will throw if an instance with the same name is not found.
     * @param instanceName Name of a instance. For example 'swedish', 'english' or 'content'.
     * @return The named instance.
     */
    public ILoop54Client getNamed(String instanceName)
    {
        if (instanceName == null)
            throw new IllegalArgumentException("instanceName is null");

        ILoop54Client client = clients.getOrDefault(instanceName, null);

        if (client == null)
            throw new Loop54RuntimeException("Loop54 client with instance name '" + instanceName + "' is not " +
                    "initialized. You must add it to the settings used when initializing.");

        return client;
    }

    public ILoop54Client getSingleOrThrow()
    {
        if (clients.isEmpty() || clients.size() > 1)
            throw new Loop54RuntimeException("Cannot guess a single default client if there are " +
                    clients.size() + " registered. Use the 'ILoop54ClientProvider.GetNamed' method instead.");

        return clients.elements().nextElement();
    }
}
