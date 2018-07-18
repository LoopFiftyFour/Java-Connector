package com.loop54;

import com.loop54.exceptions.Loop54RuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Loop54ClientProviderTest {
    @Test
    public void createSingleSetting() {
        com.loop54.Loop54ClientProvider provider = new com.loop54.Loop54ClientProvider(() -> new NullClientInfo(),
                com.loop54.Loop54SettingsCollection.create()
                        .add("Test","https://test.54proxy.com"));

        ILoop54Client namedInstance = provider.getNamed("Test");
        ILoop54Client defaultInstance = provider.getSingleOrThrow();

        Assertions.assertSame(namedInstance, defaultInstance);
        AssertEndpoint(namedInstance, "https://test.54proxy.com");
    }

    @Test
    public void createMultipleSettings() {
        com.loop54.Loop54ClientProvider provider = new com.loop54.Loop54ClientProvider(() -> new NullClientInfo(),
                com.loop54.Loop54SettingsCollection.create()
                        .add("Test1", "https://test-1.54proxy.com")
                        .add("Test2", "https://test-2.54proxy.com"));

        ILoop54Client namedInstance1 = provider.getNamed("Test1");
        ILoop54Client namedInstance2 = provider.getNamed("Test2");

        AssertEndpoint(namedInstance1, "https://test-1.54proxy.com");
        AssertEndpoint(namedInstance2, "https://test-2.54proxy.com");
        Assertions.assertNotSame(namedInstance1, namedInstance2);
    }

    @Test
    public void constructorShouldFailIfBadInput() {
        //The context accessor cannot be null
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new com.loop54.Loop54ClientProvider(null,
                        com.loop54.Loop54SettingsCollection.create()
                                .add("Test1", "https://test-1.54proxy.com")));
        Assertions.assertTrue(exception.getMessage().contains("remoteClientInfoProvider"));

        //The settingsCollection cannot be null
        exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new com.loop54.Loop54ClientProvider(() -> new NullClientInfo(), null));
        Assertions.assertTrue(exception.getMessage().contains("settingsCollection"));

        //The settingscollection cannot be empty
        exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new com.loop54.Loop54ClientProvider(() -> new NullClientInfo(),
                        com.loop54.Loop54SettingsCollection.create()));
        Assertions.assertTrue(exception.getMessage().contains("settingsCollection"));
        Assertions.assertTrue(exception.getMessage().contains("empty"));
    }

    @Test
    public void getNamedThrowsIfMissingOrNullName() {
        com.loop54.Loop54ClientProvider provider = new com.loop54.Loop54ClientProvider(() -> new NullClientInfo(),
                com.loop54.Loop54SettingsCollection.create()
                        .add("Test1", "https://test-1.54proxy.com")
                        .add("Test2", "https://test-2.54proxy.com"));

        Loop54RuntimeException exception = Assertions.assertThrows(Loop54RuntimeException.class,
                () -> provider.getNamed("Test3"));
        Assertions.assertTrue(exception.getMessage().contains("Test3"));

        IllegalArgumentException exception2 = Assertions.assertThrows(IllegalArgumentException.class,
                () -> provider.getNamed(null));
        Assertions.assertTrue(exception2.getMessage().contains("instanceName"));
    }

    @Test
    public void createMultipleSettingsShouldThrowSingle() {
        com.loop54.Loop54ClientProvider provider = new com.loop54.Loop54ClientProvider(() -> new NullClientInfo(),
                com.loop54.Loop54SettingsCollection.create()
                        .add("Test1", "https://test-1.54proxy.com")
                        .add("Test2", "https://test-2.54proxy.com"));

        Loop54RuntimeException exception = Assertions.assertThrows(Loop54RuntimeException.class,
                () -> provider.getSingleOrThrow());

        Assertions.assertTrue(exception.getMessage().contains("Cannot guess a single default client"));
    }

    private void AssertEndpoint(ILoop54Client client, String expectedEndpoint) {
        com.loop54.Loop54Client loop54Client = (com.loop54.Loop54Client)client;
        com.loop54.http.RequestManager requestManager
                = (com.loop54.http.RequestManager)loop54Client.getRequestManager();

        String endpoint = requestManager.getSettings().getEndpoint();
        Assertions.assertTrue(endpoint.equals(expectedEndpoint), "Endpoint mismatch");
    }
}
