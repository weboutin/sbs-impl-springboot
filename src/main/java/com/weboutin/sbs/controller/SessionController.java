package com.weboutin.sbs.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.weboutin.sbs.service.SessionService;
import com.weboutin.sbs.utils.Utils;


@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;
    
    @PostMapping("/v1/sessions")
	public Map create(HttpServletResponse response, @RequestBody String payload) throws Exception {
		try {
            JSONObject input = new JSONObject(payload);
			String account = input.optString("account");
			String password = input.optString("password");
            Integer userId = sessionService.auth(account, password);
            JSONObject sessionObj = new JSONObject();
            sessionObj.put("userId", userId);
            Utils.createAndSetSessionCookie(response, sessionObj);
            
			Map result = new HashMap();
			result.put("userId", userId);
			return Utils.buildResponse(0, "登录成功", result);
		} catch (JSONException e) {
			return Utils.buildResponse(1, "参数错误", new HashMap());
        } catch (Exception e) {
            if (e.getMessage() == null) {
                e.printStackTrace();
                return Utils.buildResponse(-1, "系统异常", new HashMap());
            }
            if (e.getMessage().equals("user not exist")) {
				return Utils.buildResponse(2, "账号或密码异常", new HashMap());
            } 
            if (e.getMessage().equals("password error")) {
                return Utils.buildResponse(2, "账号或密码异常", new HashMap());
            }
			e.printStackTrace();
            return Utils.buildResponse(-1, "系统异常", new HashMap());
        }
	}
}
