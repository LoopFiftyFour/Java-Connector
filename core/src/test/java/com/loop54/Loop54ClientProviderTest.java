package com.loop54;

import com.loop54.exceptions.Loop54RuntimeException;
import com.loop54.http.RequestManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.loop54.user.NullClientInfo;

class Loop54ClientProviderTest {
    @Test
    void createSingleSetting() {
        final var provider = new Loop54ClientProvider(NullClientInfo::new,
                Loop54SettingsCollection.create()
                        .add("Test","https://test.54proxy.com"));

        final var namedInstance = provider.getNamed("Test");
        final var defaultInstance = provider.getSingleOrThrow();

        Assertions.assertSame(namedInstance, defaultInstance);
        assertEndpoint(namedInstance, "https://test.54proxy.com");
    }

    @Test
    void createMultipleSettings() {
        final var provider = new Loop54ClientProvider(NullClientInfo::new,
                Loop54SettingsCollection.create()
                        .add("Test1", "https://test-1.54proxy.com")
                        .add("Test2", "https://test-2.54proxy.com"));

        final var namedInstance1 = provider.getNamed("Test1");
        final var namedInstance2 = provider.getNamed("Test2");

        assertEndpoint(namedInstance1, "https://test-1.54proxy.com");
        assertEndpoint(namedInstance2, "https://test-2.54proxy.com");
        Assertions.assertNotSame(namedInstance1, namedInstance2);
    }

    @Test
    void constructorShouldFailIfBadInput() {
        //The context accessor cannot be null
        var exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Loop54ClientProvider(null,
                        Loop54SettingsCollection.create()
                                .add("Test1", "https://test-1.54proxy.com")));
        Assertions.assertTrue(exception.getMessage().contains("remoteClientInfoProvider"));

        //The settingsCollection cannot be null
        exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Loop54ClientProvider(NullClientInfo::new, null));
        Assertions.assertTrue(exception.getMessage().contains("settingsCollection"));

        //The settingscollection cannot be empty
        exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Loop54ClientProvider(NullClientInfo::new,
                        Loop54SettingsCollection.create()));
        Assertions.assertTrue(exception.getMessage().contains("settingsCollection"));
        Assertions.assertTrue(exception.getMessage().contains("empty"));
    }

    @Test
    void getNamedThrowsIfMissingOrNullName() {
        final var provider = new Loop54ClientProvider(NullClientInfo::new,
                Loop54SettingsCollection.create()
                        .add("Test1", "https://test-1.54proxy.com")
                        .add("Test2", "https://test-2.54proxy.com"));

        final var exception = Assertions.assertThrows(Loop54RuntimeException.class,
                () -> provider.getNamed("Test3"));
        Assertions.assertTrue(exception.getMessage().contains("Test3"));

        final var exception2 = Assertions.assertThrows(IllegalArgumentException.class,
                () -> provider.getNamed(null));
        Assertions.assertTrue(exception2.getMessage().contains("instanceName"));
    }

    @Test
    void createMultipleSettingsShouldThrowSingle() {
        final var provider = new Loop54ClientProvider(NullClientInfo::new,
                Loop54SettingsCollection.create()
                        .add("Test1", "https://test-1.54proxy.com")
                        .add("Test2", "https://test-2.54proxy.com"));

        final var exception = Assertions.assertThrows(Loop54RuntimeException.class,
                provider::getSingleOrThrow);

        Assertions.assertTrue(exception.getMessage().contains("Cannot guess a single default client"));
    }

    private void assertEndpoint(final ILoop54Client client, final String expectedEndpoint) {
        final var loop54Client = (Loop54Client)client;
        final var requestManager = (RequestManager)loop54Client.getRequestManager();

        Assertions.assertEquals(expectedEndpoint, requestManager.getSettings().getEndpoint(), "Endpoint mismatch");
    }
}
