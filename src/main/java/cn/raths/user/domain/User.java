package cn.raths.user.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import cn.raths.basic.domain.BaseDomain;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lynn
 * @since 2022-07-06
 */
@Data
public class User extends BaseDomain{


    private String username;
    private String phone;
    private String email;
    /**
     * 盐值
     */
    private String salt;
    /**
     * 密码，md5加密加盐
     */
    private String password;
    /**
     * 员工状态 - 1启用，0禁用
     */
    private Integer state = 1;
    private Integer age;
    private Date  createtime =  new Date();
    private String headImg;
    private Long logininfoId;



}
