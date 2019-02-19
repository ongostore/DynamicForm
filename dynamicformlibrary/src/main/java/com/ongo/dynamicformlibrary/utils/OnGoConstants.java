package com.ongo.dynamicformlibrary.utils;

import android.content.Context;

import com.ongo.loaderdozer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ONGO-0006
 */
public class OnGoConstants {

    /********************
     * payment releated constants
     ***************/

    public static final String PAYEE_USER_DETAILS = "PAYEE_USER_DETAILS";
    public static final String PAYEE_USER_GETECASH = "PAYEE_USER_GETECASH";
    public static final String PAYEE_USER_UPDATECASH = "PAYEE_USER_UPDATECASH";
    public static final String PAYEE_CREATE_USER_ECASH_BY_PID = "PAYEE_CREATE_USER_ECASH_BY_PID";
    public static final String UPDATE_ONLY_PROFILE_FIELDS = "UPDATE_ONLY_PROFILE_FIELDS";
    public static final String UPDATE_ONLY_SPECIFIC_FIELDS = "UPDATE_ONLY_SPECIFIC_FIELDS";
    public static final String REGISTER_LOGIN_TOKEN = "REGISTER_LOGIN_TOKEN";
    public static final String DELETE_LOGIN_TOKEN = "DELETE_LOGIN_TOKEN";
    public static final String PREF_SAVE_ECASH = "PREF_SAVE_ECASH";
    public static final String YES = "Yes";
    public static final String PREF_ITEMS_ADD = "PREF_ITEMS_ADD";
    public static final String PREF_OVERLAY_SCREEN = "PREF_OVERLAY_SCREEN";

    public static final String API_CANCEL_ORDER = "API_CANCEL_ORDER";
    public static final String API_RESUME_ORDER = "API_RESUME_ORDER";
    public static final String PREF_MEAL_PACKAGE_AMOUNT = "PREF_MEAL_PACKAGE_AMOUNT";
    public static final String PREF_DISCOUNTED_AMOUNT = "PREF_DISCOUNTED_AMOUNT";
    public static final String PREF_DONT_CALC_DISOUNT_AMT = "PREF_DONT_CALC_DISOUNT_AMT";

    public static final String PREF_RENEWAL_VALID_FROM = "PREF_RENEWAL_VALID_FROM";
    public static final String PREF_RENEWAL_VALID_TILL = "PREF_RENEWAL_VALID_TILLL";

    public static final String PREF_NO_OF_DAYS_LEFT = "PREF_NO_OF_DAYS_LEFT";
    public static final String PREF_MEAL_PACK_DAYS_SELECTED = "PREF_MEAL_PACK_DAYS_SELECTED";

    public static final String PREF_SEND_NOTIFICATION_TO_USER = "PREF_SEND_NOTIFICATION_TO_USER";
    public static final String PREF_REPLACE_COUPONS = "PREF_REPLACE_COUPONS";
    public static final String PREF_ADD_MORE_COUPONS = "PREF_ADD_MORE_COUPONS";
    public static final String PREF_MANUAL_ADDRESS = "PREF_MANUAL_ADDRESS";
    public static final String PREF_MAP_ADDRESS = "PREF_MAP_ADDRESS";
    public static final String PREF_RENEWAL = "PREF_RENEWAL";
    public static final String PREF_BREAKFAST_ARRAY = "PREF_BREAKFAST_ARRAY";
    public static final String PREF_LUNCH_ARRAY = "PREF_LUNCH_ARRAY";
    public static final String PREF_DINNER_ARRAY = "PREF_DINNER_ARRAY";
    public static final String PREF_MAP_ARRAY = "PREF_MAP_ARRAY";
    public static final String PREF_MIN_DINNER_TIMINGS = "PREF_MIN_DINNER_TIMINGS";
    public static final String PREF_MIN_LUNCH_TIMINGS = "PREF_MIN_LUNCH_TIMINGS";
    public static final String PREF_MIN_BREAKFAST_TIMINGS = "PREF_MIN_BREAKFAST_TIMINGS";
    public static final String LUNCH_OLD_ITEM_ID = "lunch_OldItemId";
    public static final String BREAKFAST_OLD_ITEM_ID = "breakfast_OldItemId";
    public static final String DINNER_OLD_ITEM_ID = "dinner_OldItemId";
    public static final String PREF_LOCATION_AVAILABLE = "PREF_LOCATION_AVAILABLE";
    public static final String API_CHANGE_JOB_STATUS = "API_CHANGE_JOB_STATUS";
    public static final String API_ORDER_STATUS = "API_ORDER_STATUS";


