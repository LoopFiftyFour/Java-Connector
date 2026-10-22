package com.loop54;

import com.loop54.exceptions.Loop54ArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

class Loop54SettingsCollectionTest {
    @Test
    void create() {
        final var result = Loop54SettingsCollection.create();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
        Assertions.assertFalse(result.iterator().hasNext());
    }

    @Test
    void createAndAdd() {
        final var result = Loop54SettingsCollection.create()
                .add("Test", "https://test-1.54proxy.com");

        final var iterator = result.iterator();
        final var setting = iterator.next();

        Assertions.assertFalse(iterator.hasNext());
        Assertions.assertEquals("Test", setting.getKey());
        Assertions.assertEquals("https://test-1.54proxy.com", setting.getValue().getEndpoint());
    }

    @Test
    void addNullShouldThrow() {
        final var result = Loop54SettingsCollection.create();

        var exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> result.add("Test1", (String)null));
        Assertions.assertTrue(exception.getMessage().contains("endpoint"));

        exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> result.add(null,
                        new Loop54Settings("https://test-1.54proxy.com", null)));
        Assertions.assertTrue(exception.getMessage().contains("instanceName"));

        exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> result.add("Test1", (Loop54Settings)null));
        Assertions.assertTrue(exception.getMessage().contains("settingsToAdd"));

        //Assert the collection is unchanged after the failure
        Assertions.assertEquals(0, result.size());
        Assertions.assertFalse(result.iterator().hasNext());
    }

    @Test
    void addMultipleAndEnumerate() {
        final var result = Loop54SettingsCollection.create()
                .add("Test1", "https://test-1.54proxy.com")
                .add("Test2", "https://test-2.54proxy.com")
                .add("Test3",
                        new Loop54Settings("https://test-3.54proxy.com",
                                null,
                                true,
                                1337));

        final var settings = getSettingsAsList(result);

        Assertions.assertEquals(3, settings.size());
        Assertions.assertEquals("Test1", settings.get(0).getKey());
        Assertions.assertEquals("https://test-1.54proxy.com", settings.get(0).getValue().getEndpoint());
        Assertions.assertEquals("Test2", settings.get(1).getKey());
        Assertions.assertEquals("https://test-2.54proxy.com", settings.get(1).getValue().getEndpoint());
        Assertions.assertEquals("Test3", settings.get(2).getKey());
        Assertions.assertEquals("https://test-3.54proxy.com", settings.get(2).getValue().getEndpoint());
        Assertions.assertEquals(1337, settings.get(2).getValue().getRequestTimeoutMs());
    }

    @Test
    void addSameNameShouldThrow() {
        final var result = Loop54SettingsCollection.create()
                .add("Test1", "https://test-1.54proxy.com");

        final var exception = Assertions.assertThrows(Loop54ArgumentException.class,
                () -> result.add("Test1", "https://test-1-2.54proxy.com"));
        Assertions.assertTrue(exception.getMessage().contains("Test1"));

        //Assert the collection is unchanged after the failure
        final var settings = getSettingsAsList(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1, settings.size());
        Assertions.assertEquals("Test1", settings.get(0).getKey());
        Assertions.assertEquals("https://test-1.54proxy.com", settings.get(0).getValue().getEndpoint());
    }

    private List<Map.Entry<String, Loop54Settings>> getSettingsAsList(final Loop54SettingsCollection collection) {
        final List<Map.Entry<String, Loop54Settings>> settings = new ArrayList<>();
        for (final var setting : collection) {
            settings.add(setting);
        }
        settings.sort(Comparator.comparing(Map.Entry::getKey));
        return settings;
    }
}
