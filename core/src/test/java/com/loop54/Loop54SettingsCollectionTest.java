package com.loop54;

import com.loop54.Loop54Settings;
import com.loop54.exceptions.Loop54ArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Loop54SettingsCollectionTest {
    @Test
    public void create() {
        com.loop54.Loop54SettingsCollection result = Loop54SettingsCollection.create();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
        Assertions.assertFalse(result.iterator().hasNext());
    }

    @Test
    public void createAndAdd() {
        com.loop54.Loop54SettingsCollection result = Loop54SettingsCollection.create()
                .add("Test", "https://test-1.54proxy.com");

        Iterator<Map.Entry<String, Loop54Settings>> iterator = result.iterator();
        Map.Entry<String, Loop54Settings> setting = iterator.next();

        Assertions.assertFalse(iterator.hasNext());
        Assertions.assertEquals("Test", setting.getKey());
        Assertions.assertEquals("https://test-1.54proxy.com", setting.getValue().getEndpoint());
    }

    @Test
    public void addNullShouldThrow() {
        com.loop54.Loop54SettingsCollection result = Loop54SettingsCollection.create();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
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
    public void addMultipleAndEnumerate() {
        com.loop54.Loop54SettingsCollection result = Loop54SettingsCollection.create()
                .add("Test1", "https://test-1.54proxy.com")
                .add("Test2", "https://test-2.54proxy.com")
                .add("Test3",
                        new Loop54Settings("https://test-3.54proxy.com",
                                null,
                                true,
                                1337 ));

        List<Map.Entry<String, Loop54Settings>> settings = getSettingsAsList(result);

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
    public void addSameNameShouldThrow() {
        com.loop54.Loop54SettingsCollection result = Loop54SettingsCollection.create()
                .add("Test1", "https://test-1.54proxy.com");

        Loop54ArgumentException exception = Assertions.assertThrows(Loop54ArgumentException.class,
                () -> result.add("Test1", "https://test-1-2.54proxy.com"));
        Assertions.assertTrue(exception.getMessage().contains("Test1"));

        //Assert the collection is unchanged after the failure
        List<Map.Entry<String, Loop54Settings>> settings = getSettingsAsList(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1, settings.size());
        Assertions.assertEquals("Test1", settings.get(0).getKey());
        Assertions.assertEquals("https://test-1.54proxy.com", settings.get(0).getValue().getEndpoint());
    }

    private List<Map.Entry<String, Loop54Settings>> getSettingsAsList(com.loop54.Loop54SettingsCollection collection) {
        List<Map.Entry<String, Loop54Settings>> settings = new ArrayList<>();
        for(Map.Entry<String, Loop54Settings> setting : collection)
            settings.add(setting);
        settings.sort(Comparator.comparing(Map.Entry::getKey));
        return settings;
    }
}