    public static final String ALL_PRODUCTS = "allProducts";
    public static final String AD_TYPE = "AdType";
    public static final String RECENT_ADS = "Recent Ads";
    public static final String CATEGORY_ADS = "Category Ads";
    public static final String CATEGORY_ALL = "Categories";
    public static final String FEATURED_ADS = "Featured Products";
    public static final String SEARCH_PRODUCTS = "Search";
    public static final String SEARCH_TYPE = "SEARCH_TYPE";
    public static final String SEARCH_TYPE_LOCATIONS = "SEARCH_TYPE_LOCATIONS";
    public static final String SEARCH_TYPE_ITEMS = "SEARCH_TYPE_ITEMS";
    public static final String SEARCH_SELECTED_ITEM = "SEARCH_SELECTED_ITEM";
    public static final String PREF_MACIDJOBID = "PREF_MACIDJOBID";
    public static final String PREF_IS_BOARDING_CHECK = "PREF_IS_BOARDING_CHECK";
    public static final String GET_SERVER_TIME = "GET_SERVER_TIME";
    public static final String PREF_SUBSCRIBE_ID = "PREF_SUBSCRIBE_ID";
    public static final String PREF_ALREADY_SUBSCRIBED_USER = "PREF_ALREADY_SUBSCRIBED_USER";
    public static final String PREF_TOTAL_TODAY_MEAL_PRICE = "PREF_TOTAL_TODAY_MEAL_PRICE";
    public static final String PREF_SUBSCRIBED_MONEY = "PREF_SUBSCRIBED_MONEY";
    public static final String SMS_ORIGIN = "MLTIME";
    public static final String TAG = "OnGo";
    public static final String PREF_NAME = "OnGOPref";
    public static final String FEATURED_PRODUCTS = "Featured Products";
    public static final String PREF_USER_NAME = "USER_NAME";
    public static final String PREF_FIRST_NAME = "FIRST_NAME";
    public static final String PREF_LAST_NAME = "LAST_NAME";
    public static final String PREF_LOGGED_IN = "LOGGED_IN";
    public static final String PREF_EMAIL_ID = "EMAIL_ID";
    public static final String PREF_ORG_ID = "ORG_ID";
    public static final String PREF_USER_ID = "USER_ID";
    public static final String PREF_ADDRESS = "ADDRESS";
    public static final String PREF_COUNTRY_ARRAY = "COUNTRIES";
    public static final String PREF_COUNTRY = "COUNTRY";
    public static final String PREF_CITY = "CITY";
    public static final String PREF_ORGANIZATION = "ORG";
    public static final String PREF_STATE = "STATE";
    public static final String PREF_GENDER = "GENDER";
    public static final String PREF_SELECTED_CITY = "CITYNAME";
    //public static final String PREF_MOBILE = "MOBILE";
    public static final String PREF_PROFILE_IMAGE = "PROFILE_IMAGE";
    public static final String PREF_USER_DETAILED_ADDRESS = "PREF_USER_DETAILED_ADDRESS";
    public static final String PREF_USER_FULL_ADDRESS = "PREF_USER_FULL_ADDRESS";
    public static final String PREF_USER_ADDRESS = "PREF_USER_ADDRESS";
    public static final String PREF_USER_LANDMARK = "PREF_USER_LANDMARK";
    public static final String PREF_USER_CITY = "PREF_USER_CITY";
    public static final String PREF_USER_STATE = "PREF_USER_STATE";
    public static final String PREF_MOBILE_NUMBER = "PREF_MOBILE_NUMBER";
    public static final String PREF_LOGIN_PIN = "PREF_LOGIN_PIN";
    public static final String PREF_USER_DATA = "PREF_USER_DATA";
    public static final String GENERATE_OTP = "GENERATE_OTP";
    public static final String VERIFY_OTP = "VERIFY_OTP";
    public static final String VERIFY_USER_REGISTER_OTP = "VERIFY_USER_REGISTER_OTP";
    public static final String GET_TODAY_MEALS_API = "GET_TODAY_MEALS_API";
    public static final String PREF_MEAL_TYPE = "PREF_MEAL_TYPE";
    public static final String PREF_MEAL_CHOICE_PLAN = "PREF_MEAL_CHOICE_PLAN";
    public static final String PREF_BREAKFAST_JOBS = "PREF_BREAKFAST_JOBS";
    public static final String PREF_LUNCH_JOBS = "PREF_LUNCH_JOBS";
    public static final String PREF_MEAL_TYPE_CLICKED = "PREF_MEAL_TYPE_CLICKED";
    public static final String PREF_USER_SUBSCRIBED = "PREF_USER_SUBSCRIBED";
    public static final String PREF_ADD_MORE_ASYNC = "PREF_ADD_MORE_ASYNC";
    public static final String PREF_USER_JOB_ID = "PREF_USER_JOB_ID";
    public static final String PREF_USER_MAC_ID_JOB_ID = "PREF_USER_MAC_ID_JOB_ID";
    public static final String PREF_PROFILE_IMAGE_BITMAP = "PROFILE_BITMAP";
    public static final String IS_FROM_FACEBOOK = "YES_FACEBOOK";
    public static final String IS_FROM_PAYMENT = "IS_FROM_PAYMENT";
    public static final String PREF_COVER_IMAGE = "COVER_IMAGE";
    public static final String PREF_MACID = "MAC ID";
    public static final String PREF_ADD_MACID = "ADD MAC ID";
    public static final String PREF_REG_MACID = "REG MAC ID";
    public static final String PREF_STORE_ITEMCODE = "STORE ITEM CODE";
    public static final String CAMPAIGN_JOBS_NAMES = "CAMPAIGN JOBS NAMES";
    public static final String CAMPAIGN_JOBS_IDS = "CAMPAIGN JOBS IDS";
    public static final String PREF_TypeOfEquipmentHandled = "PREF_TypeOfEquipmentHandled";
    public static final String PREF_LicenceNumber = "PREF_LicenceNumber";
    public static final String PREF_IDProof = "PREF_IDProof";
    public static final String PREF_PreferredLocations = "PREF_PreferredLocations";
    public static final String PREF_YearsOfExperience = "PREF_YearsOfExperience";
    public static final String PREF_WorkStatus = "PREF_WorkStatus";
    public static final String PREF_AddressProof = "PREF_AddressProof";
    public static final String PREF_address = "PREF_address";
    public static final String PREF_TypeOfEquipmentHandledService = "PREF_TypeOfEquipmentHandledService";
    public static final String PREF_PreferredLocationsService = "PREF_PreferredLocationsService";
    public static final String PREF_YearsOfExperienceService = "PREF_YearsOfExperienceService";
    public static final String PREF_Category = "PREF_Category";
    public static final String PREF_Type = "PREF_Type";
    public static final String PREF_Brand = "PREF_Brand";
    public static final String PREF_Model = "PREF_Model";

