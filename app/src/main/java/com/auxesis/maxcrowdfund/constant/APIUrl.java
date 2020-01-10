package com.auxesis.maxcrowdfund.constant;

public class APIUrl {
    public final static String BASE_URL = "https://test.maxcrowdfund.com/";
    public final static String GER_LOGIN = BASE_URL + "api/user/login?_format=json";

    public final static String GER_MY_INVEST_DETAILS = BASE_URL + "api/fundraiser/details?_format=json";
    public static String investStatus = "active";
    public static String investment_status = "investment_status=";
    public static String page = "&page=";
    public final static String GER_LISTING = BASE_URL + "api/fundraiser/listing?_format=json&";
    public final static String GER_LOG_OUT = BASE_URL + "api/user/logout?_format=json&token=";

    // public final static String GER_CHECK_USER = "https://test.maxcrowdfund.com/user/login_status?_format=json";
    public final static String GER_CHECK_USER = BASE_URL + "user/login_status?_format=json";
    public final static String GER_ACCOUNT_BALANCE = BASE_URL + "api/account-balance";
    public final static String GER_PORTFOLIO = BASE_URL + "api/portfolio";
    public final static String GER_PROFILE = BASE_URL + "api/profile";

    //For Dashboard deposit
   // public final static String GET_ACTIVE_ACCOUNT_DETAILS = BASE_URL+"api/active-account-details";

    public final static String uUid ="258a2184-2842-b485-25ca-";
    public final static String mMethod_deposit ="Deposit";


    public final static String DASHBOARD_DEPOSIT_LOGIN_API = "https://test.trustly.com/api/1";

   /* public final static String SUCCESS_URL = BASE_URL+"deposit-amount/success";
    public final static String FAIL_URL = BASE_URL+"profile/";
*/

    //String Values to be Used in App
    public static final String downloadDirectory = "Androhub Downloads";
    public static final String mainUrl = "http://androhub.com/demo/";
    public static final String downloadPdfUrl = "http://androhub.com/demo/demo.pdf";
    public static final String downloadDocUrl = "http://androhub.com/demo/demo.doc";
    public static final String downloadZipUrl = "http://androhub.com/demo/demo.zip";
    public static final String downloadVideoUrl = "http://androhub.com/demo/demo.mp4";
    public static final String downloadMp3Url = "http://androhub.com/demo/demo.mp3";
}