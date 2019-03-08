package com.ongo.dynamicformlibrary.form_utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ongo.dynamicformlibrary.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;



/*
 * Created by Admin on 03-01-2018.
 */

public class FormServiceUtils {

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

    public static void showDateTimePicker(final Context mContext, final DatePickerInterface datePickerInterface) {
        int mYear, mMonth, mDay;

        //setting the Locale for the issue M10 for Oct.
        checkSetLocale(mContext);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);

                        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
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
                            showTimeDatePicker(mContext, strDate, (DatePickerInterface) datePickerInterface);
                        }
                    }
                }, mYear, mMonth, mDay);
//        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();

    }

    public static void showDatePicker(final Context mContext, final DatePickerInterface datePickerInterface) {
        int mYear, mMonth, mDay;

        //setting the Locale for the issue M10 for Oct.
        checkSetLocale(mContext);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);

                        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
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
                            datePickerInterface.datePickerInterface(strDate);
                        }
                    }
                }, mYear, mMonth, mDay);
//        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    /**
     * @param mContext            for TimePicker initializing.
     * @param datePickerInterface to get the selected time
     */
    public static void showTimeDatePicker(final Context mContext, final String selectedDate, final DatePickerInterface datePickerInterface) {
        int hour, minute;
        Calendar mcurrentTime = Calendar.getInstance();
        hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        minute = mcurrentTime.get(Calendar.MINUTE);
        FormRangeTimePickerDialog mTimePicker;
        mTimePicker = new FormRangeTimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
                String time = formatter.format(calendar.getTime());
                String finalDate = selectedDate + " " + time;
                datePickerInterface.datePickerInterface(finalDate);
            }
        }, hour, minute, false);//true for 24 hour time //false for 12 hour time with AM/PM
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    /**
     * @param mContext            for TimePicker initializing.
     * @param timePickerInterface to get the selected time
     */
    public static void showTimePicker(final Context mContext, final TimePickerInterface timePickerInterface) {
        int hour, minute;
        Calendar mcurrentTime = Calendar.getInstance();
        hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        minute = mcurrentTime.get(Calendar.MINUTE);
        FormRangeTimePickerDialog mTimePicker;
        mTimePicker = new FormRangeTimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa");
                String time = formatter.format(calendar.getTime());
                timePickerInterface.timePickerInterface(time);
            }
        }, hour, minute, false);//true for 24 hour time //false for 12 hour time with AM/PM
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


    public static TextInputLayout getTextInputLayout(Context mContext, LinearLayout.LayoutParams params) {
        // TextInputLayout
        TextInputLayout textInputLayout = new TextInputLayout(mContext);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textInputLayout.setId(View.generateViewId());
        }
        // Set the layoutParams to the textInputLayout
        textInputLayout.setLayoutParams(params);

        return textInputLayout;
    }

    public static LinearLayout getSmallEditTextWithInputLayout(Context mContext, LinearLayout.LayoutParams params, String tagName) {

        LinearLayout innerLinearLayout = new LinearLayout(mContext);

        TextInputLayout textInputLayout = getTextInputLayout(mContext, params);
        EditText edittext = new EditText(mContext);

        params.setMargins(10, 0, 10, 0);

        edittext.setTag(tagName);
        edittext.setHint(tagName);
        edittext.setLayoutParams(params);
        // Then you add editText into a textInputLayout
        textInputLayout.addView(edittext, params);

        // Lastly, you add the textInputLayout into (or onto) the layout you've chosen.
        innerLinearLayout.addView(textInputLayout, params);

        return innerLinearLayout;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static RelativeLayout getSmallEditTextWithImageView(Context mContext, LinearLayout.LayoutParams params, String tagName, boolean isImageView, String mandatory) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.smalltextview, null, false);
        RelativeLayout rl = view.findViewById(R.id.rl);
        EditText edittext = view.findViewById(R.id.smallText);
        edittext.setTag(tagName);
        if (mandatory.equalsIgnoreCase("true")) {
            edittext.setHint(tagName + "*");
        } else {
            edittext.setHint(tagName);
        }

        return rl;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static RelativeLayout getNumericEditText(Context mContext, LinearLayout.LayoutParams params, String tagName, boolean isImageView, String mandatory) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.smalltextview, null, false);
        RelativeLayout rl = view.findViewById(R.id.rl);
        EditText edittext = view.findViewById(R.id.smallText);
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        edittext.setTag(tagName);
        if (mandatory.equalsIgnoreCase("true")) {
            edittext.setHint(tagName + "*");
        } else {
            edittext.setHint(tagName);
        }

        return rl;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static LinearLayout getSignatureLayout(Context mContext, LinearLayout.LayoutParams params,
                                                  String tagName, String mandatory) {
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.setMargins(10, 0, 15, 0);
        LinearLayout innerLinearLayout = new LinearLayout(mContext);
        innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
        // innerLinearLayout.setPadding(20,10,20,10);
        innerLinearLayout.setLayoutParams(params2);

        TextView textView = new TextView(mContext);
        if (mandatory.equalsIgnoreCase("true"))
            textView.setText(tagName + "*");
        else
            textView.setText(tagName);
        textView.setTextSize(16);
        textView.setTextColor(Color.BLACK);
        params.setMargins(20, 5, 20, 2);
        innerLinearLayout.addView(textView, params);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.setMargins(20, 10, 20, 10);

        //ImageView Setup
        ImageView imageView = new ImageView(mContext);
        imageView.getLayoutParams().height = 300;
        imageView.getLayoutParams().width = 300;
        //setting image resource
        imageView.setImageResource(R.drawable.ic_signing_the_contract);
        innerLinearLayout.addView(imageView, params1);
        return innerLinearLayout;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static LinearLayout getRadioButton(Context mContext, LinearLayout.LayoutParams params,
                                              String tagName, String mandatory, List<String> allowedValuesArry) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.radiocheckview, null, false);
        LinearLayout ll = view.findViewById(R.id.ll);
        TextView textView = view.findViewById(R.id.textview);
        textView.setText(tagName);
        RadioGroup radioGroup = view.findViewById(R.id.radiogroup);