    public static final String PREF_IS_OPERATOR = "PREF_IS_OPERATOR";
    public static final String PREF_IS_SERVICE_CENTER = "PREF_IS_SERVICE_CENTER";
    public static final String PREF_IS_SUPPLIER = "PREF_IS_SUPPLIER";
    public static final String PREF_IS_BUYER = "PREF_IS_BUYER";
    public static final String PREF_IS_SELLER = "PREF_IS_SELLER";
    public static final String PREF_IS_RENTER = "PREF_IS_RENTER";

    public static final String FBTP = "Testing";
    public static final String FB_CART = "Cart";
    //analytics
    public static final String PREF_LOYALITY_EXTREME = "EXTREME";
    public static final String PREF_LOYALITY_REGISTER = "Register";
    public static final String PREF_LOYALITY_LOGIN = "Login";
    public static final String PREF_LOYALITY_PRODUCT_LIKE = "Product Like";
    public static final String PREF_LOYALITY_PRODUCT_COMMENT = "Product Comment";
    public static final String PREF_LOYALITY_PRODUCT_VIEW = "Product View";
    public static final String PREF_LOYALITY_PRODUCT_SHARE = "Product Share";
    public static final String PREF_LOYALITY_PRODUCT_CART = "Product Cart";
    public static final String PREF_LOYALITY_PRODUCT_BUY = "Product Buy";
    public static final String PREF_LOYALITY_OFFER_LIKE = "Offer Like";
    public static final String PREF_LOYALITY_OFFER_FAV = "Offer Favorite";
    public static final String PREF_LOYALITY_OFFER_SHARE = "Offer Share";
    public static final String PREF_LOYALITY_OFFER_VIEW = "Offer View";
    public static final String PREF_LOYALITY_OFFER_OFFER_COMMENT = "Offer Comment";
    public static final String PREF_NO_OF_ROTIS = "PREF_NO_OF_ROTIS";
    public static final String PREF_LOYALITY_CALL = "Call";
    public static final String PREF_LOYALITY_MESSAGE = "Messaging";
    public static final String PREF_LOYALITY_MAP = "Map";
    public static final String PREF_LOYALITY_ABOUTUS = "About us";
    public static final String PREF_LOYALITY_ORDERS = "Orders";
    public static final String PREF_LOYALITY_PROFILE = "Profile";
    public static final String PREF_LOYALITY_CARTVIEW = "Cart View";
    public static final String PREF_LOYALITY_FAV = "Products Favorite";
    public static final String PREF_LOYALITY_WISHLIST = "Wishlist";
    public static final String PREF_LOYALITY_GALLERY = "Gallery";
    public static final String PREF_LOYALITY_NOTIFICATION = "Notifications";
    public static final String PREF_LOYALTY_JSON_STRING = "Loyalty String";
    public static final String PREF_GUESTMAILL = "guestmail_id";
    public static final String PREF_GUEST_USERID = "guest_user_id";
    public static final String PREF_SELECTED_MALL_ID = "selected_mall_id";
    public static final String PREF_RETRIVE_FEATURED_DETAILS = "retrive_featered_details";
    public static final String PREF_SELECTED_MALL_NAME = "selected_mall_name";
    public static final String PREF_SELECTED_CATEGORY = "SELECTED_CATEGORY";
    public static final String PREF_DEFAULT_STORE_ID = "Defult_store_id";
    public static final String PREF_FIRSTTIME = "First_Time";
    public static final String PREF_DEFAULT_LANGUAGE_CHANGED = "default_language_changed";
    public static final String PREF_LAT = "latitude";
    public static final String PREF_LONG = "longitude";
    public static final String PREF_STORE_LAT = "StoreLatitude";
    public static final String PREF_STORE_LNG = "StoreLongitude";
    public static final String PREF_CANCELLED_DATES = "PREF_CANCELLED_DATES";
    public static final String favourite = "favourite";
    public static final String un_Favourite = "unFavourite";
    public static final String Added_to_Cart = "cart";
    public static final String removed_from_Cart = "unCart";
    public static final String Review_ID = "id";
    public static final String Review_TYPE = "review_Type";
    public static final String PREF_STORE_ID = "STOREID";
    public static final String GUESTMACID = "guest123";
    // public static final String GUESTMAILL = "guest@storeongo.com";
    // public static final String GUEST_USERID = "17";
    public static final String ActionBarHeight = "actionBarHeight";
    public static final String Review_ItemCode = "itemCode";
    public static final String ProductComment = "Products Comment";
    public static final String ProductShare = "Products Share";
    public static final String ProductCart = "Products Cart";
    public static final String ProductBuy = "Products Buy";
    public static final String ProductFavorite = "Products Favorite";
    public static final String ProductView = "Products View";
    public static final String OfferLike = "Offers Like";
    public static final String OfferComment = "Offers Comment";
    public static final String OfferShare = "Offers Share";
    public static final String OfferFavorite = "Offers Favorite";
    public static final String OfferView = "Offers View";
    public static final String ServicesShare = "Services Share";
    public static final String ServicesComment = "Services Comment";
    public static final String ServicesView = "Services View";
    public static final String ServicesFavorite = "Services Favorite";
    public static final String CampaignsView = "Campaigns View";
    public static final String CampaignsShare = "Campaigns Share";
    public static final String Login = "Login";
    public static final String Register = "Register";
    public static final int pageSize = 20;
    public static final String isCallBack = "isCallBack";
    public static final String GET_MEALTIME_ORDERS = "GET_MEALTIME_ORDERS";
    public static final String PREF_GEO_DATA = "PREF_GEO_DATA";
    public static final String FORGOT_PWD_UPDATE = "FORGOT_PWD_UPDATE";
    public static final String SUBSCRIPTION_TYPE = "SUBSCRIPTION_TYPE";
    public static final String PAYMENT_DONE_FOR = "PAYMENT_DONE_FOR";
    public static final String PAYMENT_GOING_ON_FOR = "PAYMENT_GOING_ON_FOR";
    public static final String from_sample_to_subscribe = "from_sample_to_subscribe";
    public static final String FIREBASE_K_NOTIFI_COUNT = "FIREBASE_K_NOTIFI_COUNT";
    public static final String FIREBASE_NOTIFICATIONS = "Notifications";
    public static final String FIREBASE_PATH = "https://whattf-7c380.firebaseio.com/";
    public static final String LOCATIONS_TYPE = "Locations";
    public static final String CATEGORIES_TYPE = "Categories";
    public static final String MY_CHATS = "My Chats";
    public static HashMap<String, String> editFieldsHASHMAP = new HashMap<String, String>();
    public static List<String> locations = new ArrayList<>();
    public static int CART_SIZE = 0;
    public static String PREF_SAMPLE_MEAL_NO_DAYS = "PREF_SAMPLE_MEAL_NO_DAYS";
    public static String PREF_MEAL_NO_OF_DAYS = "PREF_MEAL_NO_OF_DAYS";
    public static String PREF_PAYMENT_SUCCESS_PROFILE_UPDATE_VERIFIED = "PREF_PAYMENT_SUCCESS_PROFILE_UPDATE_VERIFIED";
    /**************
     * selected food type pref
     ******************/
    public static String PREF_FOOD_STYLE = "PREF_FOOD_STYLE";
    public static String PREF_FOOD_CHOICE = "PREF_FOOD_CHOICE";
    public static String PREF_MEAL_CHOICE = "PREF_MEAL_CHOICE";
    public static String PREF_PACKING_TYPE = "PREF_PACKING_TYPE";
    public static String PREF_VALID_TILL = "PREF_VALID_TILL";
    public static String PREF_BREAKFAST_TIMINGS = "PREF_BREAKFAST_TIMINGS";
    public static String PREF_LUNCH_TIMIMGS = "PREF_LUNCH_TIMIMGS";
    public static String PREF_DINNER_TIMINGS = "PREF_DINNER_TIMINGS";
    public static String PREF_MEAL_PACK_AMOUNT = "PREF_MEAL_PACK_AMOUNT";
    public static String PREF_USER_SUBSCRIBE_FROM_DATE = "PREF_USER_SUBSCRIBE_FROM_DATE";
    public static String PREF_USER_SUBSCRIBE_TO_DATE = "PREF_USER_SUBSCRIBE_TO_DATE";
    public static String Apps = "Apps";
    public static String ITEMCODE = "ITEMCODE";
    public static String[] breakfast_arraylist;
    public static String[] lunch_arraylist;
    public static String[] dinner_arraylist;
    public static boolean break_fast_lunch = true;
    public static boolean sweet_juice = true;
    public static String sample_end_date = "sample_end_date";
    public static String PREF_NOTIFICATION_NAME = "TESTRIDEY";
    public static String operator = "Operator"; // vehicle operator
    public static String service = "Service"; // service provider
    public static String supplier = "Supplier"; //Spare parts provider
    public static String sell = "Sell"; // seller
    public static String buy = "Buy"; // buyer
    public static String rent = "Rent"; // renter
    public static String deviceToken = null;
    public static HashMap<String, String> editFieldsImagesHashMap;


