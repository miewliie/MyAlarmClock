package com.augmentis.ayp.myalarmclock.Model;

/**
 * Created by Apinya on 8/24/2016.
 */
public class AClockDbSchema {


    public static final class AClockTable{

        public static final String NAME = "alarmClock";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String CHOUR = "hour";
            public static final String CMIN = "min";

        }

    }

}
