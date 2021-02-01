package com.weboutin.sbs.utils;

import java.io.*;
import java.util.Map;
import java.util.HashMap;

public class Utils {
    final static String secretKey = "sbs-impl-cookie-secret-key";


    public static String createAndSetSessionCookie() {
        // String originalString = data.toString();
        // String encryptedString = AES.encrypt(originalString, secretKey);
        // Cookie mycookie = new Cookie("session-id", encryptedString);
        // response.addCookie(mycookie);
        return "encryptedString";
    }
    
    public static Map buildResponse(Integer code, String message, Map data) {
        Map result = new HashMap();
        result.put("code", code);
        result.put("msg", message);
        result.put("data", data);
        return result;
    }

    // public static JSONObject parseSessionCookie(Cookie[] cookies) throws Exception {
    //     String sessionCookieStr = null;
    //     if (cookies == null) {
    //         throw new Exception("Access Denied");
    //     }
    //     for (int i = 0; i < cookies.length; i++) {
    //         String name = cookies[i].getName();
    //         String value = cookies[i].getValue();
    //         if (name.equals("session-id")) {
    //             sessionCookieStr = value;
    //         }
    //     }
    //     if (sessionCookieStr == null) {
    //         throw new Exception("Access Denied");
    //     }

    //     String decryptedString = null;

    //     JSONObject result = null;
    //     try {
    //         decryptedString = AES.decrypt(sessionCookieStr, secretKey);
    //         result = new JSONObject(decryptedString);
    //     } catch (Exception e) {
    //         throw new Exception("Cookie parse error");
    //     }
    //     return result;
    // }

}