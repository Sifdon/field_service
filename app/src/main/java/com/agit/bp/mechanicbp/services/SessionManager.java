package com.agit.bp.mechanicbp.services;

/**
 * Created by NiyatiR on 8/13/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by irsal on 5/27/16.
 */
public class SessionManager {

    public static final int CUSTOMER_ID = 1;
    public static final int SELLER_ID = 2;

    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    private static final String KEY_COUNT_OF = "count_of_";
    private static final String KEY_PRICE = "price_";
    private static final String KEY_COUNT_OF_PRICE = "count_of_price";
    //
    public static final String KEY_ITEM_ID = "item_id";
    public static final String KEY_TOTAL_ITEM = "total_item";

    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_ROLE_ID = "role_id";
    public static final String KEY_CITY_ID = "city_id";
    public static final String KEY_CITY_NAME = "city_name";

    public static final String KEY_TOKEN = "token";
    public static final String KEY_TOKEN_FCM = "token_fcm";

    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IMG_PATH = "img_path";

    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private static final String KEY_TOTAL_ALAMAT = "total_alamat";

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "mechanicBP.xml";

    public SessionManager(final Context context, boolean usingThread) {
        this.context = context;

        if (pref == null) {
            /*if (usingThread) {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        pref = new SecurePreferences(context, "irsal", PREF_NAME);
                        editor = pref.edit();
                        return null;
                    }
                }.execute();
            } else {
                pref = new SecurePreferences(context, "irsal", PREF_NAME);
                editor = pref.edit();
            }*/
//            SecurePreferences.setLoggingEnabled(true);
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

        }
    }

    public SessionManager(final Context context) {
        this.context = context;

        if (pref == null) {
            pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }
    }

    public void login(boolean isLoggedIn, String mechanic_id, String mechanic_name) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.putString(KEY_USER_ID, mechanic_id);
        editor.putString(KEY_NAME, mechanic_name);

        // commit changes
        editor.commit();

        //Log.e(TAG, "User login session modified!");

    }

    public void logout() {

        editor.remove(KEY_IS_LOGGED_IN);
        editor.remove(KEY_USER_ID);
        editor.remove(KEY_NAME);

        // commit changes
        editor.commit();
    }

    public void setTokenFCM(String tokenFCM) {
        editor.putString(KEY_TOKEN_FCM, tokenFCM);
        editor.commit();
    }

    public void setIntArray(List<Integer> array, String type) {
//        SharedPreferences.Editor edit = mContext.getSharedPreferences("product", Context.MODE_PRIVATE).edit();
        editor.putInt(KEY_COUNT_OF + type, array.size());
//        int count = array.length;
        for (int i = 0; i < array.size(); i++) {
            editor.putInt(type + "_" + i, array.get(i));
        }
        editor.commit();
    }

    public void setFloatArray(List<Float> array) {
//        SharedPreferences.Editor edit = mContext.getSharedPreferences("product", Context.MODE_PRIVATE).edit();
        editor.putInt(KEY_COUNT_OF_PRICE, array.size());
//        int count = array.length;
        for (int i = 0; i < array.size(); i++) {
            editor.putFloat(KEY_PRICE + i, array.get(i));
        }
        editor.commit();
    }

    /////////////// GETTER - START

    public ArrayList<Integer> getIntFromPrefs(String type) {
        ArrayList<Integer> ret = new ArrayList<>();
//        SharedPreferences prefs = mContext.getSharedPreferences("product", Context.MODE_PRIVATE);
        int count = pref.getInt(KEY_COUNT_OF + type, 0);

        for (int i = 0; i < count; i++) {
            ret.add(pref.getInt(type + "_" + i, i));
        }
        return ret;
    }

    public ArrayList<Float> getFloatFromPrefs() {
        ArrayList<Float> ret = new ArrayList<>();
//        SharedPreferences prefs = mContext.getSharedPreferences("product", Context.MODE_PRIVATE);
        int count = pref.getInt(KEY_COUNT_OF_PRICE, 0);
//        ret = new Float[count];
        for (int i = 0; i < count; i++) {
            ret.add(pref.getFloat(KEY_PRICE + i, 0));
        }
        return ret;
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public int getRoleId() {
        return pref.getInt(KEY_ROLE_ID, 0);
    }

    public String getName() {

        return pref.getString(KEY_NAME, "");
    }

    public String getUserId() {
        return pref.getString(KEY_USER_ID, "");
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    public String getPhoneNumber() {
        return pref.getString(KEY_PHONE_NUMBER, "");
    }

    public String getImagePath() {
        return pref.getString(KEY_IMG_PATH, "");
    }

    public String getCityName() {
        return pref.getString(KEY_CITY_NAME, "");
    }
    /////////////// GETTER - END

    public void clearBasket() {
        int count = pref.getInt(KEY_COUNT_OF + KEY_ITEM_ID, 0);

        for (int i = 0; i < count; i++) {
            editor.remove(KEY_ITEM_ID + "_" + i);
            editor.remove(KEY_TOTAL_ITEM + "_" + i);
            editor.remove(KEY_PRICE + i);
        }

        editor.remove(KEY_COUNT_OF + KEY_ITEM_ID);
        editor.remove(KEY_COUNT_OF + KEY_TOTAL_ITEM);
        editor.remove(KEY_COUNT_OF_PRICE);

        editor.commit();
    }

    public int getCityId() {
        return pref.getInt(KEY_CITY_ID, 0);
    }

//    public int getBalance() {
//        return pref.getInt(KEY_BALANCE, 0);
//    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, "");
    }

    public String getTokenFCM() {
        return pref.getString(KEY_TOKEN_FCM, "");
    }


//    public static void storeStringArray(Context mContext, String[] array) {
//        SharedPreferences.Editor edit = mContext.getSharedPreferences("product", Context.MODE_PRIVATE).edit();
//        edit.putInt("count_of_name", array.length);
//        int count =  array.length;
//        for (int i = 0; i < count; i++) {
//            edit.putString("name_" + i, array[i]);
//        }
//        edit.commit();
//    }

//    public static String[] getStringFromPrefs(Context mContext) {
//        String[] ret;
//        SharedPreferences prefs = mContext.getSharedPreferences("product", Context.MODE_PRIVATE);
//        int count = prefs.getInt("count_of_name", 0);
//        ret = new String[count];
//        for (int i = 0; i < count; i++) {
//            ret[i] = prefs.getString("name_" + i, "");
//        }
//        return ret;
//    }

}

