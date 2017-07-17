package com.agit.bp.mechanicbp.helper;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Mushlihun on 18/08/2016.
 */
public class SQLiteHelper {

	 /*	Table Timesheet	*/
    public static final String TABLE_TIMESHEET = "tbl_timesheet";
    public static final String COLUMN_ID_TIMESHEET = "id_timesheet";
    public static final String COLUMN_ID_DRIVER = "id_driver";
    public static final String COLUMN_LATITUDE_CLOCK_IN = "lat_clock_in";
    public static final String COLUMN_LONGITUDE_CLOCK_IN = "long_clock_in";
    public static final String COLUMN_DATE_CLOCK_IN = "datetime_clock_in";
    public static final String COLUMN_DATE_CLOCK_IN_EDIT = "datetime_clock_in_edit";
    public static final String COLUMN_LATITUDE_CLOCK_OUT = "lat_clock_out";
    public static final String COLUMN_LONGITUDE_CLOCK_OUT = "long_clock_out";
    public static final String COLUMN_DATE_CLOCK_OUT = "datetime_clock_out";
    public static final String COLUMN_DATE_CLOCK_OUT_EDIT = "datetime_clock_out_edit";
    public static final String COLUMN_FLAG_CLOCK = "flag_clock";
    public static final String COLUMN_WORK_DAY = "workday";
    public static final String COLUMN_EXPORT = "export";
    public static final String COLUMN_WORK_DAY_HOUR = "workday_hour";
    public static final String COLUMN_OVERTIME = "overtime";
    public static final String COLUMN_OVERTIME_EDIT = "overtime_edit";
    public static final String COLUMN_TIMESHEET_TYPE = "timesheet_type";
    public static final String COLUMN_TIMESHEET_TYPE_EDIT = "timesheet_type_edit";
    public static final String COLUMN_SYNC_IN = "syncIn";
    public static final String COLUMN_SYNC_OUT = "syncOut";
    /*public static final String COLUMN_SYNC_APPROVAL = "syncApproval";*/
    public static final String COLUMN_SYNC_REQUEST_CICO = "syncRcico";
    public static final String COLUMN_SYNC_REQUEST_OVERTIME = "syncRovertime";
    public static final String COLUMN_SYNC_REQUEST_TRIP_OLC = "syncTripOlc";
    public static final String COLUMN_ID_USER = "id_user";
    public static final String COLUMN_ID_LEADTIME = "id_leadtime";
    public static final String COLUMN_LEADTIME = "leadtime";
    public static final String COLUMN_APPROVAL_BY = "approval_by";
    public static final String COLUMN_APPROVAL_TIME = "approval_time";
    public static final String COLUMN_APPROVAL_STATUS = "approval_status";
    public static final String COLUMN_EDIT_STATUS = "edit_status";
    public static final String COLUMN_REASON = "reason";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_DATE_START_OVERTIME = "datetime_start_overtime";
    public static final String COLUMN_DATE_END_OVERTIME = "datetime_end_overtime";

