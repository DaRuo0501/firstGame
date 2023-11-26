package com.daruo.first_game.controller;

import com.daruo.first_game.dto.TempUser;
import com.daruo.first_game.dto.UserLoginRequest;
import com.daruo.first_game.dto.UserRegisterRequest;
import com.daruo.first_game.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/users/login"})
    public String loginPage(HttpSession session) {

        log.info("這是登入頁");

        session.removeAttribute("errorMessage");

        return "login";
    }

    @RequestMapping("/users/register")
    public String registerPage(HttpSession session) {

        log.info("這是使用者註冊");

        session.removeAttribute("errorMsg");

        return "register";
    }

    @RequestMapping("/users/register/create")
    public String register(@ModelAttribute @Valid UserRegisterRequest userRegisterRequest,
                           HttpSession session
    ) {

        String tempPassword = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if (tempPassword.equals(checkPassword)) {

            String user = userService.register(userRegisterRequest);

            if (("userNameNotNull").equals(user)) {

                String userNameNotNull = "帳號已存在，無法創建此帳號名，請重新輸入!";

                session.setAttribute("errorMsg", userNameNotNull);

                return "register";

            } else if (("emailNotNull").equals(user)) {

                String emailNotNull = "電子信箱已存在，無法使用此電子信箱，請重新輸入!";

                session.setAttribute("errorMsg", emailNotNull);

                return "register";
            }

            String createOk = "帳號創建成功，請登入!";

            session.setAttribute("errorMessage", createOk);

            return "login";

        } else {

            String checkPwd = "確認密碼輸入錯誤，請重新輸入!";

            session.setAttribute("errorMsg", checkPwd);

            return "register";
        }
    }

    // 登入
    @RequestMapping("/users/login/create")
    public String login(@ModelAttribute @Valid UserLoginRequest userLoginRequest,
                        HttpSession session,
                        Model model
    ) {

        log.info("這是首頁");

        session.removeAttribute("errorMessage");

        if (null != session.getAttribute("showUserName")) {

            String tempName = (String) session.getAttribute("showUserName");

            session.setAttribute("showUserName", tempName);

            log.info("menu的首頁按鈕");

        } else {

            TempUser tempUser = userService.login(userLoginRequest);

            if (tempUser != null) {

                session.setAttribute("showUserName", tempUser.getUserName());
            } else {

                String errorMessage = "帳號或密碼輸入錯誤，請重新輸入。";

                session.setAttribute("errorMessage", errorMessage);
                session.removeAttribute("showUserName");

                return "login";
            }

            log.info("db userName", tempUser.getUserName());
        }

        return "home";
    }

    @RequestMapping(value = {"/users/logout"})
    public String logoutPage(HttpSession session) {

        log.info("登出了，返回登入頁~");

        session.removeAttribute("showUserName");
        session.removeAttribute("errorMessage");

        return "login";
    }

}