//        textViewMain.setTag(tagName);
        if (mandatory.equalsIgnoreCase("true")) {
            textView.setHint(tagName + "*");
        } else {
            textView.setHint(tagName);
        }

//        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        params2.setMargins(10, 0, 15, 0);
//        LinearLayout innerLinearLayout = new LinearLayout(mContext);
//        innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
//        // innerLinearLayout.setPadding(20,10,20,10);
//        innerLinearLayout.setLayoutParams(params2);

//        TextView textView = new TextView(mContext);
//        if (mandatory.equalsIgnoreCase("true"))
//            textView.setText(tagName + "*");
//        else
//            textView.setText(tagName);
//        textView.setTextSize(16);
//        textView.setTextColor(Color.BLACK);
//        params.setMargins(10, 5, 10, 2);
//        innerLinearLayout.addView(textView, params2);
//
//        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        params1.setMargins(20, 10, 20, 10);

        final RadioButton[] rb = new RadioButton[allowedValuesArry.size()];
//        RadioGroup rg = new RadioGroup(mContext); //create the RadioGroup

        RadioGroup.LayoutParams paramsRadio = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsRadio.setMargins(15, 0, 15, 0);
        //paramsRadio

        radioGroup.setTag(tagName);
        radioGroup.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
        for (int i = 0; i < allowedValuesArry.size(); i++) {
            rb[i] = new RadioButton(mContext);
            rb[i].setText(allowedValuesArry.get(i).toString());
            rb[i].setId(i + 100);
            //rb[i].setTag(tagName);
            rb[i].setLayoutParams(paramsRadio);
            radioGroup.addView(rb[i]);
        }

        radioGroup.setGravity(Gravity.CENTER);

