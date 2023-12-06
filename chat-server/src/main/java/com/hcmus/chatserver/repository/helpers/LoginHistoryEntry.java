package com.hcmus.chatserver.repository.helpers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginHistoryEntry {
    private long userId;
    private long loginTime;
    private long dctime;
}
