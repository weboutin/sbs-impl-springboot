
package com.weboutin.sbs.restservice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;
import org.json.JSONException;

import com.weboutin.sbs.entity.User;
import com.weboutin.sbs.service.UserService;

import java.util.Map;
import java.util.HashMap;

@RestController
public class UserController {
	@PostMapping("/v1/users")
	public Map create(@RequestBody String payload) throws Exception {
		try {
            JSONObject input = new JSONObject(payload);
			String account = input.optString("account");
			String password = input.optString("password");
			Integer userId = UserService.register(account, password);
			Map result = new HashMap();
			Map data = new HashMap();
			result.put("code", 0);
			result.put("msg", "注册成功");
			result.put("data", data);
			return result;
        } catch (Exception e) {
            Map result = new HashMap();
			Map data = new HashMap();
            if (e.getMessage() == null) {
                e.printStackTrace();
                result.put("code", -1);
				result.put("msg", "系统异常");
				result.put("data", data);
            }
            if (e.getMessage().equals("user already exist")) {
                result.put("code", 2);
				result.put("msg", "用户已经存在");
				result.put("data", data);
			} else {
				e.printStackTrace();
                result.put("code", -1);
				result.put("msg", "系统异常");
				result.put("data", data);
			}
			return result;
        }
	}
}