    public static final String SELECTED_USER_ID = "SELECTED_USER_ID";
    public static final String SELECTED_USER_PROFPIC = "SELECTED_USER_PROFPIC";
    public static final String SELECTED_USER_USERNAME = "SELECTED_USER_USERNAME";
    public static final String FIREBASE_K_UNREADCOUNT = "unreadCount";

    public static final String Categories = "Categories";
    public static final String Brands = "Brands";
    public static final String Models = "Models";
    public static final String Type = "Type";
    public static final String location = "Location";
    public static final String year = "year";

    public static final String Category = "Category";
    public static final String Brand = "Brand";
    public static final String Model = "Model";
    public static final String Location = "Location";
    public static final String Mfg_year = "Mfg year";
    public static final String itemSearchString = "Name";
    public static final String locationString = "Location";
    public static final String startDateString = "StartDate";
    public static final String endDateString = "EndDate";
    public static String PREF_POST_ADDS_ARRAY = "PREF_POST_ADDS_ARRAY";
    public static String paramObj = "paramObj";

    /**
     * @param mallId
     * @param mContext
     * @return URL for get all product categories
     */
    public static String getProductCategories(String mallId, Context mContext) {
        return getHostUrl(mContext) + "/Services/getMasters?type=ProductCategories&mallId=" + mallId;
    }

