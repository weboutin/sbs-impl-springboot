package com.weboutin.sbs.utils;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import java.util.HashMap;

public class Utils {
    final static String secretKey = "sbs-impl-cookie-secret-key";

    public static void createAndSetSessionCookie(HttpServletResponse response, JSONObject data) {
        String originalString = data.toString();
        String encryptedString = AES.encrypt(originalString, secretKey);
        Cookie mycookie = new Cookie("session-id", encryptedString);
        response.addCookie(mycookie);
    }

    public static Map<String, Object> buildResponse(Integer code, String message, Map data) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("msg", message);
        result.put("data", data);
        return result;
    }

    public static JSONObject parseSessionCookie(String sessionCookieStr) throws Exception {
        if (sessionCookieStr == null) {
            throw new Exception("Access Denied");
        }

        String decryptedString = null;

        JSONObject result = null;
        try {
            decryptedString = AES.decrypt(sessionCookieStr, secretKey);
            result = new JSONObject(decryptedString);
        } catch (Exception e) {
            throw new Exception("Cookie parse error");
        }
        return result;
    }

}