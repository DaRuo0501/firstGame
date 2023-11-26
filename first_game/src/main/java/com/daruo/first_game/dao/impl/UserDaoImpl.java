package com.daruo.first_game.dao.impl;

import com.daruo.first_game.dao.UserDao;
import com.daruo.first_game.dto.TempUser;
import com.daruo.first_game.dto.UserRegisterRequest;
import com.daruo.first_game.rowmapper.TempUserRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    private final static Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public TempUser getUserByName(String tempName) {

        String sql = "SELECT user_id, user_name, password, email, user_image_url, money, created_date, last_modified_date " +
                "FROM user WHERE user_name = :userName;";

        Map<String, Object> map = new HashMap<>();
        map.put("userName", tempName);

        List<TempUser> tempUserList = namedParameterJdbcTemplate.query(sql, map, new TempUserRowMapper());

        if (tempUserList.size() > 0) {
            return tempUserList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public TempUser getUserByEmail(String tempEmail) {

        String sql = "SELECT user_id, user_name, password, email, user_image_url, money, created_date, last_modified_date " +
                "FROM user WHERE email = :email;";

        Map<String, Object> map = new HashMap<>();
        map.put("email", tempEmail);

        List<TempUser> tempUserList = namedParameterJdbcTemplate.query(sql, map, new TempUserRowMapper());

        if (tempUserList.size() > 0) {
            return tempUserList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void register(UserRegisterRequest userRegisterRequest) {

        String sql = "INSERT INTO user(user_name, password, email, money, created_date, last_modified_date) " +
                "VALUES (:userName, :password, :email, :money, :createdDate, :lastModifiedDate);";

        Map<String, Object> map = new HashMap<>();
        map.put("userName", userRegisterRequest.getUserName());
        map.put("password", userRegisterRequest.getPassword());
        map.put("email", userRegisterRequest.getEmail());
        map.put("money", 100);

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
    }
}