//        ll.addView(radioGroup, params1);
        // adding animated detail layout
        //if(tagName.contains("Work"))
        //  innerLinearLayout.addView(addDetailsLayout(mContext, rg));
        return ll;
    }

    private static View addDetailsLayout(final Context context, RadioGroup radioGroup) {
        final View view = LayoutInflater.from(context).inflate(R.layout.add_images_view, null);
        LinearLayout linearLayout = (LinearLayout) view;
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.setMargins(20, 10, 20, 10);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(params2);
        final RecyclerView recycler_view = view.findViewById(R.id.recycler_view);
        final ImageView add_photos = view.findViewById(R.id.add_photos);

        add_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FormOnImagesAddListener) context).onImagesAdded(recycler_view, add_photos);
            }
        });

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton radioButton = (RadioButton) v;
                    boolean checked = radioButton.isChecked();
                    Activity activity = (Activity) context;

                    if (view.getVisibility() == View.GONE) {
                        FormExpandCollapseAnimation animation = new FormExpandCollapseAnimation(view, 500, 0, activity);
                        view.startAnimation(animation);
                    }
                /*    if (radioButton.getText().toString().equalsIgnoreCase("Yes")) {
                        if (checked) {
                            if (view.getVisibility() == View.GONE) {
                                FormExpandCollapseAnimation animation = new FormExpandCollapseAnimation(view, 500, 0, activity);
                                view.startAnimation(animation);
                            }
                        }
                    } else {
                        if (checked) {
                            FormExpandCollapseAnimation animation = new FormExpandCollapseAnimation(view, 500, 1, activity);
                            view.startAnimation(animation);
                        }
                    }*/
                }
            });
        }
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static LinearLayout getLargeEditText(Context mContext, LinearLayout.LayoutParams params, String tagName, String mandatory) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.largetextview, null, false);
        LinearLayout ll = view.findViewById(R.id.ll);
        TextView textView = view.findViewById(R.id.textview);
        textView.setText(tagName);
        EditText edittext = view.findViewById(R.id.largeText);
        edittext.setTag(tagName);
        if (mandatory.equalsIgnoreCase("true")) {
            edittext.setHint(tagName + "*");
        } else {
            edittext.setHint(tagName);
        }

        return ll;

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void getAttachmentView(String mandatory, Context mContext, LinearLayout.LayoutParams params,
                                         String tagName, AttachmentInterface attachmentInterface) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_attachment_view, null, false);
        RelativeLayout rl = view.findViewById(R.id.rl);
        TextView textView = view.findViewById(R.id.smallText);
        ImageView imageView = view.findViewById(R.id.ic_attachment);
        LinearLayout innerLinearLayoutMain = new LinearLayout(mContext);
//        LinearLayout.LayoutParams paramsMain = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 120);
//        paramsMain.setMargins(5, 0, 15, 0);
//        innerLinearLayoutMain.setOrientation(LinearLayout.HORIZONTAL);
//        innerLinearLayoutMain.setWeightSum(1);
//        innerLinearLayoutMain.setPadding(0, 10, 0, 10);
//        innerLinearLayoutMain.setLayoutParams(paramsMain);


//        LinearLayout innerLinearLayout1 = new LinearLayout(mContext);
//        innerLinearLayout1.setOrientation(LinearLayout.HORIZONTAL);
//        innerLinearLayout1.setWeightSum(1);
//        innerLinearLayout1.setBackground(mContext.getResources().getDrawable(R.drawable.edittext_bg));
//        innerLinearLayout1.setPadding(10, 10, 10, 10);
//        innerLinearLayout.setLayoutParams(params2);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
//        params1.setMargins(0, 0, 10, 0);

//        EditText textView = new EditText(mContext);
        textView.setTag(tagName);
        if (mandatory.equalsIgnoreCase("true")) {
            textView.setHint(tagName + "*");
        } else {
            textView.setHint(tagName);
        }
//        textView.setBackgroundColor(mContext.getResources().getColor(R.color.edit_text_bg));
//        textView.setGravity(Gravity.TOP);
//        textView.setTextColor(mContext.getResources().getColor(R.color.black));
//        textView.setHintTextColor(mContext.getResources().getColor(R.color.serv_text_date_bg));
//        textView.setTextSize(mContext.getResources().getDimension(R.dimen.text_size_7));
//        textView.setEnabled(false);
//        textView.setVerticalScrollBarEnabled(true);
//       /* try {
//            Typeface face;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                face = mContext.getResources().getFont(R.font.mavenpro_regular);
//            }else {
//                face = Typeface.createFromAsset(mContext.getAssets(), "fonts/mavenpro_regular.ttf");
//            }
//            textView.setTypeface(face);
//        }catch (Exception e){
//            e.printStackTrace();
//        }*/
//        innerLinearLayout1.addView(textView, params1);

//        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams((int) mContext.getResources().getDimension(R.dimen.margin_size_70),
//                ViewGroup.LayoutParams.MATCH_PARENT, 0.4f);
//        LinearLayout innerLinearLayout2 = new LinearLayout(mContext);
//        innerLinearLayout2.setBackgroundColor(mContext.getResources().getColor(R.color.edit_text_date_bg));
//        innerLinearLayout2.setPadding(10, 5, 10, 5);
//        innerLinearLayout2.setLayoutParams(params2);
//        ImageView imageView = new ImageView(mContext);
//        innerLinearLayout2.addView(imageView, params2);

        setImageBackground(mContext, imageView, false);
        innerLinearLayoutMain.addView(rl, params1);
