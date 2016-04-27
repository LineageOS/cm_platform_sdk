/**
 * Copyright (c) 2016, The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cyanogenmod.tests.power.unit;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import cyanogenmod.app.CMContextConstants;
import cyanogenmod.power.IPerformanceManager;
import cyanogenmod.power.PerformanceManager;

/**
 * Code coverage for public facing {@link PerformanceManager} interfaces.
 * The test below will save and restore the current performance profile to
 * not impact successive tests.
 */
public class PerfomanceManagerTest extends AndroidTestCase {
    private static final String TAG = PerfomanceManagerTest.class.getSimpleName();
    private static final int IMPOSSIBLE_POWER_PROFILE = -1;
    private PerformanceManager mCMPerformanceManager;
    private int mSavedPerfProfile;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Only run this if we support performance abstraction
        org.junit.Assume.assumeTrue(mContext.getPackageManager().hasSystemFeature(
                CMContextConstants.Features.PERFORMANCE));
        mCMPerformanceManager = PerformanceManager.getInstance(mContext);
        // Save the perf profile for later restore.
        mSavedPerfProfile = mCMPerformanceManager.getPowerProfile();
    }

    @SmallTest
    public void testManagerExists() {
        assertNotNull(mCMPerformanceManager);
    }

    @SmallTest
    public void testManagerServiceIsAvailable() {
        IPerformanceManager icmStatusBarManager = mCMPerformanceManager.getService();
        assertNotNull(icmStatusBarManager);
    }

    @SmallTest
    public void testGetNumberOfPerformanceProfiles() {
        // Assert that we can even set perf profiles
        assertTrue(mCMPerformanceManager.getNumberOfProfiles() > 0);
    }

    @SmallTest
    public void testGetPowerProfile() {
        assertNotSame(IMPOSSIBLE_POWER_PROFILE, mSavedPerfProfile);
    }

    @SmallTest
    public void testSetAndGetPowerProfile() {
        // Identify what power profiles are supported. The api currently returns
        // the total number of profiles supported in an ordered manner, thus we can
        // assume what they are and if we can set everything correctly.
        for (int i = 0; i < mCMPerformanceManager.getNumberOfProfiles(); i++) {
            // If the target perf profile is the same as the current one,
            // setPowerProfile will noop, ignore that scenario
            if (mCMPerformanceManager.getPowerProfile() != i) {
                mCMPerformanceManager.setPowerProfile(i);
                // Verify that it was set correctly.
                assertEquals(i, mCMPerformanceManager.getPowerProfile());
            }
        }
    }

    @SmallTest
    public void testGetPerfProfileHasAppProfiles() {
        // No application has power save by default
        assertEquals(false, mCMPerformanceManager.getProfileHasAppProfiles(
                PerformanceManager.PROFILE_POWER_SAVE));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        // Reset
        mCMPerformanceManager.setPowerProfile(mSavedPerfProfile);
    }
}
