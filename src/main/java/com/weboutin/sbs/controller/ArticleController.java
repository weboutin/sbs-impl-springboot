package com.weboutin.sbs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weboutin.sbs.annotation.NeedLogin;
import com.weboutin.sbs.entity.Article;
import com.weboutin.sbs.service.ArticleService;
import com.weboutin.sbs.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @NeedLogin
    @PostMapping("/v1/articles")
    public Map<String, Object> create(@RequestBody String payload, Integer userId) {
        try {
            JSONObject input = new JSONObject(payload);
            String title = input.optString("title");
            String content = input.optString("content");
            Integer articleId = articleService.create(userId, title, content);
            Map<String, Object> result = new HashMap<>();
            result.put("articleId", articleId);
            return Utils.buildResponse(0, "创建成功", result);
        } catch (JSONException e) {
            Map<String, Object> result = new HashMap<>();
            return Utils.buildResponse(1, "参数异常", result);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
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

    @NeedLogin
    @DeleteMapping("/v1/articles/{articleId}")
    public Map<String, Object> remove(@PathVariable("articleId") Integer articleId, Integer userId) throws Exception {
        try {
            articleService.remove(userId, articleId);
            Map<String, Object> result = new HashMap<>();
            return Utils.buildResponse(0, "删除成功", result);
        } catch (NumberFormatException e) {
            Map<String, Object> result = new HashMap<>();
            return Utils.buildResponse(1, "参数异常", result);
        } catch (JSONException e) {
            Map<String, Object> result = new HashMap<>();
            return Utils.buildResponse(1, "参数异常", result);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
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

    @GetMapping("/v1/articles")
    public Map<String, Object> list(@RequestParam(value = "page", defaultValue = "1") Integer page) throws Exception {
        int size = 10;
        List<Article> articles = articleService.getAll(page, size);
        Map<String, Object> result = new HashMap<>();
        result.put("articles", articles);
        return Utils.buildResponse(0, "获取成功", result);
    }

    @GetMapping("/v1/articles/{articleId}")
    public Map<String, Object> Detail(@PathVariable("articleId") Integer articleId) throws Exception {
        Article article = articleService.getDetail(articleId);
        Map<String, Object> result = new HashMap<>();
        result.put("article", article);
        return Utils.buildResponse(0, "获取成功", result);
    }
}