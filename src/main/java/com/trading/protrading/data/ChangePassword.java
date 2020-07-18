package com.trading.protrading.data;

import lombok.Data;

@Data
public class ChangePassword {
    private String username;
    private String oldPassword;
    private String newPassword;
}
