package com.loop54.model.response;

import com.loop54.exceptions.Loop54Exception;
import com.loop54.exceptions.SerializationException;
import com.loop54.serialization.Serializer;

import java.util.LinkedHashMap;
import java.util.Map;

/** A response from the engine. Used for responses that don't return any standardized data parameters. */
public class Response {
    /** Any additional, non-standard, data. Contact support for information about how and when to use this. */
    public Map<String, Object> customData;

    /**
     * Retrieve a named custom value from the response. If not found, or the value could not be deserialized or cast
     * to T, it will throw an exception.
     * @param key Key of the custom data.
     * @param clazz Expected class of the response data to retrieve.
     * @param <T> Expected type of the response data to retrieve.
     * @return Deserialized data as the specified type, if it exists.
     * @throws Loop54Exception If not found, or the value could not be deserialized to the desired class.
     */
    public <T> T getCustomDataOrThrow(String key, Class<T> clazz) throws Loop54Exception {
        CustomDataContainer<T> result = tryGetCustomData(key, clazz);

        if(!result.foundData)
            throw new Loop54Exception("No data with key '" + key + "' found on the response");

        return result.data;
    }

    /**
     * Retrieve a named custom value from the response. If not found it will return the default value. If the value
     * could not be deserialized or cast to T it will throw an exception.
     * @param key Key of the custom data.
     * @param clazz Expected class of the response data to retrieve.
     * @param <T> Expected type of the response data to retrieve.
     * @return Deserialized data as the specified type. If the data did not exist null is returned.
     * @throws Loop54Exception if the value cannot be deserialized to the desired class.
     */
    public <T> T getCustomDataOrDefault(String key, Class<T> clazz) throws Loop54Exception {
        return tryGetCustomData(key, clazz).data;
    }

    private class CustomDataContainer<T> {
        public CustomDataContainer(boolean foundData, T data) {
            this.data = data;
            this.foundData = foundData;
        }

        public T data;
        public boolean foundData;
    }

    private <T> CustomDataContainer<T> tryGetCustomData(String key, Class<T> clazz) throws Loop54Exception
    {
        if (customData == null)
            return new CustomDataContainer(false, null);

        Object data = customData.get(key);

        if (data != null)
        {
            //When Jackson deserialize a complex json to a object-instance it ends up as a LinkedHashMap.
            //Which we try to deserialize to the expected type.
            if (data instanceof LinkedHashMap) {
                try{
                    return new CustomDataContainer(true, Serializer.deserialize((LinkedHashMap)data, clazz));
                }
                catch(SerializationException e){
                    new Loop54Exception("The data with key '"+ key + "' couldn't be deserialized to '" + clazz.toString() + "'", e);
                }
            }

            if (clazz.isAssignableFrom(data.getClass())) {
                return new CustomDataContainer(true, (T) data);
            }

            throw new Loop54Exception("The data with key '" + key + "' couldn't be deserialized or cast to '" + clazz.toString() + "'");
        }

        return new CustomDataContainer(false, null);
    }
}
