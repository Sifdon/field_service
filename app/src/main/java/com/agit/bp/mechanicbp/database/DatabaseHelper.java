package com.agit.bp.mechanicbp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.agit.bp.mechanicbp.models.ModelHistoryActivity;
import com.agit.bp.mechanicbp.models.ModelNotification;
import com.agit.bp.mechanicbp.models.ModelWOHeader;
import com.agit.bp.mechanicbp.models.ModelWOTakslist;
import com.agit.bp.mechanicbp.models.ResponseUserMechanic;

import java.util.ArrayList;
import java.util.List;
//import com.example.mas.myapplicationsqlite.database.StructureData.TaskList;
//import com.example.mas.myapplicationsqlite.database.StructureData.DetailTaskList;

/**
 * Created by NiyatiR on 7/20/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MECHANIC";

    //TABLES//
    private static final String TABLE_USER = "TABLE_USER";
    private static final String TABLE_WO = "TABLE_WO";
    private static final String TABLE_TASKLIST = "TABLE_TASKLIST";
    private static final String TABLE_NOTIFICATION = "TABLE_NOTIFICATION";
    private static final String TABLE_HISTORY_ACTIVITY = "TABLE_HISTORY_ACTIVITY";
    //COLOUMN//

    //COLOUMN TABLE_USER
    private static final String USER_ID = "mechanic_id";
    private static final String USER_NAME = "mechanic_name";
    private static final String USER_PHONE = "mechanic_phone";
    private static final String USER_PLANTCODE = "mechanic_plantcode";
    private static final String USER_USERNAME = "mechanic_username";
    private static final String USER_PASSWORD = "mechanic_password";
    private static final String USER_IMEI = "mechanic_imei";
    private static final String USER_TOKEN = "mechanic_token";
    private static final String USER_STATUSTOKEN = "mechanic_statustoken";
    private static final String USER_DATETIME = "mechanic_datetime";

    //COLOUMN TABLE_WO
    private static final String WO_Id = "id";
    private static final String WO_orderAssignmentId = "orderAssignmentId";
    private static final String WO_orderHeaderId = "orderHeaderId";
    private static final String WO_soNumber = "soNumber";
    private static final String WO_orderDate = "orderDate";
    private static final String WO_startDate = "startDate";
    private static final String WO_endDate = "endDate";
    private static final String WO_customerName = "customerName";
    private static final String WO_address = "address";
    private static final String WO_material = "material";
    private static final String WO_serialNumber = "serialNumber";
    private static final String WO_warranty = "warranty";
    private static final String WO_remark = "remark";
    private static final String WO_workCenter = "workCenter";
    private static final String WO_contactPerson = "contactPerson";
    private static final String WO_phone = "phone";
    private static final String WO_orderType = "orderType";
    private static final String WO_estimateTime = "estimateTime";
    private static final String WO_sparepart = "sparepart";
    private static final String WO_plant = "plant";
    private static final String WO_orderStatus = "orderStatus";
    private static final String WO_orderStatusName = "orderStatusName";
    private static final String WO_mechanicId = "mechanicId";
    private static final String WO_actionDate = "actionDate";
    //private static final String WO_readNotif = "readNotif";

    //tasklist
    private static final String TASKLIST_orderiItemId = "orderiItemId";
    private static final String TASKLIST_orderHeaderId = "orderHeaderId";
    private static final String TASKLIST_taskList = "taskList";
    private static final String TASKLIST_orderItemStatus = "orderItemStatus";
    private static final String TASKLIST_orderItemStatusName = "orderItemStatusName";
    private static final String TASKLIST_actionDate = "actionDate";
    //private static final String TASKLIST_workinghours = "workingHours";

    //notification
    private static final String NOTIFICATION_id = "id";
    private static final String NOTIFICATION_orderHeaderId = "orderHeaderId";
    private static final String NOTIFICATION_soNumber = "soNumber";
    private static final String NOTIFICATION_customerName = "customerName";
    private static final String NOTIFICATION_title = "title";
    private static final String NOTIFICATION_readNotif = "readNotif";

    //history activity
    private static final String HA_id = "id";
    private static final String HA_type = "type";
    private static final String HA_orderHeaderId = "orderHeaderId";
    private static final String HA_json = "json";
    private static final String HA_path = "path";
    private static final String HA_flag = "flag";

    //feedback
    private static final String FEEDBACK_orderHeaderId = "orderHeaderId";
    private static final String FEEDBACK_workresult = "workResult";
    private static final String FEEDBACK_score = "score";
    private static final String FEEDBACK_comment = "comment";
    private static final String FEEDBACK_recipientname = "recipientName";
    private static final String FEEDBACK_pathsignature = "recipientSignature";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //agar hilang error extend maka implementasikan methodnya
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
                + USER_ID + " TEXT PRIMARY KEY,"
                + USER_NAME + " TEXT,"
                + USER_PHONE + " TEXT,"
                + USER_PLANTCODE + " TEXT,"
                + USER_USERNAME + " TEXT,"
                + USER_PASSWORD + " TEXT,"
                + USER_IMEI + " TEXT,"
                + USER_TOKEN + " TEXT,"
                + USER_STATUSTOKEN + " TEXT,"
                + USER_DATETIME + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_USER);

        //create tabel WO
        String CREATE_TABLE_WO = "CREATE TABLE " + TABLE_WO + "("
                + WO_Id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WO_orderAssignmentId + " TEXT,"
                + WO_orderHeaderId + " TEXT,"
                + WO_soNumber + " TEXT,"
                + WO_orderDate + " TEXT,"
                + WO_startDate + " TEXT,"
                + WO_endDate + " TEXT,"
                + WO_customerName + " TEXT,"
                + WO_address + " TEXT,"
                + WO_material + " TEXT,"
                + WO_serialNumber + " TEXT,"
                + WO_warranty + " TEXT,"
                + WO_remark + " TEXT,"
                + WO_workCenter + " TEXT,"
                + WO_contactPerson + " TEXT,"
                + WO_phone + " TEXT,"
                + WO_orderType + " TEXT,"
                + WO_estimateTime + " TEXT,"
                + WO_sparepart + " TEXT,"
                + WO_plant + " TEXT,"
                + WO_orderStatus + " INTEGER,"
                + WO_orderStatusName + " TEXT,"
                + WO_mechanicId + " TEXT,"
                + WO_actionDate + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_WO);

        //create tabel Tasklist
        String CREATE_TABLE_TASKLIST = "CREATE TABLE " + TABLE_TASKLIST + "("
                + TASKLIST_orderHeaderId + " TEXT,"
                + TASKLIST_orderiItemId + " TEXT,"
                + TASKLIST_taskList + " TEXT,"
                + TASKLIST_orderItemStatus + " TEXT,"
                + TASKLIST_orderItemStatusName + " TEXT,"
                //+ TASKLIST_actionDate + " TEXT,"
                + TASKLIST_actionDate + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_TASKLIST);

        //create table HA
        String CREATE_TABLE_HISTORY_ACTIVITY = "CREATE TABLE " + TABLE_HISTORY_ACTIVITY + "("
                + HA_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + HA_type + " TEXT,"
                + HA_orderHeaderId + " TEXT,"
                + HA_json + " TEXT,"
                + HA_path + " TEXT,"
                + HA_flag + " INTEGER" + ")";
        db.execSQL(CREATE_TABLE_HISTORY_ACTIVITY);


        //create table HA
        String CREATE_TABLE_NOTIFICATION = "CREATE TABLE " + TABLE_NOTIFICATION + "("
                + NOTIFICATION_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NOTIFICATION_orderHeaderId + " TEXT,"
                + NOTIFICATION_soNumber + " TEXT,"
                + NOTIFICATION_customerName + " TEXT,"
                + NOTIFICATION_title + " TEXT,"
                + NOTIFICATION_readNotif + " INTEGER" + ")";
        db.execSQL(CREATE_TABLE_NOTIFICATION);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);

        // Create tables again
        onCreate(db);
    }

    public void writeUserMechanic(ResponseUserMechanic user) {
        //Log.e("MASUK INSERT DATA user", "MASUK INSERT DATA user");
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(USER_ID, user.getStaffId());
            values.put(USER_NAME, user.getStaffName());

            values.put(USER_PHONE, user.getPhone());
            values.put(USER_PLANTCODE, user.getPlantcode());

            values.put(USER_USERNAME, user.getUsername());
            values.put(USER_PASSWORD, user.getPassword());
            values.put(USER_IMEI, user.getImei());
            values.put(USER_TOKEN, user.getToken());
            values.put(USER_STATUSTOKEN, user.getStatusToken());
            values.put(USER_DATETIME, user.getDateTime());

            db.insert(TABLE_USER, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
    }

    public ResponseUserMechanic login(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase(); //membuka database
        Cursor cursor = db.query(TABLE_USER, new String[]{ //menjalankan query
                        USER_ID,
                        USER_NAME
                }, USER_USERNAME + "= ? AND " + USER_PASSWORD + "= ?", //diisi parameter di bawahnya ini untuk dicari lewat string user
                new String[]{
                        username,
                        password
                }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        ResponseUserMechanic user = new ResponseUserMechanic();
        try {
            user.setStaffId(cursor.getString(cursor.getColumnIndex("" + USER_ID)));
            user.setStaffName(cursor.getString(cursor.getColumnIndex("" + USER_NAME)));
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    public ResponseUserMechanic logout(String staffid) {
        SQLiteDatabase db = this.getWritableDatabase(); //membuka database
        Cursor cursor = db.query(TABLE_USER, new String[]{ //menjalankan query
                        USER_USERNAME,
                        USER_PASSWORD
                }, USER_ID + "= ?", //diisi parameter di bawahnya ini untuk dicari lewat string user
                new String[]{
                        staffid
                }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        ResponseUserMechanic user = new ResponseUserMechanic();
        try {
            user.setUsername(cursor.getString(cursor.getColumnIndex("" + USER_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex("" + USER_PASSWORD)));
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    public long deleteUserMechanic(String staffid) {
        //DIKASIH try catch
        long result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_USER, USER_ID + " = '" + staffid + "'", null);
        db.close();
        return result;
    }

    public boolean checkIDWO(String ID) {
        //boolean check = true;
        SQLiteDatabase db = this.getWritableDatabase(); //membuka database
        Cursor cursor = db.query(TABLE_WO, new String[]{ //menjalankan query
                        WO_orderHeaderId
                }, WO_orderHeaderId + "= ?", //diisi parameter di bawahnya ini untuk dicari lewat string user
                new String[]{
                        ID
                }, null, null, null, null);

        if (cursor.getCount() <= 0) {
            //Log.e("masuk checkIDWO : ", "" + cursor.getCount());
            return false;
        } else {
            //Log.e("masuk checkIDWO : ", "" + cursor.getCount());
            return true;
        }
    }

    public void writeWO(ModelWOHeader data) {
        //Toast.makeText(get, "masuk insert data", Toast.LENGTH_SHORT).show();
        //Log.e("MASUK INSERT DATA WO", "MASUK INSERT DATA WO");
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(WO_orderAssignmentId, data.getOrderAssignmentId());
            values.put(WO_orderHeaderId, data.getOrderHeaderId());
            values.put(WO_soNumber, data.getSoNumber());
            values.put(WO_orderDate, data.getOrderDateString());
            values.put(WO_startDate, data.getStartDateString());
            values.put(WO_endDate, data.getEndDateString());
            values.put(WO_customerName, data.getCustomerName());
            values.put(WO_address, data.getAddress());
            values.put(WO_material, data.getMaterial());
            values.put(WO_serialNumber, data.getSerialNumber());
            values.put(WO_warranty, data.getWarranty());
            values.put(WO_remark, data.getRemark());
            values.put(WO_workCenter, data.getWorkCenter());
            values.put(WO_contactPerson, data.getContactPerson());
            values.put(WO_phone, data.getPhone());
            values.put(WO_orderType, data.getOrderType());
            values.put(WO_estimateTime, data.getEstimateTime());
            values.put(WO_sparepart, data.getSparepart());
            values.put(WO_plant, data.getPlant());
            values.put(WO_orderStatus, data.getOrderStatus());
            values.put(WO_orderStatusName, data.getOrderStatusName());
            values.put(WO_mechanicId, data.getMechanicId());
            values.put(WO_actionDate, data.getActionDate());
            //values.put(WO_readNotif, 0);
            // Inserting Row
            db.insert(TABLE_WO, null, values);

            //insert to notification
            if (data.getOrderStatus() == 5070 || data.getOrderStatus() == 5100) {
                ModelNotification data_notif = new ModelNotification();
                data_notif.setOrderHeaderId(data.getOrderHeaderId());
                data_notif.setSoNumber(data.getSoNumber());
                data_notif.setCustomerName(data.getCustomerName());
                data_notif.setTitle("New Work Order Assignment");
                //data_notif.setReadNotif(0); //no need cause default insert is 0 for notif
                Log.e("write notif", "writeWO");
                writeNotification(data_notif);
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
    }

    public void writeTasklist(ModelWOTakslist data) {
        //Toast.makeText(get, "masuk insert data", Toast.LENGTH_SHORT).show();
        long result = 0;
        //Log.e("MASUK INSERT Tasklist", "MASUK INSERT Tasklist");
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(TASKLIST_orderiItemId, data.getOrderiItemId());
            values.put(TASKLIST_orderHeaderId, data.getOrderHeaderId());
            values.put(TASKLIST_taskList, data.getTaskList());
            values.put(TASKLIST_orderItemStatus, data.getOrderItemStatus());
            values.put(TASKLIST_orderItemStatusName, data.getOrderItemStatusName());
            //values.put(TASKLIST_actionDate, data.getActionDate().toString());
            //values.put(TASKLIST_workinghours, data.getWorkinghours());
            // Inserting Row
            result = db.insert(TABLE_TASKLIST, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            //Log.e("isi result tasklist : ", "" + result);
            db.endTransaction();
        }
    }

    public int updateStatusWO(String WO_ID, int ID_STATUS, String STATUS_NAME) {
        int rowsUpdated = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(WO_orderStatus, ID_STATUS);
            values.put(WO_orderStatusName, STATUS_NAME);
            rowsUpdated = db.update(TABLE_WO, values, WO_orderHeaderId + "='" + WO_ID + "'", null);
            //Log.e("isi rowsUpdated", "" + rowsUpdated);
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
        return rowsUpdated;
    }


    public int updateStatusWOApproval(String WO_ID, int ID_STATUS, String STATUS_NAME) {
        ModelWOHeader dataWO = new ModelWOHeader();
        dataWO = getWO_Detail(WO_ID);
        int rowsUpdated = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(WO_orderStatus, ID_STATUS);
            values.put(WO_orderStatusName, STATUS_NAME);
            rowsUpdated = db.update(TABLE_WO, values, WO_orderHeaderId + "='" + WO_ID + "' AND " + WO_orderStatus + " = 5054", null);
            Log.e("updateStatusWOApproval", "" + rowsUpdated);

            //updateNotif
            if(rowsUpdated == 1) {
                ModelNotification data_notif = new ModelNotification();
                data_notif.setOrderHeaderId(WO_ID);
                data_notif.setSoNumber(dataWO.getSoNumber());
                data_notif.setCustomerName(dataWO.getCustomerName());
                data_notif.setTitle("Approval Admin");
                Log.e("write notif", "updateStatusWOApproval");
                writeNotification(data_notif);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
        return rowsUpdated;
    }

    public List<ModelWOHeader> getAllWO(String status) {
        List<ModelWOHeader> wo = new ArrayList<>();
        // Select All Query
        String selectQuery = "";
        if (status.equals("onprocess")) {
            selectQuery = "SELECT  * FROM " + TABLE_WO + " WHERE " + WO_orderStatus + " NOT IN (5070, 5100) ORDER BY " + WO_Id + " DESC";
        } else if (status.equals("new")) {
            selectQuery = "SELECT  * FROM " + TABLE_WO + " WHERE " + WO_orderStatus + " IN (5070, 5100) ORDER BY " + WO_Id + " DESC";
        } else if (status.equals("approval")) {
            selectQuery = "SELECT  * FROM " + TABLE_WO + " WHERE " + WO_orderStatus + " = 5055 ORDER BY " + WO_Id + " DESC";
        } else if (status.equals("all")) {
            selectQuery = "SELECT  * FROM " + TABLE_WO;
        }
        //selectQuery = "SELECT  * FROM " + TABLE_WO;
        Log.e("ISI QUERY : ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelWOHeader data = new ModelWOHeader();
                data.setOrderAssignmentId(cursor.getString(cursor.getColumnIndex("" + WO_orderAssignmentId)));
                data.setOrderHeaderId(cursor.getString(cursor.getColumnIndex("" + WO_orderHeaderId)));
                data.setSoNumber(cursor.getString(cursor.getColumnIndex("" + WO_soNumber)));
                data.setOrderDate(cursor.getString(cursor.getColumnIndex("" + WO_orderDate)));
                data.setStartDate(cursor.getString(cursor.getColumnIndex("" + WO_startDate)));
                data.setEndDate(cursor.getString(cursor.getColumnIndex("" + WO_endDate)));
                data.setCustomerName(cursor.getString(cursor.getColumnIndex("" + WO_customerName)));
                data.setAddress(cursor.getString(cursor.getColumnIndex("" + WO_address)));
                data.setMaterial(cursor.getString(cursor.getColumnIndex("" + WO_material)));
                data.setSerialNumber(cursor.getString(cursor.getColumnIndex("" + WO_serialNumber)));
                data.setWarranty(cursor.getString(cursor.getColumnIndex("" + WO_warranty)));
                data.setRemark(cursor.getString(cursor.getColumnIndex("" + WO_remark)));
                data.setWorkCenter(cursor.getString(cursor.getColumnIndex("" + WO_workCenter)));
                data.setContactPerson(cursor.getString(cursor.getColumnIndex("" + WO_contactPerson)));
                data.setPhone(cursor.getString(cursor.getColumnIndex("" + WO_phone)));
                data.setOrderType(cursor.getString(cursor.getColumnIndex("" + WO_orderType)));
                data.setEstimateTime(cursor.getString(cursor.getColumnIndex("" + WO_estimateTime)));
                data.setSparepart(cursor.getString(cursor.getColumnIndex("" + WO_sparepart)));
                data.setPlant(cursor.getString(cursor.getColumnIndex("" + WO_plant)));
                data.setOrderStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex("" + WO_orderStatus))));
                data.setOrderStatusName(cursor.getString(cursor.getColumnIndex("" + WO_orderStatusName)));
                //Log.e("isi status name", "" + data.getOrderStatus());
                data.setMechanicId(cursor.getString(cursor.getColumnIndex("" + WO_mechanicId)));
                data.setActionDate(cursor.getString(cursor.getColumnIndex("" + WO_actionDate)));
                wo.add(data);
            } while (cursor.moveToNext());
        }
        db.endTransaction();
        return wo;
    }

    public List<ModelWOTakslist> getAllTasklist(String WO_ID) {
        List<ModelWOTakslist> tasklist = new ArrayList<>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_TASKLIST;
        String selectQuery = "SELECT  * FROM " + TABLE_TASKLIST + " WHERE " + TASKLIST_orderHeaderId + " = '" + WO_ID + "'";
        Log.e("ISI QUERY : ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelWOTakslist data = new ModelWOTakslist();
                data.setOrderiItemId(cursor.getString(cursor.getColumnIndex("" + TASKLIST_orderiItemId)));
                data.setOrderHeaderId(cursor.getString(cursor.getColumnIndex("" + TASKLIST_orderHeaderId)));
                //Log.e("isi item tasklist :", "" + cursor.getString(cursor.getColumnIndex("" + TASKLIST_orderHeaderId)));
                data.setTaskList(cursor.getString(cursor.getColumnIndex("" + TASKLIST_taskList)));
                data.setOrderItemStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex("" + TASKLIST_orderItemStatus))));
                data.setOrderItemStatusName(cursor.getString(cursor.getColumnIndex("" + TASKLIST_orderItemStatusName)));
                data.setActionDate(cursor.getString(cursor.getColumnIndex("" + TASKLIST_actionDate)));
                //data.setWorkinghours(cursor.getString(cursor.getColumnIndex("" + TASKLIST_workinghours)));
                tasklist.add(data);
            } while (cursor.moveToNext());
        }
        db.endTransaction();
        return tasklist;
    }

    public List<ModelWOHeader> getRecentStatusWO(String WO_ID) {
        List<ModelWOHeader> wo = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WO + " WHERE " + WO_orderHeaderId + " = '" + WO_ID + "'";
        //selectQuery = "SELECT  * FROM " + TABLE_WO;
        Log.e("ISI QUERY : ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelWOHeader data = new ModelWOHeader();
                data.setOrderAssignmentId(cursor.getString(cursor.getColumnIndex("" + WO_orderAssignmentId)));
                data.setOrderHeaderId(cursor.getString(cursor.getColumnIndex("" + WO_orderHeaderId)));
                data.setSoNumber(cursor.getString(cursor.getColumnIndex("" + WO_soNumber)));
                data.setOrderDate(cursor.getString(cursor.getColumnIndex("" + WO_orderDate)));
                data.setStartDate(cursor.getString(cursor.getColumnIndex("" + WO_startDate)));
                data.setEndDate(cursor.getString(cursor.getColumnIndex("" + WO_endDate)));
                data.setCustomerName(cursor.getString(cursor.getColumnIndex("" + WO_customerName)));
                data.setAddress(cursor.getString(cursor.getColumnIndex("" + WO_address)));
                data.setMaterial(cursor.getString(cursor.getColumnIndex("" + WO_material)));
                data.setSerialNumber(cursor.getString(cursor.getColumnIndex("" + WO_serialNumber)));
                data.setWarranty(cursor.getString(cursor.getColumnIndex("" + WO_warranty)));
                data.setRemark(cursor.getString(cursor.getColumnIndex("" + WO_remark)));
                data.setWorkCenter(cursor.getString(cursor.getColumnIndex("" + WO_workCenter)));
                data.setContactPerson(cursor.getString(cursor.getColumnIndex("" + WO_contactPerson)));
                data.setPhone(cursor.getString(cursor.getColumnIndex("" + WO_phone)));
                data.setOrderType(cursor.getString(cursor.getColumnIndex("" + WO_orderType)));
                data.setEstimateTime(cursor.getString(cursor.getColumnIndex("" + WO_estimateTime)));
                data.setSparepart(cursor.getString(cursor.getColumnIndex("" + WO_sparepart)));
                data.setPlant(cursor.getString(cursor.getColumnIndex("" + WO_plant)));
                data.setOrderStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex("" + WO_orderStatus))));
                data.setOrderStatusName(cursor.getString(cursor.getColumnIndex("" + WO_orderStatusName)));
                //Log.e("isi status name", "" + data.getOrderStatusName());
                data.setMechanicId(cursor.getString(cursor.getColumnIndex("" + WO_mechanicId)));
                data.setActionDate(cursor.getString(cursor.getColumnIndex("" + WO_actionDate)));
                wo.add(data);
            } while (cursor.moveToNext());
        }
        db.endTransaction();
        return wo;
    }

    public long deleteWO(String WO_ID) {
        long result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            result = db.delete(TABLE_WO, WO_orderHeaderId + " = '" + WO_ID + "'", null);
            deleteWOTasklist(WO_ID);
            deleteAllWOHistoryActivity(WO_ID);
            deleteAllWONotification(WO_ID);
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
        return result;
    }

    public long deleteWOTasklist(String WO_ID) {
        //DIKASIH try catch
        long result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            result = db.delete(TABLE_TASKLIST, TASKLIST_orderHeaderId + " = '" + WO_ID + "'", null);
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
        return result;
    }

    public int checkOnProcessWO(String WO_ID) {
        int size = 0;
        //boolean check = true;
        SQLiteDatabase db = this.getWritableDatabase(); //membuka database
        String selectQuery = "SELECT " + WO_orderHeaderId + " FROM " + TABLE_WO
                + " WHERE " + WO_orderStatus + " NOT IN (5070, 5100, 5099, 5060, 5062, 5054, 5058, 5065, 5056, 5068, 5069) AND "
                + WO_orderHeaderId + " != '" + WO_ID + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        //(5070, 5100, 5099, 5060, 5061, 5062, 5054, 5058, 5065)

        if (cursor.getCount() <= 0) {
            //Log.e("masuk checkIDWO : ", "" + cursor.getCount());
            size = 0;
            return size;
        } else {
            //Log.e("masuk checkIDWO : ", "" + cursor.getCount());
            size = cursor.getCount();
            return size;
        }
    }


    public int updateStatusTasklist(String WO_ID, String TL_ID, int ID_STATUS, String STATUS_NAME, String actionTime) {

        ModelWOHeader dataWO = new ModelWOHeader();
        dataWO = getWO_Detail(WO_ID);

        //Log.e("TL_ID : ", "" + ID_STATUS + "/" + STATUS_NAME);
//        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
//        Log.e("isi TL formatter", "" + formatter.format(actionTime).toString());
//        formatter.parse(actionTime).toString();
//        actionTime = formatter.format(actionTime).toString();

        int rowsUpdated = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();

            if(ID_STATUS == 7004){
                ContentValues values = new ContentValues();
                values.put(TASKLIST_orderItemStatus, ID_STATUS);
                values.put(TASKLIST_orderItemStatusName, STATUS_NAME);
                //values.put(TASKLIST_workinghours, WORKING_HOURS);
                values.put(TASKLIST_actionDate, actionTime);
                rowsUpdated = db.update(TABLE_TASKLIST, values, TASKLIST_orderItemStatus + " != 7004 AND " + TASKLIST_orderiItemId + "='" + TL_ID + "'", null);
                //rowsUpdated = db.update(TABLE_TASKLIST, values, TASKLIST_orderItemStatus + " != 7004 AND " + TASKLIST_orderHeaderId + " = '" + WO_ID + "'", null);
                Log.e("rowsUpdated TL", "" + rowsUpdated + " " +TL_ID);

            }
            //updateNotif
            if(rowsUpdated == 1) {
                int countcompleteTL = checkTasklistTotalCompleted(WO_ID);
                int countAllTL = checkTasklistCompleted(WO_ID) + countcompleteTL;
                ModelNotification data_notif = new ModelNotification();
                data_notif.setOrderHeaderId(WO_ID);
                data_notif.setSoNumber(dataWO.getSoNumber());
                data_notif.setCustomerName(dataWO.getCustomerName());
                data_notif.setTitle("" + countcompleteTL + " Of " + countAllTL + " Tasklist Completed");
                Log.e("write notif", "updateStatusTasklist");
                writeNotification(data_notif);
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
        return rowsUpdated;
    }

    public int updateStatusTasklistAll(int ID_STATUS, String STATUS_NAME, String WO_ID) {
        int rowsUpdated = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(TASKLIST_orderItemStatus, ID_STATUS);
            values.put(TASKLIST_orderItemStatusName, STATUS_NAME);
            rowsUpdated = db.update(TABLE_TASKLIST, values, TASKLIST_orderItemStatus + " != 7004 AND " + TASKLIST_orderHeaderId + " = '" + WO_ID + "'", null);
            //Log.e("rowsUpdated TL All", "" + rowsUpdated);
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
        return rowsUpdated;
    }

    public int checkTasklistCompleted(String WO_ID) {
        int size = 0;
        //boolean check = true;
        SQLiteDatabase db = this.getWritableDatabase(); //membuka database
        String selectQuery = "SELECT " + TASKLIST_orderiItemId + " FROM " + TABLE_TASKLIST
                + " WHERE " + TASKLIST_orderItemStatus + " != 7004 AND " + TASKLIST_orderHeaderId + " = '" + WO_ID + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() <= 0) {
            size = 0;
            return size;
        } else {
            size = cursor.getCount();
            return size;
        }
    }

    public int checkTasklistTotalCompleted(String WO_ID) {
        int size = 0;
        //boolean check = true;
        SQLiteDatabase db = this.getWritableDatabase(); //membuka database
        String selectQuery = "SELECT " + TASKLIST_orderiItemId + " FROM " + TABLE_TASKLIST
                + " WHERE " + TASKLIST_orderItemStatus + " = 7004 AND " + TASKLIST_orderHeaderId + " = '" + WO_ID + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() <= 0) {
            size = 0;
            return size;
        } else {
            size = cursor.getCount();
            return size;
        }
    }


    public long deleteAllWO() {
        //DIKASIH try catch
        long result = 0;
        long result2 = 0;
        long result3 = 0;
        long result4 = 0;
        SQLiteDatabase db = this.getWritableDatabase();
//        result = db.delete(TABLE_WO, null, null);
//        result2 = deleteAllWOTasklist();
//        result3 = deleteAllHistoryActivity();
//        result4 = deleteAllNotification();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        onCreate(db);
        return result + result2 + result3 + result4;
    }

    public long deleteAllWOTasklist() {
        //DIKASIH try catch
        long result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_TASKLIST, null, null);
        db.close();
        return result;
    }

    public long writeNotification(ModelNotification data) {

        deleteAllWONotification(data.getOrderHeaderId());

        long result = 0;
        Log.e("MASUK INSERT Notif", "MASUK INSERT Notif");
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(NOTIFICATION_orderHeaderId, data.getOrderHeaderId());
            values.put(NOTIFICATION_soNumber, data.getSoNumber());
            values.put(NOTIFICATION_customerName, data.getCustomerName());
            values.put(NOTIFICATION_title, data.getTitle());
            values.put(NOTIFICATION_readNotif, 0);
            // Inserting Row
            result = db.insert(TABLE_NOTIFICATION, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            Log.e("isi result tasklist : ", "" + result);
            db.endTransaction();
        }
        return result;
    }

    public List<ModelNotification> getAllNotification() {
        List<ModelNotification> notif = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATION + " ORDER BY " + NOTIFICATION_id + " DESC";

        //selectQuery = "SELECT  * FROM " + TABLE_WO;
        Log.e("ISI QUERY : ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelNotification data = new ModelNotification();
                data.setOrderHeaderId(cursor.getString(cursor.getColumnIndex("" + NOTIFICATION_orderHeaderId)));
                data.setSoNumber(cursor.getString(cursor.getColumnIndex("" + NOTIFICATION_soNumber)));
                data.setCustomerName(cursor.getString(cursor.getColumnIndex("" + NOTIFICATION_customerName)));
                data.setTitle(cursor.getString(cursor.getColumnIndex("" + NOTIFICATION_title)));
                //data.setReadNotif(cursor.getInt(cursor.getColumnIndex("" + NOTIFICATION_readNotif)));
                notif.add(data);
            } while (cursor.moveToNext());
        }
        db.endTransaction();
        return notif;
    }

    public int countNotif() {
        int count = 0;
        SQLiteDatabase db = this.getWritableDatabase(); //membuka database
        String selectQuery = "SELECT " + NOTIFICATION_readNotif + " FROM " + TABLE_NOTIFICATION
                + " WHERE " + NOTIFICATION_readNotif + " = 0";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() <= 0) {
            count = 0;
        } else {
            count = cursor.getCount();
        }
        Log.e("jumlah notif", "" + count);
        return count;
    }

    public ModelWOHeader getWO_Detail(String WO_ID) {
        List<ModelWOHeader> wo = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WO + " WHERE " + WO_orderHeaderId + " = '" + WO_ID + "'";
        //selectQuery = "SELECT  * FROM " + TABLE_WO;
        Log.e("ISI QUERY : ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelWOHeader data = new ModelWOHeader();
                data.setOrderAssignmentId(cursor.getString(cursor.getColumnIndex("" + WO_orderAssignmentId)));
                data.setOrderHeaderId(cursor.getString(cursor.getColumnIndex("" + WO_orderHeaderId)));
                data.setSoNumber(cursor.getString(cursor.getColumnIndex("" + WO_soNumber)));
                data.setOrderDate(cursor.getString(cursor.getColumnIndex("" + WO_orderDate)));
                data.setStartDate(cursor.getString(cursor.getColumnIndex("" + WO_startDate)));
                data.setEndDate(cursor.getString(cursor.getColumnIndex("" + WO_endDate)));
                data.setCustomerName(cursor.getString(cursor.getColumnIndex("" + WO_customerName)));
                data.setAddress(cursor.getString(cursor.getColumnIndex("" + WO_address)));
                data.setMaterial(cursor.getString(cursor.getColumnIndex("" + WO_material)));
                data.setSerialNumber(cursor.getString(cursor.getColumnIndex("" + WO_serialNumber)));
                data.setWarranty(cursor.getString(cursor.getColumnIndex("" + WO_warranty)));
                data.setRemark(cursor.getString(cursor.getColumnIndex("" + WO_remark)));
                data.setWorkCenter(cursor.getString(cursor.getColumnIndex("" + WO_workCenter)));
                data.setContactPerson(cursor.getString(cursor.getColumnIndex("" + WO_contactPerson)));
                data.setPhone(cursor.getString(cursor.getColumnIndex("" + WO_phone)));
                data.setOrderType(cursor.getString(cursor.getColumnIndex("" + WO_orderType)));
                data.setEstimateTime(cursor.getString(cursor.getColumnIndex("" + WO_estimateTime)));
                data.setSparepart(cursor.getString(cursor.getColumnIndex("" + WO_sparepart)));
                data.setPlant(cursor.getString(cursor.getColumnIndex("" + WO_plant)));
                data.setOrderStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex("" + WO_orderStatus))));
                data.setOrderStatusName(cursor.getString(cursor.getColumnIndex("" + WO_orderStatusName)));
                //Log.e("isi status name", "" + data.getOrderStatusName());
                data.setMechanicId(cursor.getString(cursor.getColumnIndex("" + WO_mechanicId)));
                data.setActionDate(cursor.getString(cursor.getColumnIndex("" + WO_actionDate)));
                wo.add(data);
            } while (cursor.moveToNext());
        }
        db.endTransaction();
        return wo.get(0);
    }

    public int updateCountNotif(int type, String WO_ID) {
        int rowsUpdated = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            if (type == 1) {//readtrue all
                ContentValues values = new ContentValues();
                values.put(NOTIFICATION_readNotif, 1);
                rowsUpdated = db.update(TABLE_NOTIFICATION, values, null, null);
                Log.e("rowsUpdated readTrue", "" + rowsUpdated);
            } else if (type == 0) {//read true som WO ID
                ContentValues values = new ContentValues();
                values.put(NOTIFICATION_readNotif, 1);
                rowsUpdated = db.update(TABLE_NOTIFICATION, values, NOTIFICATION_orderHeaderId + " = '" + WO_ID + "' and "+NOTIFICATION_readNotif+" = 0", null);
                Log.e("rowsUpdated readFalse", "" + rowsUpdated);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
        } finally {
            db.endTransaction();
        }
        return rowsUpdated;
    }

    public int deleteAllWONotification(String WO_ID) {
        //DIKASIH try catch
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            result = db.delete(TABLE_NOTIFICATION, NOTIFICATION_orderHeaderId + " = '" + WO_ID + "'", null);
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
        return result;
    }

    public long deleteAllNotification() {
        //DIKASIH try catch
        long result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_NOTIFICATION, null, null);
        db.close();
        return result;
    }

    //////////////////////////////////////////////

    public long writeHistoryActivity(ModelHistoryActivity data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HA_type, data.getType());
        values.put(HA_orderHeaderId, data.getOrderHeaderId());
        values.put(HA_json, data.getJson());
        values.put(HA_path, data.getPath());
        values.put(HA_flag, data.getFlag());
        // Inserting Row
        long result = db.insert(TABLE_HISTORY_ACTIVITY, null, values);
        // db.close(); // Closing database connection
        return result;
    }

    public long updateHistoryActivity(int idHA, String WO_ID, int flag) {

        int rowsUpdated = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(HA_flag, flag);
            rowsUpdated = db.update(TABLE_HISTORY_ACTIVITY, values, HA_id + " = " + idHA + " AND " + HA_orderHeaderId + " = '" + WO_ID + "'", null);
            Log.e("isi rowsUpdated", "" + rowsUpdated);
            db.setTransactionSuccessful();
        } catch (Exception e) {
        } finally {
            db.endTransaction();
        }
        return rowsUpdated;
    }

    public int deleteTrueHistoryActivity() {
        //DIKASIH try catch
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            result = db.delete(TABLE_HISTORY_ACTIVITY, HA_flag + " = 1", null);
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
        return result;
    }

    public int deleteAllWOHistoryActivity(String WO_ID) {
        //DIKASIH try catch
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            result = db.delete(TABLE_HISTORY_ACTIVITY, HA_orderHeaderId + " = '" + WO_ID + "'", null);
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
        return result;
    }

    public long deleteAllHistoryActivity() {
        //DIKASIH try catch
        long result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_HISTORY_ACTIVITY, null, null);
        db.close();
        return result;
    }

    public int getWOHistoryActivity(String WO_ID) {

        int count = 0;

        String selectQuery = "SELECT " + HA_flag + " FROM " + TABLE_HISTORY_ACTIVITY
                + " WHERE " + HA_orderHeaderId + " = '" + WO_ID + "' AND " + HA_flag + " = 0";

        Log.e("ISI QUERY : ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.getCount() <= 0) {
            count = 0;
        } else {
            count = cursor.getCount();
        }
        db.endTransaction();
        return count;
    }

    public ModelHistoryActivity getFalseHistoryActivity() {
        ModelHistoryActivity dataHA = new ModelHistoryActivity();
        String selectQuery = "SELECT * FROM " + TABLE_HISTORY_ACTIVITY + " WHERE " + HA_flag + " = 0 ORDER BY " + HA_id + " ASC LIMIT 1";

        Log.e("ISI QUERY : ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        if (cursor.getCount() <= 0) {
            dataHA = null;
        } else {
            if (cursor.moveToFirst()) {
                do {
                    ModelHistoryActivity data = new ModelHistoryActivity();
                    data.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("" + HA_id))));
                    data.setType(cursor.getString(cursor.getColumnIndex("" + HA_type)));
                    data.setOrderHeaderId(cursor.getString(cursor.getColumnIndex("" + HA_orderHeaderId)));
                    data.setJson(cursor.getString(cursor.getColumnIndex("" + HA_json)));
                    data.setPath(cursor.getString(cursor.getColumnIndex("" + HA_path)));
                    data.setFlag(Integer.parseInt(cursor.getString(cursor.getColumnIndex("" + HA_flag))));
                    dataHA = data;
                } while (cursor.moveToNext());
            }
        }

        db.endTransaction();
        return dataHA;
    }

    public List<ModelHistoryActivity> getListFalseHistoryActivity() {
        List<ModelHistoryActivity> dataHA = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_HISTORY_ACTIVITY + " WHERE " + HA_flag + " = 0 ORDER BY " + HA_id + " ASC ";

        Log.e("ISI QUERY : ", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelHistoryActivity data = new ModelHistoryActivity();
                data.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("" + HA_id))));
                data.setType(cursor.getString(cursor.getColumnIndex("" + HA_type)));
                data.setOrderHeaderId(cursor.getString(cursor.getColumnIndex("" + HA_orderHeaderId)));
                data.setJson(cursor.getString(cursor.getColumnIndex("" + HA_json)));
                data.setPath(cursor.getString(cursor.getColumnIndex("" + HA_path)));
                data.setFlag(Integer.parseInt(cursor.getString(cursor.getColumnIndex("" + HA_flag))));
                dataHA.add(data);
                //break;
            } while (cursor.moveToNext());
        }
        db.endTransaction();
        return dataHA;
    }

    public int getCountHistoryActivity() {
        int count = 0;
        SQLiteDatabase db = this.getWritableDatabase(); //membuka database
        String selectQuery = "SELECT " + HA_flag + " FROM " + TABLE_HISTORY_ACTIVITY;
        //+ " WHERE " + HA_flag + " = 0";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() <= 0) {
            count = 0;
        } else {
            count = cursor.getCount();
        }
        //Log.e("jumlah HA", "" + count);
        return count;
    }


    public int getCountAllActivity(String type) {
        int count = 0;
        SQLiteDatabase db = this.getWritableDatabase(); //membuka database
        String selectQuery = "";

        if(type.equals("new")){
             selectQuery = "SELECT " + WO_orderHeaderId + " FROM " + TABLE_WO + " WHERE " + WO_orderStatus + " IN (5070, 5100) ORDER BY " + WO_Id + " DESC";
        } else if(type.equals("onprocess")){
            selectQuery = "SELECT " + WO_orderHeaderId + " FROM " + TABLE_WO + " WHERE " + WO_orderStatus + " NOT IN (5070, 5100) ORDER BY " + WO_Id + " DESC";

        } else if(type.equals("syncoffline")){
            selectQuery = "SELECT " + HA_orderHeaderId + " FROM " + TABLE_HISTORY_ACTIVITY;
        }
        //+ " WHERE " + HA_flag + " = 0";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() <= 0) {
            count = 0;
        } else {
            count = cursor.getCount();
        }
        //Log.e("jumlah HA", "" + count);
        return count;
    }

}