    private static final String CREATE_TBL_TIMESHEET =
            "CREATE TABLE " + TABLE_TIMESHEET +
                    "(" +
                    COLUMN_ID_TIMESHEET + " TEXT PRIMARY KEY," +
                    COLUMN_ID_DRIVER + " TEXT," +
                    COLUMN_LATITUDE_CLOCK_IN + " TEXT," +
                    COLUMN_LONGITUDE_CLOCK_IN + " TEXT," +
                    COLUMN_DATE_CLOCK_IN + " TEXT," +
                    COLUMN_DATE_CLOCK_IN_EDIT + " TEXT," +
                    COLUMN_LATITUDE_CLOCK_OUT + " TEXT," +
                    COLUMN_LONGITUDE_CLOCK_OUT + " TEXT," +
                    COLUMN_DATE_CLOCK_OUT + " TEXT," +
                    COLUMN_DATE_CLOCK_OUT_EDIT + " TEXT," +
                    COLUMN_FLAG_CLOCK + " TEXT," +
                    COLUMN_WORK_DAY + " TEXT," +
                    COLUMN_EXPORT + " TEXT," +
                    COLUMN_WORK_DAY_HOUR + " TEXT," +
                    COLUMN_OVERTIME + " TEXT," +
                    COLUMN_OVERTIME_EDIT + " TEXT," +
                    COLUMN_TIMESHEET_TYPE + " TEXT," +
                    COLUMN_TIMESHEET_TYPE_EDIT + " TEXT," +
                    COLUMN_SYNC_IN + " TEXT," +
                    COLUMN_SYNC_OUT + " TEXT," +
                    COLUMN_SYNC_REQUEST_CICO + " TEXT," +
                    COLUMN_SYNC_REQUEST_OVERTIME + " TEXT," +
                    COLUMN_SYNC_REQUEST_TRIP_OLC + " TEXT," +
                    COLUMN_ID_USER + " TEXT," +
                    COLUMN_ID_LEADTIME + " TEXT," +
                    COLUMN_LEADTIME + " TEXT," +
                    COLUMN_APPROVAL_BY + " TEXT," +
                    COLUMN_APPROVAL_TIME + " TEXT," +
                    COLUMN_APPROVAL_STATUS + " TEXT," +
                    COLUMN_EDIT_STATUS+ " TEXT," +
                    COLUMN_REASON + " TEXT," +
                    COLUMN_USER_NAME + " TEXT," +
                    COLUMN_USER_EMAIL + " TEXT," +
                    COLUMN_DATE_START_OVERTIME + " TEXT," +
                    COLUMN_DATE_END_OVERTIME+ " TEXT" +
                    ")";

    public static final String TABLE_DRIVER = "tbl_driver";
    public static final String COLUMN_DV_ID_DRIVER = "id_driver";
    public static final String COLUMN_DV_NAME = "name";
    public static final String COLUMN_DV_PASSWORD = "password";
    public static final String COLUMN_DV_PIN_CODE = "pin_code";
    public static final String COLUMN_DV_LOGIN_STATUS = "login_status";
    private static final String CREATE_TBL_DRIVER =
            "CREATE TABLE " + TABLE_DRIVER +
                    "(" +
                    COLUMN_DV_ID_DRIVER + " TEXT PRIMARY KEY," +
                    COLUMN_DV_NAME + " TEXT," +
                    COLUMN_DV_PASSWORD + " TEXT," +
                    COLUMN_DV_PIN_CODE + " TEXT," +
                    COLUMN_DV_LOGIN_STATUS + " TEXT" +
                    ")";

    public static final String TABLE_USER = "tbl_user";
    public static final String COLUMN_CS_ID_USER = "id_user";
    public static final String COLUMN_CS_USER = "name";
    public static final String COLUMN_CS_EMAIL = "email";
    
    private static final String CREATE_TBL_USER =
            "CREATE TABLE " + TABLE_USER +
                    "(" +
                    COLUMN_CS_ID_USER + " TEXT PRIMARY KEY," +
                    COLUMN_CS_USER + " TEXT," +
                    COLUMN_CS_EMAIL + " TEXT" +
                    ")";

    public static final String TABLE_GLOBALPARAM = "tbl_globalparam";
    public static final String COLUMN_GP_ID_PARAM = "id_param";
    public static final String COLUMN_GP_PARAM = "param";
    public static final String COLUMN_GP_CATEGORY = "category";
    public static final String COLUMN_GP_VAL = "val";
    public static final String COLUMN_GP_FLAG = "flag";
    private static final String CREATE_TBL_GLOBALPARAM =
            "CREATE TABLE " + TABLE_GLOBALPARAM +
                    "(" +
                    COLUMN_GP_ID_PARAM + " TEXT PRIMARY KEY," +
                    COLUMN_GP_PARAM + " TEXT," +
                    COLUMN_GP_CATEGORY + " TEXT," +
                    COLUMN_GP_VAL + " TEXT," +
                    COLUMN_GP_FLAG + " TEXT" +
                    ")";

