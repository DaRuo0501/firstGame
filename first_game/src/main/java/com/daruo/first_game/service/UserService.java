package com.daruo.first_game.service;

import com.daruo.first_game.dto.TempUser;
import com.daruo.first_game.dto.UserLoginRequest;
import com.daruo.first_game.dto.UserRegisterRequest;

public interface UserService {
    TempUser login(UserLoginRequest userLoginRequest);

    String register(UserRegisterRequest userRegisterRequest);
}