//        innerLinearLayoutMain.addView(innerLinearLayout2, params2);
//        params.setMargins(40, 15, 40, 0);

        attachmentInterface.attachmentListener(innerLinearLayoutMain, imageView, textView);
    }

    public static void setImageBackground(Context mContext, ImageView imageView, boolean isCheck) {
        if (isCheck) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_attachment_green));
            } else {
                imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_attachment_green));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_attachment_black));
            } else {
                imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_attachment_black));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static LinearLayout getSelectionView(String mandatory, Context mContext, LinearLayout.LayoutParams params,
                                                String tagName, boolean isMultiset, String[] stringArray, SelectionInterface selectionInterface) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.singleselectionview, null, false);
        LinearLayout ll = view.findViewById(R.id.ll);
        TextView textView = view.findViewById(R.id.textview);
        textView.setText(tagName);
        TextView textViewMain = view.findViewById(R.id.selectionView);
        textViewMain.setTag(tagName);
        if (mandatory.equalsIgnoreCase("true")) {
            textViewMain.setHint(tagName + "*");
        } else {
            textViewMain.setHint(tagName);
        }

        selectionInterface.selectionListener(isMultiset, stringArray, textViewMain);
        return ll;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static LinearLayout getSelectionViewForQColumn(String mandatory, Context mContext, LinearLayout.LayoutParams params,
                                                          String tagName, boolean isMultiset, ArrayList<String> keysArray, ArrayList<String> valuesArray, SelectionInterfaceForQColums selectionInterface) {


        View view = LayoutInflater.from(mContext).inflate(R.layout.singleselectionview, null, false);
        LinearLayout ll = view.findViewById(R.id.ll);
        TextView textView = view.findViewById(R.id.textview);
        textView.setText(tagName);
        TextView textViewMain = view.findViewById(R.id.selectionView);
        textViewMain.setTag(tagName);
        if (mandatory.equalsIgnoreCase("true")) {
            textViewMain.setHint(tagName + "*");
        } else {
            textViewMain.setHint(tagName);
        }

        selectionInterface.selectionListener(isMultiset, keysArray, valuesArray, textViewMain);
        return ll;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static LinearLayout getDateTimeView(String mandatory, Context mContext, LinearLayout.LayoutParams params,
                                               String tagName, String dateTime, DateTimeInterface dateTimeInterface) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.dateselectionview, null, false);
        LinearLayout ll = view.findViewById(R.id.ll);
        TextView textView = view.findViewById(R.id.textview);
        textView.setTag(tagName);
        textView.setText(tagName);
        ImageView imageView = view.findViewById(R.id.imageView);

        Drawable drawable;
        if (dateTime.equalsIgnoreCase("DateTime")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable = mContext.getDrawable(R.drawable.ic_calendar_with_a_clock_time_tools);
            } else {
                drawable = mContext.getResources().getDrawable(R.drawable.ic_calendar_with_a_clock_time_tools);
            }
        } else if (dateTime.equalsIgnoreCase("Date")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable = mContext.getDrawable(R.drawable.ic_date_toola);
            } else {
                drawable = mContext.getResources().getDrawable(R.drawable.ic_date_toola);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable = mContext.getDrawable(R.drawable.ic_clock_tools);
            } else {
                drawable = mContext.getResources().getDrawable(R.drawable.ic_clock_tools);
            }
        }
        imageView.setImageDrawable(drawable);
        dateTimeInterface.dateTimeListener(textView, imageView);

        return ll;