    public static final String TABLE_WORKDAY = "tbl_workday";
    public static final String COLUMN_WD_ID_WORKDAY = "workday_id";
    public static final String COLUMN_WD_WORKDAY = "workday";
    public static final String COLUMN_WD_HOUR = "hour";
    public static final String COLUMN_WD_FLAG = "flag";
    public static final String COLUMN_WD_DAYIDENTITY = "day_identity";
    private static final String CREATE_TBL_WORKDAY =
            "CREATE TABLE " + TABLE_WORKDAY +
                    "(" +
                    COLUMN_WD_ID_WORKDAY + " TEXT PRIMARY KEY," +
                    COLUMN_WD_WORKDAY + " TEXT," +
                    COLUMN_WD_HOUR + " TEXT," +
                    COLUMN_WD_FLAG + " TEXT," +
                    COLUMN_WD_DAYIDENTITY + " TEXT" +
                    ")";
    
    public static final String TABLE_REASON = "tbl_reason";
    public static final String COLUMN_RS_ID_REASON = "id_reason";
    public static final String COLUMN_RS_NAME = "name_reason";
    public static final String COLUMN_RS_FLAG = "flag";
    private static final String CREATE_TBL_REASON =
            "CREATE TABLE " + TABLE_REASON +
                    "(" +
                    COLUMN_RS_ID_REASON + " TEXT PRIMARY KEY," +
                    COLUMN_RS_NAME + " TEXT," +
                    COLUMN_RS_FLAG + " TEXT " +
                    ")";

    public static final String TABLE_TIMESHEETTYPE = "tbl_timesheet_type";
    public static final String COLUMN_TT_TIMESHEET_TYPE_ID = "timesheet_type_id";
    public static final String COLUMN_TT_TIMESHEET_TYPE = "timesheet_type";
    public static final String COLUMN_TT_TIMESHEET_REQUEST = "timesheet_request";
    public static final String COLUMN_TT_FLAG = "flag";
    private static final String CREATE_TBL_TIMESHEET_TYPE =
            "CREATE TABLE " + TABLE_TIMESHEETTYPE +
                    "(" +
                    COLUMN_TT_TIMESHEET_TYPE_ID + " TEXT PRIMARY KEY," +
                    COLUMN_TT_TIMESHEET_TYPE + " TEXT," +
                    COLUMN_TT_TIMESHEET_REQUEST + " TEXT," +
                    COLUMN_TT_FLAG + " TEXT" +
                    ")";
    
    public static final String TABLE_JENISREQUEST = "tbl_jenis_request";
    public static final String COLUMN_JR_JENISREQUEST_ID = "id_jenisrequest";
    public static final String COLUMN_JR_JENISREQUEST_TYPE = "type_request";
    public static final String COLUMN_JR_JENISREQUEST_NAME = "nama_request";
    public static final String COLUMN_JR_JENISREQUEST_FLAG = "flag";
    private static final String CREATE_TBL_JENISREQUEST =
            "CREATE TABLE " + TABLE_JENISREQUEST +
                    "(" +
                    COLUMN_JR_JENISREQUEST_ID + " TEXT PRIMARY KEY," +
                    COLUMN_JR_JENISREQUEST_TYPE + " TEXT," +
                    COLUMN_JR_JENISREQUEST_NAME + " TEXT," +
                    COLUMN_JR_JENISREQUEST_FLAG + " TEXT" +
                    ")";

