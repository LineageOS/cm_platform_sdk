/*
 * Copyright (C) 2016 The CyanogenMod Project
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

package cyanogenmod.providers;

import android.net.Uri;

/**
 * The contract between the weather provider and applications.
 */
public class WeatherContract {

    /**
     * The authority of the weather content provider
     */
    public static final String AUTHORITY = "com.cyanogenmod.weather";

    /**
     * A content:// style uri to the authority for the weather provider
     */
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public static class WeatherColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "weather");

        public static final Uri CURRENT_AND_FORECAST_WEATHER_URI
                = Uri.withAppendedPath(CONTENT_URI, "current_and_forecast");
        public static final Uri CURRENT_WEATHER_URI
                = Uri.withAppendedPath(CONTENT_URI, "current");
        public static final Uri FORECAST_WEATHER_URI
                = Uri.withAppendedPath(CONTENT_URI, "forecast");

        /**
         * A unique ID for the city. NOTE: this value fully depends on the implementation of the
         * weather provider service and can potentially change when you switch providers.
         * <P>Type: TEXT</P>
         */
        public static final String CURRENT_CITY_ID = "city_id";

        /**
         * The city name
         * <P>Type: TEXT</P>
         */
        public static final String CURRENT_CITY = "city";

        /**
         * A Valid {@link WeatherCode}
         * <P>Type: INTEGER</P>
         */
        public static final String CURRENT_CONDITION_CODE = "condition_code";

        /**
         * The current weather temperature
         * <P>Type: FLOAT</P>
         */
        public static final String CURRENT_TEMPERATURE = "temperature";

        /**
         * The unit in which current temperature is reported
         * <P>Type: INTEGER</P>
         * Can be one of the following:
         * <ul>
         * <li>{@link TempUnit#CELSIUS}</li>
         * <li>{@link TempUnit#FAHRENHEIT}</li>
         * </ul>
         */
        public static final String CURRENT_TEMPERATURE_UNIT = "temperature_unit";

        /**
         * The current weather humidity
         * <P>Type: FLOAT</P>
         */
        public static final String CURRENT_HUMIDITY = "humidity";

        /**
         * The current wind direction (in degrees)
         * <P>Type: FLOAT</P>
         */
        public static final String CURRENT_WIND_DIRECTION = "wind_direction";

        /**
         * The current wind speed
         * <P>Type: FLOAT</P>
         */
        public static final String CURRENT_WIND_SPEED = "wind_speed";

        /**
         * The unit in which the wind speed is reported
         * <P>Type: INTEGER</P>
         * Can be one of the following:
         * <ul>
         * <li>{@link WindSpeedUnit#KPH}</li>
         * <li>{@link WindSpeedUnit#MPH}</li>
         * </ul>
         */
        public static final String CURRENT_WIND_SPEED_UNIT = "wind_speed_unit";

        /**
         * The timestamp when this weather was reported
         * <P>Type: LONG</P>
         */
        public static final String CURRENT_TIMESTAMP = "timestamp";

        /**
         * The forecasted low temperature
         * <P>Type: FLOAT</P>
         */
        public static final String FORECAST_LOW = "forecast_low";

        /**
         * The forecasted high temperature
         * <P>Type: FLOAT</P>
         */
        public static final String FORECAST_HIGH = "forecast_high";

        /**
         * The code identifying the forecasted weather condition.
         * @see #CURRENT_CONDITION_CODE
         */
        public static final String FORECAST_CONDITION_CODE = "forecast_condition_code";

        /**
         * Temperature units
         */
        public static final class TempUnit {
            private TempUnit() {}
            public final static int CELSIUS = 1;
            public final static int FAHRENHEIT = 2;
        }

        /**
         * Wind speed units
         */
        public static final class WindSpeedUnit {
            private WindSpeedUnit() {}
            /**
             * Kilometers per hour
             */
            public final static int KPH = 1;

            /**
             * Miles per hour
             */
            public final static int MPH = 2;
        }

        /**
         * Weather condition codes
         */
        public static final class WeatherCode {
            private WeatherCode() {}

            public final static int TORNADO = 0;
            public final static int TROPICAL_STORM = 1;
            public final static int HURRICANE = 2;
            public final static int SEVERE_THUNDERSTORMS = 3;
            public final static int THUNDERSTORMS = 4;
            public final static int MIXED_RAIN_AND_SNOW = 5;
            public final static int MIXED_RAIN_AND_SLEET = 6;
            public final static int MIXED_SNOW_AND_SLEET = 7;
            public final static int FREEZING_DRIZZLE = 8;
            public final static int DRIZZLE = 9;
            public final static int FREEZING_RAIN = 10;
            public final static int SHOWERS = 11;
            //public final static int SHOWERS = 12;
            public final static int SNOW_FLURRIES = 13;
            public final static int LIGHT_SNOW_SHOWERS = 14;
            public final static int BLOWING_SNOW = 15;
            public final static int SNOW = 16;
            public final static int HAIL = 17;
            public final static int SLEET = 18;
            public final static int DUST = 19;
            public final static int FOGGY = 20;
            public final static int HAZE = 21;
            public final static int SMOKY = 22;
            public final static int BLUSTERY = 23;
            public final static int WINDY = 24;
            public final static int COLD = 25;
            public final static int CLOUDY = 26;
            public final static int MOSTLY_CLOUDY_NIGHT = 27;
            public final static int MOSTLY_CLOUDY_DAY = 28;
            public final static int PARTLY_CLOUDY_NIGHT = 29;
            public final static int PARTLY_CLOUDY_DAY = 30;
            public final static int CLEAR_NIGHT = 31;
            public final static int SUNNY = 32;
            public final static int FAIR_NIGHT = 33;
            public final static int FAIR_DAY = 34;
            public final static int MIXED_RAIN_AND_HAIL = 35;
            public final static int HOT = 36;
            public final static int ISOLATED_THUNDERSTORMS = 37;
            public final static int SCATTERED_THUNDERSTORMS = 38;
            //public final static int SCATTERED_THUNDERSTORMS = 39;
            public final static int SCATTERED_SHOWERS = 40;
            public final static int HEAVY_SNOW = 41;
            public final static int SCATTERED_SNOW_SHOWERS = 42;
            //public final static int HEAVY_SNOW = 43;
            public final static int PARTLY_CLOUDY = 44;
            public final static int THUNDERSHOWER = 45;
            public final static int SNOW_SHOWERS = 46;
            public final static int ISOLATED_THUNDERSHOWERS = 47;
            public final static int NOT_AVAILABLE =3200;
        }
    }
}