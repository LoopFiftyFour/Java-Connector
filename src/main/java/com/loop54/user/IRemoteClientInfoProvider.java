package com.loop54.user;

/** An interface for creating {@link IRemoteClientInfo}. */
public interface IRemoteClientInfoProvider {
    /**
     * Creates and returns a {@link IRemoteClientInfo} that contains information of the current, calling end-user (headers, cookies etc)
     * @return A new {@link IRemoteClientInfo}.
     */
    IRemoteClientInfo getRemoteClientInfo();
}
