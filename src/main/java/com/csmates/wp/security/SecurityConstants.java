package com.csmates.wp.security;

//TODO should remove this constants from code, idk
public class SecurityConstants {
    // expiration time for jwt, =10 days
    public static final long EXPIRATION_TIME = 864_000_000;

    // salt to generate jwt
    //TODO must hide it somewhere
    public static String SECRET_JWT = "wGc00S4S'bBpi?X5oSpl!Jcu";

    // header name in response where to get jwt
    public static String HEADER_STRING = "Authorization";

    // special token prefix
    public static String TOKEN_PREFIX = "Bearer ";

    // url for sign up
    public static String SIGN_UP_URL = "/users/sign-up";
}
