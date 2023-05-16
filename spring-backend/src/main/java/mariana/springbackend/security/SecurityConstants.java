package mariana.springbackend.security;

import mariana.springbackend.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_DATE = 864000000; // 10 DAYS, time in which the json web token is valid

    public static final String TOKEN_PREFIX = "Bearer "; // json web token

    public static final String HEADER_STRING = "Authorization"; // header through which we are going to send the token

    public static final String SIGN_UP_URL = "/users"; // url with which users are going to register in the system

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
