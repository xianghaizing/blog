package com.lyf.service;

import com.lyf.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class UserService {
  @Autowired
  private UserMapper userMapper;
}
