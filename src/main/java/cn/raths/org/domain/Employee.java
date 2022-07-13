package cn.raths.org.domain;

import cn.raths.basic.domain.BaseDomain;
import lombok.Data;

import java.util.List;

@Data
public class Employee extends BaseDomain {

    // 员工姓名
    private String username;
    // 员工电话
    private String phone;
    // 员工邮箱
    private String email;
    // 员工密码盐值
    private String salt;
    // 密码
    private String password;
    // 年龄
    private Integer age;
    // 是否启用 1启用，0禁用
    private Integer state = 1;
    // 部门id
    private Long departmentId;
    // 登录验证id
    private Long logininfoId;
    // 店铺id
    private Long shopId;

    // 所属部门
    private Department department;

    // 所属店铺
    private Shop shop;

    // 第二次输入密码
    private String comfirmPassword;


    private List<Long> roles;
}
