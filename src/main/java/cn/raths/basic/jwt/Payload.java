package cn.raths.basic.jwt;
import lombok.Data;

import java.util.Date;

/**
* @Description: 存储载荷数据，也就是用户数据
* @Author: Mr.She
* @Version: 1.0
* @Date:  2022/6/30 13:09  
*/
@Data
public class Payload<T> {

    private String id;  // jwt的id(token) 唯一标识
    private T userInfo;  // 存储用户信息、权限信息、菜单信息
    private Date expiration;  // JWT串过期时间
}