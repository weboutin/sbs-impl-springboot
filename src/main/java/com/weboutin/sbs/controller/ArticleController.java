package com.weboutin.sbs.controller;

import java.util.HashMap;
import java.util.Map;

import com.weboutin.sbs.service.ArticleService;
import com.weboutin.sbs.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {
    @PostMapping("/v1/articles")
    public Map create(@RequestBody String payload, @CookieValue(value = "session-id") String sessionId) {
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

    @DeleteMapping("/v1/articles/{articleId}")
    public Map remove(@PathVariable("articleId") Integer articleId, @CookieValue(value = "session-id") String sessionId)
            throws Exception {
        try {
            JSONObject session = Utils.parseSessionCookie(sessionId);
            Integer userId = session.optInt("userId");
            ArticleService.remove(userId, articleId);
            Map result = new HashMap();
            return Utils.buildResponse(0, "删除成功", result);
        } catch (NumberFormatException e) {
            Map result = new HashMap();
            return Utils.buildResponse(1, "参数异常", result);
        } catch (JSONException e) {
            Map result = new HashMap();
            return Utils.buildResponse(1, "参数异常", result);
        } catch (Exception e) {
            Map result = new HashMap();
            e.printStackTrace();
            if (e.getMessage() == null) {
                return Utils.buildResponse(-1, "系统异常", result);
            }
            if (e.getMessage().equals("Access Denied")) {
                return Utils.buildResponse(-1, "无访问权限", result);
            } else if (e.getMessage().equals("Remove error")) {
                return Utils.buildResponse(-1, "删除失败", result);
            } else if (e.getMessage().equals("Cookie parse error")) {
                return Utils.buildResponse(-1, "无访问权限", result);
            } else {
                return Utils.buildResponse(-1, "系统异常", result);
            }
        }

    }

    // /**
    // * DELETE /v1/articles/:articleId
    // */
    // public void doDelete(HttpServletRequest request, HttpServletResponse
    // response)
    // throws ServletException, IOException {
    // try {
    // String pathInfo = request.getPathInfo();
    // Integer articleId = null;
    // String[] pathParts = pathInfo.split("/");
    // articleId = Integer.parseInt(pathParts[1]);

    // JSONObject session = Utils.parseSessionCookie(request.getCookies());
    // Integer userId = session.optInt("userId");
    // ArticlesService.remove(userId, articleId);
    // JSONObject data = new JSONObject();
    // Utils.buildResponse(response, 0, "删除成功", data);
    // } catch (NumberFormatException e) {
    // JSONObject data = new JSONObject();
    // Utils.buildResponse(response, 1, "参数异常", data);
    // } catch (JSONException e) {
    // JSONObject data = new JSONObject();
    // Utils.buildResponse(response, 1, "参数异常", data);
    // } catch (Exception e) {
    // JSONObject data = new JSONObject();
    // e.printStackTrace();
    // if (e.getMessage() == null) {
    // Utils.buildResponse(response, -1, "系统异常", data);
    // }
    // if (e.getMessage().equals("Access Denied")) {
    // Utils.buildResponse(response, -1, "无访问权限", data);
    // } else if (e.getMessage().equals("Remove error")) {
    // Utils.buildResponse(response, -1, "删除失败", data);
    // } else if (e.getMessage().equals("Cookie parse error")) {
    // Utils.buildResponse(response, -1, "无访问权限", data);
    // } else {
    // Utils.buildResponse(response, -1, "系统异常", data);
    // }
    // }
    // }
}