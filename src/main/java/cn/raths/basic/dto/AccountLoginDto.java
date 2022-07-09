package cn.raths.basic.dto;

import lombok.Data;

@Data
public class AccountLoginDto {

    private String account;
    private String checkPass;
    private String type;
}
