package com.pps.globant.fittracker.utils;

import com.pps.globant.fittracker.service.AvatarService;

public final class ServiceUtils {
    private static AvatarService avatarService;

    private ServiceUtils() {
    }

    public static final AvatarService getAvatarService() {
        if (avatarService == null) {
            avatarService = new AvatarService(BusProvider.getInstance());
        }
        return avatarService;
    }
}