    public static String getProductSubCategories(String mallId,
                                                 Context mContext, String prodCateId) {
        return getHostUrl(mContext)
                + "/Services/getMasters?type=PSubCategories&mallId=" + mallId
                + "&refTypeProperty=MasterCategory&refId=" + prodCateId;
    }

    /**
     * @param mallId
     * @param mContext
     * @return URL for get all Store categories
     */
    public static String get_StoreCategories(String mallId, Context mContext) {
        return getHostUrl(mContext) + "/Services/getMasters?type=StoreCategories&mallId=" + mallId;
    }

    /**
     * @param mContext
     * @param mallId
     * @return URL for get all product categories
     */
    public static String addMacId(Context mContext) {
        return getHostUrl(mContext) + "/MobileAPIs/postedJobs?type=MacIdInfo";
        /*
         * &json={"list":[{"ItemCode":"MacId","Name":"OrganisationName"}]}&dt=
		 * DEVICES&category=Devices&userId=mallId
		 */
    }

    public static String demoPageURL(Context mContext) {
        return "http://storeongo.com/MobileBuild/demo?reference=";
        /*
         * CmRfAAAAv6lxrgzRzfbzhsGP6sSTqNYPEoZIe4li-
		 * vHeLiiQkoq6CzXgXjWLqAIFzxjYeqGkLwdh99ikXC
		 * TThgy5ohRlLF1pKiEZK-aXVI-_m5w3e88UWPvokifHLrI3HukKHchtEhD9lbBze9cn-
		 * eoBtSwyTwTbGhTayOCW_ERf9x8EddtDTNxjKYeo5A
		 */
    }

    /**
     * @param mContext
     * @param mallId
     * @return URL for get all product categories
     */
    public static String changeJobStatus(Context mContext) {
        return getHostUrl(mContext) + "/Services/updateConsumerJobMetaData?";
        /* userId={mallId}&jobId=556&op={nexStatusId} */
    }

    /**
     * @param mContext
     * @return URL to register MacId
     */
    public static String registerMacId(Context mContext) {
        return getHostUrl(mContext) + "/Services/registerMacId?";
        /* macId&email=orgMail&orgId=orgName&cat=DEVICES */
    }

    /**
     * @param mallId
     * @param mContext
     * @return URL for get all PSubCategories
     */
    public static String get_PSubCategories(String mallId, Context mContext) {
        return getHostUrl(mContext) + "/Services/getMasters?type=PSubCategories&mallId=" + mallId;
    }

    /**
     * @param mallId
     * @param jobId
     * @param mContext
     * @return URL for get ParticularJob CalenderEvents
     */
    public static String getParticularJobCalenderEvents(String mallId,
                                                        String jobId, Context mContext) {
        return getHostUrl(mContext)
                + "/Services/getMasters?refTypeProperty=jobId&mallId=" + mallId
                + "&type=CalenderEvents&refId=" + jobId;
    }

    /**
     * @param mallId
     * @param mContext
     * @return URL for get All CalenderEvents
     */
    public static String getAllCalenderEvents(String mallId, Context mContext) {
        return getHostUrl(mContext) + "/Services/getMasters?mallId=" + mallId
                + "&type=CalenderEvents";
    }

    /**
     * @param mallId
     * @param mContext
     * @return URL for get all P3rdCategory
     */
    public static String get_P3rdCategory(String mallId, Context mContext) {
        return getHostUrl(mContext)
                + "/Services/getMasters?type=P3rdLevelCategories&mallId="
                + mallId;
    }

    /**
     * @param mallId
     * @param type
     * @param mContext
     * @return URL for get all Stores
     */
    public static String get_Stores(String mallId, String type, Context mContext) {
        return getHostUrl(mContext) + "/Services/getMasters?type=" + type + "&mallId=" + mallId;
    }

    /**
     * @param mallId
     * @param mContext
     * @return URL for get all ServiceCategories
     */
    public static String get_ServiceCategories(String mallId, Context mContext) {
        return getHostUrl(mContext)
                + "/Services/getMasters?type=ServicesCategories&mallId="
                + mallId;
    }

    /**
     * @param mallId
     * @param type
     * @return URL for get all Final Products
     */

