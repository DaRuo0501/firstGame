package com.daruo.first_game.service.impl;

import com.daruo.first_game.dao.UserDao;
import com.daruo.first_game.dto.TempUser;
import com.daruo.first_game.dto.UserLoginRequest;
import com.daruo.first_game.dto.UserRegisterRequest;
import com.daruo.first_game.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public TempUser login(UserLoginRequest userLoginRequest) {

        String tempName = userLoginRequest.getUserName();

        TempUser tempUser = userDao.getUserByName(tempName);

        if (tempUser == null) {

            log.warn("查無此帳號!");
            return null;
        }

        // 使用 MD5 生成密碼得雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        // 比較 password
        if (tempUser.getPassword().equals(hashedPassword)) {

            return tempUser;
        } else {

            log.warn("登入密碼錯誤! db psw :" + tempUser.getPassword() + "  使用者輸入 psw :" + hashedPassword);
            return null;
        }
    }

    @Override
    public String register(UserRegisterRequest userRegisterRequest) {

        TempUser checkName = userDao.getUserByName(userRegisterRequest.getUserName());

        TempUser checkEmail = userDao.getUserByEmail(userRegisterRequest.getEmail());

        log.info("checkName :" + checkName);
        log.info("checkEmail :" + checkEmail);

        if (checkName != null) {

            log.info("帳號已存在");

            return "userNameNotNull";
        }

        if (checkEmail != null) {

            log.info("信箱已存在");

            return "emailNotNull";
        }

        // 使用 MD5 生成密碼得雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());

        userRegisterRequest.setPassword(hashedPassword);

        userDao.register(userRegisterRequest);

        return "login";
    }
}
