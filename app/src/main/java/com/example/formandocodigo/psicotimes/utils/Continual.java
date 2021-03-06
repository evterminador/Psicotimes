package com.example.formandocodigo.psicotimes.utils;

/**
 * Created by FormandoCodigo on 10/12/2017.
 */

public final class Continual {

    public static final class Net {
        public static final String BASE_URL = "http://addictphones.com/api/";
    }

    public static final class Shared {
        public static final String DEFAULT_FILE_NAME = "user_";

        public static final class LockScreen {
            public static final String FILE_NAME = "screen_";
            public static final String KEY_SCREEN = "screen";
            public static final String KEY_CREATED_AT = "created_at";
        }
    }

    public static final class SQLite {
        public static final String DEFAULT_FILE_NAME = "psicotimes";

        public static final class App {
            public final String TABLE_NAME = "application";
            public final String COLUMN_ID = "id";
            public final String COLUMN_NAME = "name";
            public final String COLUMN_RELEVANCE = "relevance";
            public final String COLUMN_IMAGE = "image";
            public final String COLUMN_DESCRIPTION = "description";
            public final String COLUMN_CREATED_AT = "created_at";
            public final String COLUMN_UPDATED_AT = "updated_at";
        }

        public static final class StateUse {
            public final String TABLE_NAME = "state_uses";
            public final String COLUMN_USER_ID = "user_id";
            public final String COLUMN_APP_ID = "app_id";
            public final String COLUMN_TIME_USE = "time_use";
            public final String COLUMN_QUANTITY = "quantity";
            public final String COLUMN_LAST_USE_TIME = "last_use_time";
            public final String COLUMN_CREATED_AT = "created_at";
            public final String COLUMN_UPDATED_AT = "updated_at";
        }

        public static final class Statistics {
            public final String TABLE_NAME = "statistics";
            public final String COLUMN_ID = "id";
            public final String COLUMN_NAME_APP_TOP = "name_app_top";
            public final String COLUMN_QUANTITY = "quantity";
            public final String COLUMN_TIME_USE = "time_use";
            public final String COLUMN_CREATED_AT = "created_at";
            public final String COLUMN_UPDATED_AT = "updated_at";
        }
    }
}
