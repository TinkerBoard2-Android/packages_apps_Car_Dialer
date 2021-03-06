/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.car.dialer.testutils;

import android.os.IBinder;
import android.os.ServiceManager;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowServiceManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Derived from {@link org.robolectric.shadows.ShadowServiceManager}.
 *
 * Needed for {@link com.android.car.dialer.telecom.UiCallManagerTest} which needs to set up the
 * ITelephony service.
 */

@Implements(value = ServiceManager.class)
public class ShadowServiceManagerOverride extends ShadowServiceManager {
    private static final Map<String, IBinder> EXTRA_SERVICES = new HashMap<>();

    @Implementation
    public static IBinder getService(String name) {
        IBinder iBinder = ShadowServiceManager.getService(name);
        if (iBinder == null) {
            return EXTRA_SERVICES.get(name);
        }
        return iBinder;
    }

    /** Sets the service with {@param name} to {@param service}. */
    @Implementation
    public static void addService(String name, IBinder service) {
        ShadowServiceManager.addService(name, service);
        EXTRA_SERVICES.put(name, service);
    }

}
