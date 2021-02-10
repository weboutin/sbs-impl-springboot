package com.weboutin.sbs.service;

import java.util.Date;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weboutin.sbs.entity.User;
import com.weboutin.sbs.mappers.UserMapper;
import com.weboutin.sbs.utils.MyBatisUtils;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public  Integer register(String account, String password) throws Exception {
        User user = userMapper.selectUserByAccount(account);
        if (user != null) {
            throw new Exception("user already exist");
        }
        Date date = new Date();
        Long now = date.getTime();
        User inUser = new User();
        inUser.account = account;
        inUser.password = password;
        inUser.createdAt = now;
        inUser.modifiedAt = now;
        userMapper.insertUser(inUser);
        return inUser.userId;
    }
}