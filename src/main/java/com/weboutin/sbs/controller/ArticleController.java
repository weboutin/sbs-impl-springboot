package com.weboutin.sbs.controller;

import java.util.HashMap;
import java.util.Map;

import com.weboutin.sbs.service.ArticleService;
import com.weboutin.sbs.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {
    @PostMapping("/v1/articles")
    public Map create(@RequestBody String payload, @CookieValue(value="session-id") String sessionId) {
        try {
            JSONObject input = new JSONObject(payload);
            String title = input.optString("title");
            String content = input.optString("content");

            JSONObject session = Utils.parseSessionCookie(sessionId);
            Integer userId = session.optInt("userId");
            Integer articleId = ArticleService.create(userId, title, content);
            Map result = new HashMap();
            result.put("articleId", articleId);
            return Utils.buildResponse(0, "创建成功", result);
        } catch (JSONException e) {
            Map result = new HashMap();
            return Utils.buildResponse(1, "参数异常", result);
        } catch (Exception e) {
            Map result = new HashMap();
            if (e.getMessage() == null) {
                e.printStackTrace();
                return Utils.buildResponse(-1, "系统异常", result);
            }
            if (e.getMessage().equals("Access Denied")) {
                return Utils.buildResponse(-1, "无访问权限", result);
            } else {
                e.printStackTrace();
                return Utils.buildResponse(-1, "系统异常", result);
            }
        }
    }

}