    public static final String TABLE_TASKLIST = "tbl_tasklist";
    public static final String COLUMN_TL_ID_TASKLIST = "id_tasklist";
    public static final String COLUMN_TL_ID_TIMESHEET = "id_timesheet";
    public static final String COLUMN_TL_ID_DRIVER = "id_driver";
    public static final String COLUMN_TL_LOCATION = "location";
    public static final String COLUMN_TL_LAT_CHECK_IN = "lat_check_in";
    public static final String COLUMN_TL_LONG_CHECK_IN = "long_check_in";
    public static final String COLUMN_TL_DATETIME_CHECK_IN = "datetime_check_in";
    public static final String COLUMN_TL_LAT_COMPLETE = "lat_complete";
    public static final String COLUMN_TL_LONG_COMPLETE = "long_complete";
    public static final String COLUMN_TL_DATETIME_COMPLETE = "datetime_complete";
    public static final String COLUMN_TL_STATUS = "status";
    public static final String COLUMN_TL_SYNC_CREATE = "syncCreate";
    public static final String COLUMN_TL_SYNC_CHECKIN = "syncCheckin";
    public static final String COLUMN_TL_SYNC_COMPLETE = "syncComplete";
    public static final String COLUMN_TL_SYNC_DELETE = "syncDelete";
    public static final String COLUMN_TL_ID_USER = "id_user";
    public static final String COLUMN_TL_EXPORT = "export";
    public static final String CREATE_TBL_TASKLIST =
            "CREATE TABLE " + TABLE_TASKLIST +
                    "(" +
                    COLUMN_TL_ID_TASKLIST + " TEXT PRIMARY KEY," +
                    COLUMN_TL_ID_TIMESHEET + " TEXT," +
                    COLUMN_TL_ID_DRIVER + " TEXT," +
                    COLUMN_TL_LOCATION + " TEXT," +
                    COLUMN_TL_LAT_CHECK_IN + " TEXT," +
                    COLUMN_TL_LONG_CHECK_IN + " TEXT," +
                    COLUMN_TL_DATETIME_CHECK_IN + " TEXT," +
                    COLUMN_TL_LAT_COMPLETE + " TEXT," +
                    COLUMN_TL_LONG_COMPLETE + " TEXT," +
                    COLUMN_TL_DATETIME_COMPLETE + " TEXT," +
                    COLUMN_TL_STATUS + " TEXT," +
                    COLUMN_TL_SYNC_CREATE + " TEXT," +
                    COLUMN_TL_SYNC_CHECKIN + " TEXT," +
                    COLUMN_TL_SYNC_COMPLETE + " TEXT," +
                    COLUMN_TL_SYNC_DELETE + " TEXT," +
                    COLUMN_TL_ID_USER + " TEXT," +
                    COLUMN_TL_EXPORT + " TEXT" +
                    ")";

    public static final String TABLE_REQUEST_TRIP_OLC = "tbl_request_trip_olc";
    public static final String COLUMN_TR_ID_REQUEST = "id_request_tripolc";
    public static final String COLUMN_TR_ID_TIMESHEET = "id_timesheet";
    public static final String COLUMN_TR_ID_DRIVER = "id_driver";
    public static final String COLUMN_TR_DATE = "date";
    public static final String COLUMN_TR_SUM_TRIP = "sum_trip";
    public static final String COLUMN_TR_SUM_OLC = "sum_olc";
    public static final String COLUMN_TR_NAME_CITY = "city";
    public static final String COLUMN_TR_SYNC_IN_TRIP = "syncInTrip";
    public static final String COLUMN_TR_USER_NAME = "user_name";
    public static final String COLUMN_TR_USER_EMAIL = "user_email";
    public static final String COLUMN_TR_APPROVAL_STATUS = "approval_status";

    private static final String CREATE_TBL_REQUEST_TRIP_OLC =
            "CREATE TABLE " + TABLE_REQUEST_TRIP_OLC +
                    "(" +
                    COLUMN_TR_ID_REQUEST + " TEXT PRIMARY KEY," +
                    COLUMN_TR_ID_TIMESHEET + " TEXT," +
                    COLUMN_TR_ID_DRIVER + " TEXT," +
                    COLUMN_TR_DATE + " TEXT," +
                    COLUMN_TR_SUM_TRIP + " TEXT," +
                    COLUMN_TR_SUM_OLC + " TEXT," +
                    COLUMN_TR_NAME_CITY + " TEXT," +
                    COLUMN_TR_SYNC_IN_TRIP + " TEXT," +
                    COLUMN_TR_USER_NAME + " TEXT," +
                    COLUMN_TR_USER_EMAIL + " TEXT," +
                    COLUMN_TR_APPROVAL_STATUS + " TEXT" +
                    ")";
    
