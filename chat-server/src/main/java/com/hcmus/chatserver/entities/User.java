package com.hcmus.chatserver.entities;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String sex;
    private String address;

}