//        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        params2.setMargins(15, 0, 15, 0);
//        LinearLayout innerLinearLayout = new LinearLayout(mContext);
//        innerLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        innerLinearLayout.setWeightSum(1);
//        innerLinearLayout.setBackground(mContext.getResources().getDrawable(R.drawable.edittext_bg));
//        innerLinearLayout.setPadding(20, 10, 20, 10);
//        innerLinearLayout.setLayoutParams(params2);
//
//        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT, 0.6f);
//        params1.setMargins(0, 0, 10, 0);
//
//        TextView textView = new EditText(mContext);
//        textView.setTag(tagName);
//        if (mandatory.equalsIgnoreCase("true")) {
//            textView.setHint(tagName + "*");
//        } else {
//            textView.setHint(tagName);
//        }
//        textView.setBackground(mContext.getResources().getDrawable(R.drawable.edit_text_bg_white_corners));
//        textView.setGravity(Gravity.TOP);
//        textView.setTextColor(mContext.getResources().getColor(R.color.black));
//        textView.setHintTextColor(mContext.getResources().getColor(R.color.serv_text_date_bg));
//        textView.setTextSize(mContext.getResources().getDimension(R.dimen.text_size_7));
//        textView.setVerticalScrollBarEnabled(true);
//        textView.setEnabled(false);
//        /*try {
//            Typeface face;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                face = mContext.getResources().getFont(R.font.mavenpro_regular);
//            }else {
//                face = Typeface.createFromAsset(mContext.getAssets(), "fonts/mavenpro_regular.ttf");
//            }
//            textView.setTypeface(face);
//        }catch (Exception e){
//            e.printStackTrace();
//        }*/
//        innerLinearLayout.addView(textView, params1);
//
//        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.MATCH_PARENT, 0.4f);
//        ImageView imageView = new ImageView(mContext);
//
//        innerLinearLayout.addView(imageView, params3);
//
//        params.setMargins(40, 15, 40, 0);
//
//        return innerLinearLayout;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static LinearLayout getLargeEditTextWithInputLayout(Context mContext, LinearLayout.LayoutParams params, String tagName) {

        LinearLayout.LayoutParams innerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 300);
        LinearLayout innerLinearLayout = new LinearLayout(mContext);
        innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
        innerLinearLayout.setBackground(mContext.getResources().getDrawable(R.drawable.edit_text_bg_white_corners));
        innerParams.setMargins(15, 0, 15, 0);
        params.setMargins(15, 5, 15, 0);

        TextView textView = new TextView(mContext);
        textView.setText(tagName);
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);

        EditText edittext = new EditText(mContext);

        edittext.setTag(tagName);
        edittext.setHint(tagName);
        edittext.setLayoutParams(params);
        edittext.setBackground(mContext.getResources().getDrawable(R.drawable.edittext_bg));
        edittext.setVerticalScrollBarEnabled(true);
        edittext.setGravity(Gravity.TOP);

        // Lastly, you add the textInputLayout into (or onto) the layout you've chosen.
        innerLinearLayout.addView(textView, params);
        innerLinearLayout.addView(edittext, innerParams);

        return innerLinearLayout;
    }

    @SuppressLint("ResourceType")
    public static EditText getEditText(Context mContext, LinearLayout.LayoutParams params, String tagName) {

        params.setMargins(10, 0, 10, 0);
        EditText edittext = new EditText(mContext);
        edittext.setTag(tagName);
        edittext.setHint("Select Here");
        edittext.setBackgroundResource(mContext.getResources().getColor(R.color.edit_trans));
        edittext.setLayoutParams(params);

        return edittext;
    }

    public static LinearLayout getLinearLayout(Context mContext) {
        return new LinearLayout(mContext);
    }

    public static TextView getTextView(Context mContext) {
        TextView textView = new TextView(mContext);
//        Typeface face = Typeface.createFromAsset(mContext.getAssets(), "fonts/montserrat_medium.ttf");
//        textView.setTypeface(face);
        return textView;

    }

    public static TextView getTextView(Context mContext, LinearLayout.LayoutParams params, String tagName) {
        TextView textView = new TextView(mContext);
//        Typeface face = Typeface.createFromAsset(mContext.getAssets(), "fonts/montserrat_medium.ttf");
//        textView.setTypeface(face);

        params.setMargins(10, 0, 10, 0);
        textView.setTag(tagName);
        textView.setHint("Select Here");
        textView.setBackgroundResource(R.color.edit_trans);
        textView.setLayoutParams(params);
        return textView;

    }


    public interface DatePickerInterface {
        void datePickerInterface(String date);
    }

    public interface TimePickerInterface {
        void timePickerInterface(String time);
    }

    public interface AttachmentInterface {
        void attachmentListener(LinearLayout innerLinearLayout, ImageView view, TextView textView);
    }

    public interface SelectionInterface {
        void selectionListener(boolean isMultiset, String[] stringArray, TextView textViewMain);
    }

    public interface SelectionInterfaceForQColums {
        void selectionListener(boolean isMultiset, ArrayList<String> keysArray, ArrayList<String> valuesArray, TextView textViewMain);
    }

    public interface DateTimeInterface {
        void dateTimeListener(TextView textViewMain, ImageView imageView);
    }
}