    /**
     * @param mallId
     * @param id
     * @param mContext
     * @return URL for get all ServiceJobTypes
     */
    public static String get_ServiceJobTypes(String mallId, String id,
                                             Context mContext) {
        if (id != null) {
            return getHostUrl(mContext)
                    + "/Services/getMasters?type=allServicesJobTypes" + "|"
                    + id + "&mallId=" + mallId;
        } else {
            return getHostUrl(mContext)
                    + "/Services/getMasters?type=allServicesJobTypes&mallId="
                    + mallId;
        }
    }

    /**
     * @param mallId
     * @param mContext
     * @return URL for get all ProductJobTypes
     */
    public static String getProductJobTypes(String mallId, Context mContext) {
        return getHostUrl(mContext)
                + "/Services/getMasters?type=allJobTypes&mallId=" + mallId;
    }

    /**
     * @param mallId
     * @param type
     * @return URL for get all Suggested Products
     */
    public static String getSuggestedProducts(Context mContext, String mallId,
                                              String type, String refTypeProperty, String refId) {
        return getHostUrl(mContext) + "/Services/getMasters?refTypeProperty=" + refTypeProperty + "&refId=" + refId + "&unlimited=false&pageNumber=1&pageSize=10&type=" + type + "&mallId=" + mallId;
    }

    /**
     * @param userId
     * @param type
     * @param mContext
     * @return URL for get My posted Services
     */
    public static String get_myServices(String userId, String type,
                                        String mallId, Context mContext) {
        return getHostUrl(mContext) + "/Services/getMyJobs?consumerId=" + userId + "&type=" + type + "&mallId=" + mallId;
    }

    public static String getMyJobs(String jobId, Context mContext) {
        return getHostUrl(mContext) + "/Services/getMyJobs?jobId=" + jobId;
    }

    /**
     * @param jobId
     * @param mContext
     * @return URL for get My Sub Services
     */
    public static String get_SubService(String jobId, Context mContext) {
        return getHostUrl(mContext) + "/Services/getMyJobs?jobId=" + jobId;
    }

    /**
     * @param mContext
     * @param pageNo
     * @return all Malls URL
     */
    public static String getAllMalls(Context mContext, String pageNo,
                                     String keyWord) {
        if (keyWord != null) {
            if (pageNo.equalsIgnoreCase("-1")) {
                return getHostUrl(mContext)
                        + "/Services/getMasters?type=allMalls&keyWord=" + keyWord;
            } else {
                return getHostUrl(mContext)
                        + "/Services/getMasters?type=allMalls&mainCategory=" + keyWord;
            }
        } else {
            return getHostUrl(mContext)
                    + "/Services/getMasters?type=allMalls&pageNumber=" + pageNo
                    + "&pageSize=" + pageSize;
        }
    }

    /**
     * @param mContext
     * @param category
     * @param locality
     */
    public static String getSearchMalls(Context mContext, String category,
                                        String locality) {
        // http://storeongo.com:8081/MobileAPIs/searchResults?name=tabla&address=Hyderabad,
        // Telangana, India
        return getHostUrl(mContext) + "/MobileAPIs/searchResults?name=" + category + "&address=" + locality;
    }

    /**
     * @param mContext
     * @return URL to register User
     */
    public static String get_Registered(Context mContext) {
        return getHostUrl(mContext) + "/MobileAPIs/regAndloyaltyAPI?";
        /*
         * if user will registerd manually use this api
		 * http://exapplor.com:8081/
		 * MobileAPIs/regAndloyaltyAPI?orgId=3&userEmailId
		 * =cxsample2@gmail.com&dt
		 * =DEVICES&firstName=Cx&lastName=Sample2&password=123
		 *
		 * if user will register with facebook use this api
		 * http://exapplor.com:8081
		 * /MobileAPIs/regAndloyaltyAPI?orgId=3&userEmailId=
		 * cxsample@gmail.com&dt=DEVICES&firstName=Cx&lastName=Sample&filePath=
		 * "GIVE PROFILE IMAGE PATH HERE"
		 * &userBannerPath="GIVE BANNER PATH HERE"&isLoginWithFB=true
		 */
    }

    /**
     * @param mContext
     * @return getupdate profile
     */
    public static String get_UpdateProfile(Context mContext) {
        return getHostUrl(mContext) + "/MobileAPIs/updateUserDetails?";

		/*
         * http://exapplor.com:8081/MobileAPIs/updateUserDetails?orgId=3&email=
		 * cxsample@gmail.com&dt=DEVICES&firstName=
		 * Cx&lastName=Sample&address=&mobileNo
		 * =&city=&state=&country=&userImagePath
		 * ="GIVE PROFILE IMAGE PATH"&userBannerPath="GIVE COVE IMAGE PATH"
		 */
    }

    /**
     * @param fbUserId
     * @return fb profileImage
     */
    public static String getFacebookProfileImage(String fbUserId) {
        return "https://graph.facebook.com/" + fbUserId + "?fields=picture";
    }

    /**
     * @param fbUserId
     * @return fb coverImage
     */
    public static String getFacebookCoverImage(String fbUserId) {
        return "https://graph.facebook.com/" + fbUserId + "?fields=cover";
    }

