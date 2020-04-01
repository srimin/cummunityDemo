package com.srimin.demo01springbootquickstart.service;

import com.srimin.demo01springbootquickstart.mapper.UserMapper;
import com.srimin.demo01springbootquickstart.model.User;
import com.srimin.demo01springbootquickstart.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class Userservice {
    @Autowired
    private UserMapper userMapper;
    public void createOrUpdate(User user){
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() != 0){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {

        }
    }
}
