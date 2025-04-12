package com.example.db_mdudash;

public final class DBContract {
    public static class Dormitories{
        public static final String TABLE_NAME = "dormitories";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ADDRESS = "address";
    }

    public static class Rooms{
        public static final String TABLE_NAME = "rooms";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DORMITORY_ID = "dormitory_id";
        public static final String COLUMN_NUMBER = "number";
        public static final String COLUMN_BEDS = "beds";
        public static final String COLUMN_EQUIPMENT = "equipment";
        public static final String COLUMN_AREA = "area";
        public static final String COLUMN_IS_OCCUPIED = "is_occupied";
        public static final String COLUMN_PRICE = "price";
    }
}
