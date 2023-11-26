package com.daruo.first_game.dao;

import com.daruo.first_game.dto.TempUser;
import com.daruo.first_game.dto.UserRegisterRequest;

public interface UserDao {
    TempUser getUserByName(String tempName);

    TempUser getUserByEmail(String tempEmail);

    void register(UserRegisterRequest userRegisterRequest);
}
