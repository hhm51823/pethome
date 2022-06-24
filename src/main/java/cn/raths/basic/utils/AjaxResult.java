package cn.raths.basic.utils;

import lombok.Data;

@Data
public class AjaxResult {

    private Boolean success = true;

    private String message = "操作成功！";

    public static AjaxResult getAjaxResult(){
        return new AjaxResult();
    }

    public static AjaxResult error(){
        return  getAjaxResult().setSuccess(false).setMessage("系统繁忙，请重试！");
    }

    public AjaxResult setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public AjaxResult setMessage(String message) {
        this.message = message;
        return this;
    }
}
