package cyanogenmod.weatherservice;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import cyanogenmod.os.Build;
import cyanogenmod.weather.WeatherLocation;
import cyanogenmod.weather.WeatherInfo;

import java.util.ArrayList;

/**
 * Use this class to build a request result.
 */
public final class ServiceRequestResult implements Parcelable {

    private WeatherInfo mWeatherInfo;
    private ArrayList<WeatherLocation> mLocationLookupList;

    private ServiceRequestResult() {}

    private ServiceRequestResult(Parcel in) {
        int parcelableVersion = in.readInt();
        int parcelableSize = in.readInt();
        int startPosition = in.dataPosition();
        if (parcelableVersion >= Build.CM_VERSION_CODES.ELDERBERRY) {
            int hasWeatherInfo = in.readInt();
            if (hasWeatherInfo == 1) {
                mWeatherInfo = WeatherInfo.CREATOR.createFromParcel(in);
            }
            int hasLocationLookupList = in.readInt();
            if (hasLocationLookupList == 1) {
                mLocationLookupList = new ArrayList<>();
                int listSize = in.readInt();
                while (listSize > 0) {
                    mLocationLookupList.add(WeatherLocation.CREATOR.createFromParcel(in));
                    listSize--;
                }
            }
        }
        in.setDataPosition(startPosition + parcelableSize);
    }

    public static final Creator<ServiceRequestResult> CREATOR
            = new Creator<ServiceRequestResult>() {
        @Override
        public ServiceRequestResult createFromParcel(Parcel in) {
            return new ServiceRequestResult(in);
        }

        @Override
        public ServiceRequestResult[] newArray(int size) {
            return new ServiceRequestResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Build.PARCELABLE_VERSION);

        int sizePosition = dest.dataPosition();
        dest.writeInt(0);
        int startPosition = dest.dataPosition();

        // ==== ELDERBERRY =====
        if (mWeatherInfo != null) {
            dest.writeInt(1);
            mWeatherInfo.writeToParcel(dest, 0);
        } else {
            dest.writeInt(0);
        }
        if (mLocationLookupList != null) {
            dest.writeInt(1);
            dest.writeInt(mLocationLookupList.size());
            for (WeatherLocation lookup : mLocationLookupList) {
                lookup.writeToParcel(dest, 0);
            }
        } else {
            dest.writeInt(0);
        }

        int parcelableSize = dest.dataPosition() - startPosition;
        dest.setDataPosition(sizePosition);
        dest.writeInt(parcelableSize);
        dest.setDataPosition(startPosition + parcelableSize);
    }

    public static class Builder {
        private WeatherInfo mBuilderWeatherInfo;
        private ArrayList<WeatherLocation> mBuilderLocationLookupList;
        public Builder() {
            this.mBuilderWeatherInfo = null;
            this.mBuilderLocationLookupList = null;
        }

        /**
         * Add the supplied weather information to the result. Attempting to add a WeatherLocation
         * list to the same builder will cause the system to throw IllegalArgumentException
         *
         * @param weatherInfo The WeatherInfo object holding the data that will be used to update
         *                    the weather content provider
         */
        public Builder setWeatherInfo(@NonNull WeatherInfo weatherInfo) {
            if (mBuilderLocationLookupList != null) {
                throw new IllegalArgumentException("Can't add weather information when you have"
                        + " already added a WeatherLocation list");
            }

            if (weatherInfo == null) {
                throw new IllegalArgumentException("WeatherInfo can't be null");
            }

            mBuilderWeatherInfo = weatherInfo;
            return this;
        }

        /**
         * Add the supplied list of WeatherLocation objects to the result. Attempting to add a
         * WeatherInfo object to the same builder will cause the system to throw
         * IllegalArgumentException
         *
         * @param locations The list of WeatherLocation objects. The list should not be null
         */
        public Builder setLocationLookupList(@NonNull ArrayList<WeatherLocation> locations) {
            if (mBuilderWeatherInfo != null) {
                throw new IllegalArgumentException("Can't add a WeatherLocation list when you have"
                        + " already added weather information");
            }

            if (locations == null) {
                throw new IllegalArgumentException("WeatherLocation list can't be null");
            }

            mBuilderLocationLookupList = locations;
            return this;
        }

        /**
         * Creates a {@link ServiceRequest} with the arguments
         * supplied to this builder
         * @return {@link ServiceRequestResult}
         */
        public ServiceRequestResult create() {
            ServiceRequestResult result = new ServiceRequestResult();
            result.mWeatherInfo = this.mBuilderWeatherInfo;
            result.mLocationLookupList = this.mBuilderLocationLookupList;
            return result;
        }
    }

    /**
     * @return The WeatherInfo object supplied by the weather provider service
     */
    public WeatherInfo getWeatherInfo() {
        return mWeatherInfo;
    }

    /**
     * @return The list of WeatherLocation objects supplied by the weather provider service
     */
    public ArrayList<WeatherLocation> getLocationLookupList() {
        return mLocationLookupList;
    }
}