    /**
     * @param mContext
     * @return URL to login
     */
    public static String login(Context mContext) {
        return getHostUrl(mContext) + "/MobileAPIs/loginConsumerForOrg?";
        /* storeongo admin */
        /* orgId=199&email=test1@sog.com&dt=DEVICES&password=123 */
    }

    /**
     * @param mContext
     * @return URL to Change Password
     */
    public static String change_Pwd(Context mContext) {
        return getHostUrl(mContext) + "/MobileAPIs/changePassword?";
        /* cPassword=0chtYdVw&nPassword=321&nrPassword=321&email=phani.k@ */
        /* creativexperts.co.in */
    }

    /**
     * @param mContext
     * @return URL to get Forgot Password
     */
    public static String forgot_Pwd(Context mContext) {
        return getHostUrl(mContext) + "/MobileAPIs/forgotpwd?";
        /* email=phani.k@creativexperts.co.in&remail= */
        /* phani.k@creativexperts.co.in */
    }

    /**
     * @param mallId
     * @param mContext
     * @return URL for general offers
     */
    public static String getNotifications(String mallId, String offerName, Context mContext) {
        return getHostUrl(mContext) + "/Services/getMasters?type=" + offerName + "&mallId=" + mallId;
    }

    /**
     * @param mallId
     * @param mContext
     * @return URL for Special Offers
     */
    public static String get_Special_Offers(String mallId, Context mContext) {
        return getHostUrl(mContext)
                + "/Services/getMasters?type=SpecificOffers&mallId=" + mallId;
    }

    /**
     * @param mContext
     * @return URL for update user details
     */
    public static String get_Update(Context mContext) {
        return getHostUrl(mContext) + "/MobileAPIs/updateUserDetails?";
        /*
         * "email=cxsample@gmail.com&fullName=cxsample&mobileNo=111111111&address=hitech%20city&city=Hyderabad&state=AP&country=India"
		 * ;
		 */
    }

    /**
     * @param mContext
     * @return contries
     */
    public static String get_Countries(Context mContext) {
        return getHostUrl(mContext) + "/Services/countries";
    }

    /**
     * @param mContext
     * @return URL to post reviews
     */
    public static String postReviews(Context mContext) {
        return getHostUrl(mContext) + "/jobs/saveJobCommentJSON?";
        /* userId=11&jobId=239&comment=excellent&rating=0.5&commentId=74 */
    }

    /**
     * @param mContext
     * @return URL for post Loyality
     */
    public static String postLoyality(Context mContext) {
        return getHostUrl(mContext) + "/Services/createORGetJobInstance?";
        /*
		 * email=vinodhapudari@gmail.com&orgId=3(mallId)
		 * &activityName=Register(SKYPE,WHATSAPP)&loyalty=true&ItemCodes=
		 * 1403328294834_3;white13&trackOnlyOnce=false
		 */
    }

    public static String uploadFile(Context mContext) {
        return getHostUrl(mContext) + "/Services/uploadFileFromAndroidToServer";
    }

    public static String postServices(Context mContext) {
        return getHostUrl(mContext) + "/MobileAPIs/postedJobs?";
		/*
		 * type=ContactUs&json={"list":[{"Name":"gujjj","Email%20Id":
		 * "bjvwdj@gjgig.com","Description":"huijj","PhoneNumber":"yuuj"}]}
		 * &dt=CAMPAIGNS
		 * &category=Services&userId=12&consumerEmail=consumer5@s.com
		 */

        /**
         * Creating Custom Tabs
         *
         * type=customTabName&json={"list":[{"Name":"vinodha","Description":
         * "description","storeId":"defaultStoreID"}]}
         * &dt=CAMPAIGNS&category=CustomHtmls
         * &userId=mallID&consumerEmail=cxsample@gmail.com
         * */
    }

    public static String placeOrder(Context mContext) {
        return getHostUrl(mContext) + "/MobileAPIs/postedJobs?";
		/*
		 * type=PlaceOrder&json={"list":[{"Name":"vinodha","OrderItemId":
		 * "961`9612|956`9563"
		 * ,"OrderItemQuantity":"2`9612|3`9563","Address":"hyderabad"
		 * ,"Contact_Number"
		 * :"9898989898"}]}&dt=CAMPAIGNS&category=Services&userId
		 * =16&consumerEmail=cxsample@gmail.com
		 */

    }

    public static String cancelOrder(String ownerId, String orderJobId, String consumerEmail, Context mContext) {
        return getHostUrl(mContext) + "/MobileAPIs/cancelOrder?ownerId=" + ownerId + "&orderJobId=" + orderJobId + "&consumerEmail=" + consumerEmail;
		/*
		 * type=PlaceOrder&json={"list":[{"Name":"vinodha","OrderItemId":
		 * "961`9612|956`9563"
		 * ,"OrderItemQuantity":"2`9612|3`9563","Address":"hyderabad"
		 * ,"Contact_Number"
		 * :"9898989898"}]}&dt=CAMPAIGNS&category=Services&userId
		 * =16&consumerEmail=cxsample@gmail.com
		 */

    }
//    /**
//     * @param userId
//     * @param mallId
//     * @param mContext
//     * @return mall id is equal to user id
//     */
//    public static String getPaymentDetails(String userId, String mallId, Context mContext) {
//        return getHostUrl(mContext) + "/Instamojo/getUserEcash?consumerId=" + userId + "&type=PlaceOrder&mallId=" + mallId;
//        //"http://storeongo.com:8081macId=ef1575c4-9a95-4946-8a39-0c729c544655&mallId=20408");
//    }

