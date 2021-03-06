package com.weboutin.sbs.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weboutin.sbs.entity.User;
import com.weboutin.sbs.mappers.ArticleMapper;
import com.weboutin.sbs.mappers.UserMapper;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    public Integer register(String account, String password) throws Exception {
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

    @Transactional
    public void remove(Integer userId) throws Exception {
        userMapper.removeUser(userId);
        articleMapper.removeArticlesByUserId(userId);
    }
}