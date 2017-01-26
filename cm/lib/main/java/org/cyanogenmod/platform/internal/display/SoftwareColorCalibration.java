/*
 * Copyright (C) 2017 The LineageOS Project
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
package org.cyanogenmod.platform.internal.display;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Slog;

public class SoftwareColorCalibration {

    private static final int MIN = 255;
    private static final int MAX = 32768;

    private static final int[] sCurColors = new int[] { MAX, MAX, MAX };

    public static int getDisplayColorCalibrationMax()  {
        return MAX;
    }

    public static String getDisplayColorCalibration()  {
        return String.format("%d %d %d", sCurColors[0],
                sCurColors[1], sCurColors[2]);
    }

    public static boolean setDisplayColorCalibration(String colors) {
        float[] mat = toColorMatrix(colors);

        // set to null if identity
        if (mat == null ||
                (mat[0] == 1.0f && mat[5] == 1.0f &&
                 mat[10] == 1.0f && mat[15] == 1.0f)) {
            return setColorTransform(null);
        }
        return setColorTransform(mat);
    }

    private static float[] toColorMatrix(String rgbString) {
        String[] adj = rgbString == null ? null : rgbString.split(" ");

        if (adj == null || adj.length != 3) {
            return null;
        }

        float[] mat = new float[16];

        // sanity check
        for (int i = 0; i < 3; i++) {
            int v = Integer.parseInt(adj[i]);

            if (v >= MAX) {
                v = MAX;
            } else if (v < MIN) {
                v = MIN;
            }

            mat[i * 5] = (float)v / (float)MAX;
            sCurColors[i] = v;
        }

        mat[15] = 1.0f;
        return mat;
    }

    /**
     * Sets the surface flinger's color transformation as a 4x4 matrix. If the
     * matrix is null, color transformations are disabled.
     *
     * @param m the float array that holds the transformation matrix, or null to
     *            disable transformation
     */
    private static boolean setColorTransform(float[] m) {
        try {
            final IBinder flinger = ServiceManager.getService("SurfaceFlinger");
            if (flinger != null) {
                final Parcel data = Parcel.obtain();
                data.writeInterfaceToken("android.ui.ISurfaceComposer");
                if (m != null) {
                    data.writeInt(1);
                    for (int i = 0; i < 16; i++) {
                        data.writeFloat(m[i]);
                    }
                } else {
                    data.writeInt(0);
                }
                flinger.transact(1030, data, null, 0);
                data.recycle();
            }
        } catch (RemoteException ex) {
            Slog.e(TAG, "Failed to set color transform", ex);
            return false;
        }
        return true;
    }
}
