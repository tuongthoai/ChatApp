package com.hcmus.chatserver.repository.helpers;

import lombok.Data;

@Data
public class UserLoginTimeEntry {
    private long loginTime;
    private String username;
    private String fullname;
}