    /**
     * @param userId
     * @param mallId
     * @param mContext
     * @return mall id is equal to user id
     */
    public static String getOrders(String userId, String mallId, Context mContext) {
        return getHostUrl(mContext) + "/Services/getMasters?consumerId=" + userId + "&type=PlaceOrder&mallId=" + mallId;
    }

    /**
     * @param tabName
     * @param mallId
     * @param mContext
     * @return URL for custom tabs
     */
    public static String getCustomTabs(String tabName, String mallId,
                                       Context mContext) {

        return getHostUrl(mContext) + "/Services/getMasters?&type=" + tabName + "&mallId=" + mallId;
    }

    /**
     * @param mallId
     * @param mContext
     * @return URL for customHtml views in landing page
     */
    public static String getCustomHTML(String mallId, Context mContext) {

        return getHostUrl(mContext) + "/MobileAPIs/getCustomJobs?category=Custom&mallId=" + mallId;
    }

    /**
     * @param email
     * @param ownerId
     * @param mContext
     * @return loyal points with email and owner id
     */
    public static String get_LoyaltyCard(String email, String ownerId, Context mContext) {
        return getHostUrl(mContext) + "/MobileAPIs/getConJobInstances?email=" + email + "&ownerId=" + ownerId;
		/*
		 * http://exapplor.com:8081/MobileAPIs/getConJobInstances?email=cxsample@
		 * gmail.com&ownerId=3
		 */
    }

    public static String delete_Recard(Context mContext) {
        return getHostUrl(mContext) + "/Jobs/jobToDelete?";
		/* http://exapplor.com:8081/Jobs/jobToDelete?jobId=24399 */
    }

    /**
     * @param jobId
     * @param mContext
     * @return campaigns (shared products or offers)
     */
    public static String getSingleJob(String jobId, Context mContext) {
        return getHostUrl(mContext) + "/Services/getMasters?jobId=" + jobId;
		/*
		 * http://exapplor.com:8081/Services/getMasters?jobId=1140&myJobs=true
		 */
    }

    /**
     * @param mallId
     * @param jobs
     * @param mContext
     * @return preferred jobs
     */
    public static String getPreffredJobs(String mallId, String jobs, Context mContext) {
        return getHostUrl(mContext) + "/Services/getMasters?mallId=" + mallId + "&PrefferedJobs=" + jobs;
    }

    /**
     * @param mallId
     * @return Notifications
     */
    public static String getNotificationCategories(Context mContext, String mallId) {
        return getHostUrl(mContext) + "/Services/getMasters?type=NotificationCategories&mallId=" + mallId;
    }

    public static String getHostUrl(Context mContext) {
        return Utils.getString(mContext, R.string.server_api_url);
    }

    public static String getPaymentHostUrl(Context mContext) {
        return Utils.getString(mContext, R.string.server_payment_url);
    }

    public static String getWeblinks(Context mContext, String mallId) {
        return getHostUrl(mContext) + "/Users/getWeblinks?mallId=" + mallId;
    }

    /**
     * @param mContext
     * @param mallId
     * @param refId
     * @return
     */
    public static String getAssesmentQuiz(Context mContext, String mallId, String refId) {
        return "" + getHostUrl(mContext) + "/services/getmasters?type=Questions&mallId=" + mallId + "&refTypeProperty=QuizName&refId=" + refId;
		/*http://storeongo.com:8081/services/getmasters?type=Questions&mallId=3037&refTypeProperty=QuizName&refId=91159*/
    }

    public static String getAssesmentPoll(Context mContext, String mallId, String refId) {
        return "" + getHostUrl(mContext) + "/services/getmasters?type=Questions&mallId=" + mallId + "&refTypeProperty=PollName&refId=" + refId;
		/*http://storeongo.com:8081/services/getmasters?type=Questions&mallId=3037&refTypeProperty=QuizName&refId=91159*/
    }

    public static String getPollQuizSuervery(Context mContext, String surveyType, String mallId) {
        return "" + getHostUrl(mContext) + "/services/getmasters?type=" + surveyType + "&mallId=" + mallId;
				/*http://storeongo.com:8081/services/getmasters?type=Poll&mallId=3037*/
    }

    public static String getQuizSuervery(Context mContext, String mallId) {
        return "" + getHostUrl(mContext) + "/services/getmasters?type=Quiz" + "&mallId=" + mallId;
				/*http://storeongo.com:8081/services/getmasters?type=Poll&mallId=3037*/
    }

    public static String postQuizResults(Context mContext) {
        return "" + getHostUrl(mContext) + "/Services/saveORUpdateQuizResult?";
    }

    /*Service Form*/

    public static String postResourceSlots(Context mContext) {
        return getHostUrl(mContext) + "/MobileAPIs/postResourceSlots?";
    }

    public static String uploadAttachments(Context mContext) {
        return getHostUrl(mContext) + "/Services/uploadMultipleFiles?";
    }
    public static String deleteAllAttachments(Context mContext) {
        return getHostUrl(mContext) + "/MobileAPIs/deleteJobAttachments?";
    }
}
