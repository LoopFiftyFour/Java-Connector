package com.loop54.spring;

import com.loop54.user.IRemoteClientInfo;
import com.loop54.user.IRemoteClientInfoProvider;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * ClientInfoProvider that uses the Spring RequestContextHolder class for making it's IRemoteClientInfo
 */
public class SpringRemoteClientInfoProvider implements IRemoteClientInfoProvider {
    @Override
    public IRemoteClientInfo getRemoteClientInfo() {
        ServletRequestAttributes attributes
                = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes());

        return new SpringRemoteClientInfo(attributes);
    }
}
