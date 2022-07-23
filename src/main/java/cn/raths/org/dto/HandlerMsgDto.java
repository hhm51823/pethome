package cn.raths.org.dto;

import lombok.Data;

@Data
public class HandlerMsgDto {
    private Long msgId;
    // 员工id
    private Long handler;
    private String note;
}
