
package com.weboutin.sbs.restservice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;
import org.json.JSONException;

import com.weboutin.sbs.entity.User;
import com.weboutin.sbs.service.UserService;
import com.weboutin.sbs.utils.Utils;

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
			return Utils.buildResponse(0, "注册成功", new HashMap());
        } catch (Exception e) {
            Map result = new HashMap();
			Map data = new HashMap();
            if (e.getMessage() == null) {
                e.printStackTrace();
                return Utils.buildResponse(-1, "系统异常", new HashMap());
            }
            if (e.getMessage().equals("user already exist")) {
				return Utils.buildResponse(2, "用户已存在", new HashMap());
			} 
			e.printStackTrace();
            return Utils.buildResponse(-1, "系统异常", new HashMap());
        }
	}
}

