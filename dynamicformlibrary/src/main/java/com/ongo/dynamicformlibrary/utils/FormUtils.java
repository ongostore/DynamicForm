package com.ongo.dynamicformlibrary.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ongo.dynamicformlibrary.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormUtils {

    public static boolean checkType(String type) {
        if (type != null && (!type.equals(""))) {
            for (excludedProps propName : excludedProps.values()) {
                if (propName.name().equalsIgnoreCase(type))
                    return false;
            }
        }
        return !type.equalsIgnoreCase("YouTube URL");
    }

    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || mConnectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }

    public static void toast(String msg, Context ctx) {
        Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_LONG);
        toast.show();
    }



    public static int get_Width(Context mContext) {
        int screenWidth;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            ((Activity) mContext).getWindowManager().getDefaultDisplay()
                    .getSize(size);
            screenWidth = size.x;
        } else {
            screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        }
        return screenWidth;
    }

    public static int get_Height(Context mContext) {
        int screenHeight;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            ((Activity) mContext).getWindowManager().getDefaultDisplay()
                    .getSize(size);
            screenHeight = size.y;
        } else {
            screenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        }
        return screenHeight;
    }



    public static void loadImage(ImageView imageView, String imageUrl, Context mContext) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.no_image);
        requestOptions.error(R.drawable.no_image);
        requestOptions.getDiskCacheStrategy();
        requestOptions.centerCrop();

        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .load(imageUrl).into(imageView);
    }


    public static JSONArray checkJsonArray(JSONObject jsonObject, String key) {
        try {
            if (jsonObject.has(key) && jsonObject.getString(key) != null && !jsonObject.getString(key).isEmpty() && !jsonObject.getString(key).equalsIgnoreCase("null")) {
                return jsonObject.getJSONArray(key);
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject checkJsonObj(JSONObject jsonObject, String key) {
        try {
            if (jsonObject.has(key) && jsonObject.getString(key) != null && !jsonObject.getString(key).isEmpty() && !jsonObject.getString(key).equalsIgnoreCase("null")) {
                return jsonObject.getJSONObject(key);
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String checkJsonObjStr(JSONObject jsonObject, String key) {
        try {
            if (jsonObject.has(key) && jsonObject.getString(key) != null && !jsonObject.getString(key).isEmpty() && !jsonObject.getString(key).equalsIgnoreCase("null")) {
                return jsonObject.getString(key);
            } else {
                return "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean checkJsonObjBool(JSONObject jsonObject, String key, Boolean defaultVal) {
        try {
            if (jsonObject.has(key) && jsonObject.getString(key) != null && !jsonObject.getString(key).isEmpty() && !jsonObject.getString(key).equalsIgnoreCase("null")) {
                return jsonObject.getBoolean(key);
            } else {
                return defaultVal;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultVal;
        }
    }

    public static int checkJsonObjInt(JSONObject jsonObject, String key, int defaultVal) {
        try {
            if (jsonObject.has(key) && jsonObject.getString(key) != null && !jsonObject.getString(key).isEmpty() && !jsonObject.getString(key).equalsIgnoreCase("null")) {
                return jsonObject.getInt(key);
            } else {
                return defaultVal;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return defaultVal;
        }
    }

    /**
     * @param mContext   for setting Locale
     * @param dateFormat for date output
     * @return present date
     */
    public static String todayDate(Context mContext, String dateFormat) {
        //setting the Locale for the issue M10 for Oct.
        checkSetLocale(mContext);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        String strDate = format.format(calendar.getTime());
        String correctDate = checkLocaleExits(strDate);
        return correctDate;
    }

    /**
     * @param mContext            to initialize datePicker
     * @param datePickerInterface to get selected date
     */
    public static void showDatePicker(final Context mContext, final DatePickerInterface datePickerInterface) {
        int mYear, mMonth, mDay;

        //setting the Locale for the issue M10 for Oct.
        checkSetLocale(mContext);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);

                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                String strDate = format.format(calendar.getTime());
                String correctDate = checkLocaleExits(strDate);
                datePickerInterface.datePickerInterface(correctDate);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    /**
     * @param mContext            to initialize datePicker
     * @param datePickerInterface to get selected date
     */
    public static void showDatePicker(final Context mContext, String selectedDate, final DatePickerInterface datePickerInterface) {
        int mYear, mMonth, mDay;

        //setting the Locale for the issue M10 for Oct.
        checkSetLocale(mContext);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);

                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                String strDate = format.format(calendar.getTime());
                String correctDate = checkLocaleExits(strDate);
                datePickerInterface.datePickerInterface(correctDate);
            }
        }, mYear, mMonth, mDay);
        Date date = new Date(selectedDate);
        datePickerDialog.getDatePicker().setMinDate(date.getTime());
        datePickerDialog.show();
    }

    private static String checkLocaleExits(String strDate) {
        String date = strDate;
        String M01 = "January", M02 = "February", M03 = "March", M04 = "April", M05 = "May",
                M06 = "June", M07 = "July", M08 = "August", M09 = "September", M10 = "October",
                M11 = "November", M12 = "December";
        if (strDate.contains("M01")) {
            date = strDate.replace("M01", M01);
        } else if (strDate.contains("M02")) {
            date = strDate.replace("M02", M02);
        } else if (strDate.contains("M03")) {
            date = strDate.replace("M03", M03);
        } else if (strDate.contains("M04")) {
            date = strDate.replace("M04", M04);
        } else if (strDate.contains("M05")) {
            date = strDate.replace("M05", M05);
        } else if (strDate.contains("M06")) {
            date = strDate.replace("M06", M06);
        } else if (strDate.contains("M07")) {
            date = strDate.replace("M07", M07);
        } else if (strDate.contains(" M08 ")) {
            date = strDate.replace("M08", M08);
        } else if (strDate.contains("M09")) {
            date = strDate.replace("M09", M09);
        } else if (strDate.contains("M10")) {
            date = strDate.replace("M10", M10);
        } else if (strDate.contains("M11")) {
            date = strDate.replace("M11", M11);
        } else if (strDate.contains("M12")) {
            date = strDate.replace("M12", M12);
        }

        return date;
    }

    /**
     * @param mContext            to initialize datePicker
     * @param datePickerInterface to get selected date
     * @param minimumCheckoutDays to set checkout date
     */
    public static void showDatePicker(final Context mContext, String selectedDate, final int minimumCheckoutDays, final TodayNextDatePickerInterface datePickerInterface) {
        int mYear, mMonth, mDay;
        final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        final SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa");

        //setting the Locale for the issue M10 for Oct.
        checkSetLocale(mContext);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);

                String strDate = format.format(calendar.getTime());
                String currentDate = format.format(new Date());
                Date selectedDate = null, currentDay = null;
                try {
                    selectedDate = format.parse(strDate);
                    currentDay = format.parse(currentDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (selectedDate != null) {
                    String nextDate = "";
                    if (minimumCheckoutDays > 0) {
                        final Calendar calendarToday = Calendar.getInstance();
                        calendarToday.setTime(selectedDate);
                        calendarToday.add(Calendar.DAY_OF_YEAR, minimumCheckoutDays);
                        nextDate = format.format(calendarToday.getTime());
                    } else {
                        nextDate = strDate;
                    }

                    String currentTime = formatter.format(calendar.getTime());
                    String checkoutTime = "12:00 PM";//formatter.format(calendar.getTime());

                    datePickerInterface.datePickerInterface(strDate, nextDate, currentTime, checkoutTime);
                }
            }
        }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        if (selectedDate.equalsIgnoreCase("")) {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        } else {
            try {
                final Calendar calendarToday = Calendar.getInstance();
                Date d = format.parse(selectedDate);
                calendarToday.setTime(d);
                calendarToday.add(Calendar.DAY_OF_YEAR, 1);
                d = calendarToday.getTime();
                long milliseconds = d.getTime();
                datePickerDialog.getDatePicker().setMinDate(milliseconds);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        datePickerDialog.show();
    }

    /**
     * author Md Adil
     *
     * @param mContext context to set the locale for date picker
     */
    public static void checkSetLocale(Context mContext) {
        //Use before dialogPicker call.
        //for the issue of M10 for Oct and likewise for the other months.
        Locale locale = mContext.getResources().getConfiguration().locale;
        if (locale.getLanguage().equals("null")) {
            Locale.setDefault(new Locale("en-US"));
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static String removeBracket(String name) {
        if (name.contains("(")) {
            return name.substring(0, name.lastIndexOf("("));
        }
        return name;
    }

    public static String getPath(Context mContext, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }


    public enum excludedProps {
        /*ItemCode,*/ id, /*Name,*/ createdById, jobTypeId, createdByFullName, publicURL, ExpiredOn, /*Quantity,*/ In_Stock, guestUserId, guestUserEmail,
        Image_Name, Image_URL, Latitude, Longitude, Attachments, Additional_Details, jobComments, Current_Job_Status, Category_Mall, /*MRP,*/ Insights,
        Current_Job_StatusId, Next_Seq_Nos, CreatedSubJobs, Next_Job_Statuses, offersCount, productsCount, businessType, storeId, CategoryType, SubCategoryType,
        P3rdCategory, /*Description,*/ createdOn, jobTypeName, hrsOfOperation, overallRating, totalReviews, ServiceType, favourites, malldetails, favouritesCount, lastModifiedDate
    }

    public interface DatePickerInterface {
        void datePickerInterface(String date);
    }

    public interface TimePickerInterface {
        void timePickerInterface(String time);
    }

    public interface TodayNextDatePickerInterface {
        void datePickerInterface(String todayDate, String nextDayDate, String arrivingTime, String checkoutTime);
    }

    /**
     * @param mContext            to initialize datePicker
     * @param datePickerInterface to get selected date
     */
    public static void showDateOfBirthPicker(final Context mContext, int noOfMinYears, final DatePickerInterface datePickerInterface) {
        int mYear, mMonth, mDay;

        //setting the Locale for the issue M10 for Oct.
        checkSetLocale(mContext);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);

                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                String strDate = format.format(calendar.getTime());
                String correctDate = checkLocaleExits(strDate);
                datePickerInterface.datePickerInterface(correctDate);
            }
        }, mYear, mMonth, mDay);
// Date date = new Date(selectedDate);
        if (noOfMinYears > 0) {
            c.set(Calendar.YEAR, mYear - noOfMinYears);
        }
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }
}