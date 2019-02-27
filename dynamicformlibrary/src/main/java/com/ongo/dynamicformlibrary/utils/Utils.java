package com.ongo.dynamicformlibrary.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ongo.dynamicformlibrary.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class Utils {

    public static String regular_Font = "Regular";
    public static String semiBold_Font = "SemiBold";
    public static String bold_Font = "Bold";
    public static String droidSerif = "droidSerif";
    public static String roboto_bold = "roboto_bold";
    public static String roboto = "roboto";
    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static ProgressDialog mProgressDialog;

    public static String getClassName(Context mContext) {
        return mContext.getClass().getName();
    }

    public static void setVisibility(ImageView imageView, int visibility) {
        if (imageView.getVisibility() != visibility) {
            imageView.setVisibility(visibility);
        }
    }

    //decodes image and scales it to reduce memory consumption
    public static Bitmap decodeFile(File f) {
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1 = new FileInputStream(f);
            BitmapFactory.decodeStream(stream1, null, o);
            stream1.close();

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 100;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            if (scale >= 2) {
                scale /= 2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            FileInputStream stream2 = new FileInputStream(f);
            Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String compressImage(Bitmap bit) {

        //  ImageLoader imgLoader=new ImageLoader();

        String root = Environment.getExternalStorageDirectory().toString();
        File dir = new File(root + "/req_images");
        dir.mkdirs();
        //Bitmap b= BitmapFactory.decodeFile(orgPath);
        Bitmap out = bit;// Bitmap.createScaledBitmap(b, 400, 400, false);

        File file = new File(dir, "resize.png");
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            out.compress(Bitmap.CompressFormat.PNG, 80, fOut);
            fOut.flush();
            fOut.close();
            out.recycle();

            return file.getAbsolutePath();

        } catch (Exception e) {
            return null;

        }
    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public static Spanned getCurrency(String language) {
        String stringText = "";
        try {
            for (currency currencySet : currency.values()) {
                if (currencySet.name().equalsIgnoreCase(language)) {
                    int hexValue = Integer.parseInt("" + currencySet.currency, 16);
                    stringText += (char) hexValue;
                    return Html.fromHtml(stringText);
                }
            }
        } catch (Exception e) {
            return Html.fromHtml(stringText);
        }
        return Html.fromHtml(stringText);
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static String changeUTCtimeToLocalFormat(String time) {
        String formattedDate;
        try {
            SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a", Locale.getDefault());
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = df.parse(time);
            df.setTimeZone(TimeZone.getDefault());
            formattedDate = df.format(date);
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }
    }

    public static boolean checkType(String type) {
        if (type != null && (!type.equals(""))) {
            for (excludedProps propName : excludedProps.values()) {
                if (propName.name().equalsIgnoreCase(type))
                    return false;
            }
        }
        return !type.equalsIgnoreCase("YouTube URL");
    }

    public static String getWeek(int type) {
        for (weekDays propName : weekDays.values()) {
            if (propName.name == (type))
                return propName.name();
        }
        return "";
    }

    public static String getShareWidgetName(String packageName) {
        for (shareNames propName : shareNames.values()) {
            if (propName.name.equalsIgnoreCase((packageName)))
                return propName.name();
        }
        return "";
    }

    public static String getKeyName(String keyName) {
        for (keys propName : keys.values()) {
            if (propName.name.equalsIgnoreCase((keyName)))
                return propName.name();
        }
        return "";
    }

    public static void hideProgress(Context mContext) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {
                mProgressDialog.dismiss();

            }
        }

    }

    /**
     * this will return 1 if male and like verse.
     *
     * @param currentGender present gender
     * @return String
     */
    public static String getGender(String currentGender) {
        if (!TextUtils.isEmpty(currentGender)) {
            if (currentGender.equalsIgnoreCase("Male")) {
                return "1";
            } else if (currentGender.equalsIgnoreCase("1")) {
                return "Male";
            } else if (currentGender.equalsIgnoreCase("Female")) {
                return "0";
            } else {
                return "Female";
            }
        }
        return "";
    }

    public static void showProgress(Context mContext, Boolean val) {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCancelable(val);
        mProgressDialog.setMessage("Loading..");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();
    }


    public static Typeface getFontFromAsset(Context context, String type) {
        Typeface typeface = null;
        if (type.equals(Utils.bold_Font)) {
            typeface = Typeface.createFromAsset(context.getAssets(), "bold.otf");
        } else if (type.equals(Utils.semiBold_Font)) {
            typeface = Typeface.createFromAsset(context.getAssets(), "semibold.otf");
        } else if (type.equals(Utils.regular_Font)) {
            typeface = Typeface.createFromAsset(context.getAssets(), "regular.otf");
        } else if (type.equals(Utils.droidSerif)) {
            typeface = Typeface.createFromAsset(context.getAssets(), "DroidSerif.ttf");

        } else if (type.equals(Utils.roboto)) {
            typeface = Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");
        } else if (type.equals(Utils.roboto_bold)) {
            typeface = Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");
        }
        return typeface;
    }

    public static int tabsHeight(Context cont) {
        int alt;
        int dx, dy;
        DisplayMetrics metrics = cont.getResources().getDisplayMetrics();

        dx = metrics.widthPixels;
        dy = metrics.heightPixels;
        if (dx < dy)
            alt = dy / 16;
        else
            alt = dy / 12;

        return alt;
    }

    public static int tabsWidth(Context cont) {
        int alt;
        int dx;
        DisplayMetrics metrics = cont.getResources().getDisplayMetrics();

        dx = metrics.widthPixels;
        alt = dx / 5;

        return alt;
    }

     /**
     * @param mContext
     * @return string from Strings File
     */
    public static String getString(Context mContext, int id) {
        return mContext.getResources().getString(id);
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

    /**
     * @param mContext To hide soft key board
     */
    public static void hideSoftKeyboard(Context mContext) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isAcceptingText()) {
                inputMethodManager.hideSoftInputFromWindow(((Activity) mContext).getCurrentFocus().getWindowToken(), 0);
            } else {
            }
        } catch (Exception e) {
        }
    }

    public static float SetTextSize(String text, Context mContext) {
        Paint paint = new Paint();
        float textWidth = paint.measureText(text);
        float textSize = (int) (((get_Width(mContext) * 0.4) / textWidth) * paint
                .getTextSize());
        paint.setTextSize(textSize);

        textWidth = paint.measureText(text);
        textSize = (int) (((get_Width(mContext) * 0.4) / textWidth) * paint
                .getTextSize());

        // Re-measure with font size near our desired result
        paint.setTextSize(textSize);
        // Check height constraints
        FontMetricsInt metrics = paint.getFontMetricsInt();
        float textHeight = metrics.descent - metrics.ascent;
        if (textHeight > (get_Height(mContext) * 0.45)) {
            textSize = (int) (textSize * ((get_Height(mContext) * 0.45) / textHeight));
            paint.setTextSize(textSize);
        }
        return textSize;
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

    public static int get_Dpi(Context mContext) {
        return mContext.getResources().getDisplayMetrics().densityDpi;
    }



    public static void animate(ExpandableListView expandableListView, ListView listView, GridView gridView, SearchView linearLayout) {
        AnimationSet set = new AnimationSet(true);
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(500);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(
                set, 0.5f);
        if (listView != null) {
            listView.setLayoutAnimation(controller);
        } else if (gridView != null) {
            gridView.setLayoutAnimation(controller);
        } else if (expandableListView != null) {
            expandableListView.setLayoutAnimation(controller);
        } else {
            linearLayout.setLayoutAnimation(controller);
        }
    }

    public static ArrayList<String> convertStringToArray(String str) {
        ArrayList<String> arrayList = new ArrayList<String>();
        if (str.contains(",")) {
            String[] arr = str.split(",");
            for (int i = 0; i < arr.length; i++) {
                arrayList.add(arr[i]);
            }
            return arrayList;
        } else {
            if (!(str.equals(""))) {
                arrayList.add(str);
            }
            return arrayList;
        }
    }

    public static String convertArrayToString(ArrayList<String> array) {
        String str = "";
        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                str = str + array.get(i);
                // Do not append comma at the end of last element
                if (i < array.size() - 1) {
                    str = str + ",";
                }
            }
        }
        return str;
    }

    public static ArrayList<String> remove_Duplicates(
            ArrayList<String> with_Duplicates) {
        ArrayList<String> no_DuplicatesList = new ArrayList<String>();
        HashSet<String> hashSet = null;
        ArrayList<String> arrayList = null;
        if (with_Duplicates != null) {
            hashSet = new HashSet<String>(with_Duplicates);
        }
        if (hashSet != null) {
            arrayList = new ArrayList<String>(hashSet);
        }
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i) != null && (!arrayList.get(i).equals(""))) {
                    no_DuplicatesList.add(arrayList.get(i));
                }
            }
        }
        return no_DuplicatesList;
    }

    public static boolean appInstalledOrNot(Context ctx, String uri) {
        PackageManager pm = ctx.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static void gotoMarket(String string, Context mContext) {
        Uri uri = Uri.parse("market://search?q=pname:" + string);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
        }
    }

    public static String removeLeading_and_TrailingSpaces(String string) {
        string = string.replaceAll("^\\s+|\\s+$", "");
        return string;
    }

    /**
     * Webview UTF - 8
     **/

    @SuppressLint("SetJavaScriptEnabled")
    public static void webViewUTF(WebView webview, String htmlCode, int fontSize) {

        fontSize = 38;
        if (webview != null && htmlCode != null && !htmlCode.equalsIgnoreCase("")) {
            WebSettings webSettings = webview.getSettings();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                webSettings.setSupportZoom(false);
                webSettings.setDisplayZoomControls(false);
                webSettings.setBuiltInZoomControls(false);
            }
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setDefaultTextEncodingName("UTF-8");
            webSettings.setLoadsImagesAutomatically(true);
            webview.setInitialScale(100);

            try {
                String content = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                        "<html><head>" +
                        "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />"
                        + "<style> "
                        + "@font-face { font-family: roboto; src: url(\"file:///android_asset/Roboto-Regular.ttf\") }"
                        + "body { color:black;" + " font-family:roboto;font-size:" + fontSize + ";} "
                        + "p { color:black;" + " font-family:roboto;font-size:" + fontSize + ";} "
                        + "table { color:black;" + " font-family:roboto;font-size:" + fontSize + ";} "
                        + " </style>"
                        + "</head><body> <div style='font-family:roboto;'font-size:" + fontSize + ";>";
                content += htmlCode + "</div></body></html>";
                Log.i("htmlCode", " content " + content);
                webview.loadData(content, "text/html; charset=utf-8", "UTF-8");
            } catch (Exception e) {
                try {
                    webview.loadUrl(htmlCode);
                } catch (Exception e2) {
                }
            }
        }
    }


	/* Android Version Codes */

    // Platform Version API Level
    // Android 4.2 17
    // Android 4.1 16
    // Android 4.0.3 15
    // Android 4.0 14
    // Android 3.2 13
    // Android 3.1 12
    // Android 3.0 11
    // Android 2.3.3 10
    // Android 2.3 9
    // Android 2.2 8
    // Android 2.1 7
    // Android 2.0.1 6
    // Android 2.0 5
    // Android 1.6 4
    // Android 1.5 3
    // Android 1.1 2
    // Android 1.0 1

    public static void mapLoadProgressSpinnerShow(ProgressDialog pd) {

        // Set progress dialog style spinner
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // Set the progress dialog title and message
        pd.setTitle("Fetching Locations");
        pd.setMessage("Loading.........");

        // Set the progress dialog background color
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));

        pd.setIndeterminate(false);

        // Finally, show the progress dialog
        pd.show();
    }

    public static void mapLoadProgressSpinnerDismiss(ProgressDialog pd) {
        pd.dismiss();
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

    public static ACProgressFlower showFlowerProgressBar(Context mContext) {
        ACProgressFlower dialog = new ACProgressFlower.Builder(mContext)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Loading...")
                .fadeColor(Color.DKGRAY).build();
        return dialog;
    }

    public static void hideFlowerProgressBar(ACProgressFlower dialog) {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                dialog.dismiss();
            }
        }
    }

    public static int getJsonInt(JSONObject jsonObject, String key) {
        int value = 0;
        try {
            if (jsonObject.has(key)) {
                value = jsonObject.getInt(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getJsonString(JSONObject jsonObject, String key) {
        String value = "";
        try {
            if (jsonObject.has(key)) {
                value = jsonObject.getString(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static boolean getJsonBoolean(JSONObject jsonObject, String key) {
        boolean value = false;
        try {
            if (jsonObject.has(key)) {
                value = jsonObject.getBoolean(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
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

    public static String getBracketId(String name) {
        if (name.contains("(") && name.contains(")")) {
            return name.substring(name.indexOf("(") + 1, name.indexOf(")"));
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

    public static String getFormattedDate(String date, String actFormat, String reqFormat) {
        if (date.contains("\\/")) {
            date = date.replaceAll("\\/", "/");
        }
        SimpleDateFormat actDateFormat = new SimpleDateFormat(actFormat, Locale.getDefault());
        SimpleDateFormat reqDateFormat = new SimpleDateFormat(reqFormat, Locale.getDefault());

        try {
            Date actDate = actDateFormat.parse(date);
//            Date currDate = Calendar.getInstance().getTime();
            //            int compare = currDate.compareTo(actDate);
            return reqDateFormat.format(actDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Function to convert milliseconds time to
     * Timer Format
     * Hours:Minutes:Seconds
     */
    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    /**
     * Function to get Progress percentage
     *
     * @param currentDuration
     * @param totalDuration
     */
    public int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     *
     * @param progress      -
     * @param totalDuration returns current duration in milliseconds
     */
    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = totalDuration / 1000;
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

    public static HashMap<String, String> getHashMap(String paramObj) {
        HashMap<String,String> params = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(paramObj);
            Iterator<?> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (jsonObject.get(key) instanceof String) {
                    String value = jsonObject.getString(key);
                    if (!TextUtils.isEmpty(value)) {
                        params.put(key, value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public enum weekDays {
        Mon(Calendar.MONDAY), Tue(Calendar.TUESDAY), Wed(Calendar.WEDNESDAY), Thu(Calendar.THURSDAY), Fri(Calendar.FRIDAY), Sat(Calendar.SATURDAY), Sun(Calendar.SUNDAY);

        public int name;

        weekDays(int name) {
            this.name = name;
        }
    }

    public enum currency {
        INR("20B9"), USD("0024"), AUD("0024"), NZD("0024");
        public String currency;

        currency(String currency) {
            this.currency = currency;
        }
    }

    public enum keys {
        groupName("Group Name");

        public String name;

        keys(String name) {
            this.name = name;
        }
    }

    public enum shareNames {
        Whatsapp("com.whatsapp"), Gmail("com.google.android.gm"), Skype("com.skype.raider"), Facebook("com.facebook.katana"), Linkedin("com.linkedin.android"),
        Instagram("com.instagram.android"), Twitter("com.twitter.android"), Hangouts("com.google.android.talk"),
        Messaging("com.thinkyeah.message"), gPlus("com.google.android.apps.plus"), Pinterest("com.pinterest");

        public String name;

        shareNames(String name) {
            this.name = name;
        }
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
}