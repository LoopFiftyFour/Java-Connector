package com.loop54;

import com.loop54.model.request.Request;
import com.loop54.user.UserMetaData;
import com.loop54.user.IRemoteClientInfo;

import java.util.Optional;

/** This class wraps a request object together with options for the API call. */
public class RequestContainer<T extends Request> {
    /** @param requestData The request to wrap. */
    public RequestContainer(T requestData, UserMetaData metaDataOverrides) {
        this.request = requestData;
        this.metaDataOverrides = Optional.ofNullable(metaDataOverrides);
    }

    /** Contains the request data to send to the engine. */
    public final T request;

    /**
     * Overrides for client meta data. Should be used if the UserId needs to be set from your
     * internal customer id. If no override is provided all the meta-data will be taken from
     * the current {@link IRemoteClientInfo}.
     */
    public final Optional<UserMetaData> metaDataOverrides;
}