    public static final String TABLE_REQUEST_OVERTIME = "tbl_request_overtime";
    public static final String COLUMN_OV_ID_REQUEST = "id_request_overtime";
    public static final String COLUMN_OV_ID_TIMESHEET = "id_timesheet";
    public static final String COLUMN_OV_ID_DRIVER = "id_driver";
    public static final String COLUMN_OV_DATETIME_IN_BEFORE_OFFICE = "datetime_start_overtime_before_clockin";
    public static final String COLUMN_OV_DATETIME_OUT_BEFORE_OFFICE = "datetime_end_overtime_before_clockin";
    public static final String COLUMN_OV_USER_NAME = "user_name";
    public static final String COLUMN_OV_USER_EMAIL = "user_email";
    public static final String COLUMN_OV_DATETIME_IN_AFTER_OFFICE = "datetime_start_overtime_after_clockout";
    public static final String COLUMN_OV_DATETIME_OUT_AFTER_OFFICE = "datetime_end_overtime_after_clockout";
    public static final String COLUMN_OV_SYNC_OVERTIME = "sync_overtime";
    public static final String COLUMN_OV_APPROVAL_STATUS = "approval_status";
    public static final String COLUMN_OV_STATUS = "status";

    private static final String CREATE_TBL_REQUEST_OVERTIME =
            "CREATE TABLE " + TABLE_REQUEST_OVERTIME +
                    "(" +
                    COLUMN_OV_ID_REQUEST + " TEXT PRIMARY KEY," +
                    COLUMN_OV_ID_TIMESHEET + " TEXT," +
                    COLUMN_OV_ID_DRIVER + " TEXT," +
                    COLUMN_OV_DATETIME_IN_BEFORE_OFFICE + " TEXT," +
                    COLUMN_OV_DATETIME_OUT_BEFORE_OFFICE + " TEXT," +
                    COLUMN_OV_USER_NAME + " TEXT," +
                    COLUMN_OV_USER_EMAIL + " TEXT," +
                    COLUMN_OV_DATETIME_IN_AFTER_OFFICE + " TEXT," +
                    COLUMN_OV_DATETIME_OUT_AFTER_OFFICE + " TEXT," +
                    COLUMN_OV_SYNC_OVERTIME + " TEXT," +
                    COLUMN_OV_STATUS + " TEXT," +
                    COLUMN_OV_APPROVAL_STATUS + " TEXT" +
                     ")";

    public static void CREATE_TABLE(SQLiteDatabase db) {
        db.execSQL(CREATE_TBL_TIMESHEET);
        db.execSQL(CREATE_TBL_TASKLIST);
        db.execSQL(CREATE_TBL_DRIVER);
        db.execSQL(CREATE_TBL_USER);
        db.execSQL(CREATE_TBL_GLOBALPARAM);
        db.execSQL(CREATE_TBL_WORKDAY);
        db.execSQL(CREATE_TBL_TIMESHEET_TYPE);
        db.execSQL(CREATE_TBL_REQUEST_TRIP_OLC);
        db.execSQL(CREATE_TBL_REQUEST_OVERTIME);
        db.execSQL(CREATE_TBL_JENISREQUEST);
    }

    public static void DROP_TABLE(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMESHEET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRIVER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GLOBALPARAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKDAY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMESHEETTYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST_TRIP_OLC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST_OVERTIME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JENISREQUEST);
    }

    public static void DELETE_TABLE(SQLiteDatabase db, String userId) {
        String[] param = {userId};
        db.delete(TABLE_DRIVER, null, null);
        db.delete(TABLE_USER, null, null);
        db.delete(TABLE_GLOBALPARAM, null, null);
        db.delete(TABLE_WORKDAY, null, null);
        db.delete(TABLE_TIMESHEETTYPE, null, null);
        db.delete(TABLE_TIMESHEET, null, null);
        db.delete(TABLE_TASKLIST, null, null);
    }
}
