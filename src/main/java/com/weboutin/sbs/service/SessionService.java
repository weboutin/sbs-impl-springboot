package com.weboutin.sbs.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.weboutin.sbs.entity.User;
import com.weboutin.sbs.mappers.UserMapper;
import com.weboutin.sbs.utils.MyBatisUtils;

@Service
public class SessionService {

    public Integer auth(String account, String password) throws Exception {
        SqlSession session = MyBatisUtils.getSqlSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.selectUserByAccount(account);

        if (user == null) {
            throw new Exception("user not exist");
        }

        if (!password.equals(user.password)) {
            throw new Exception("password error");
        }

        return user.userId;
    }
}