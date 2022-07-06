package cn.raths.user.domain;

import java.math.BigDecimal;
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
public class Logininfo extends BaseDomain{


    private String username;
    private String phone;
    private String email;
    private String salt;
    private String password;
    /**
     * 类型 - 0管理员，1用户
     */
    private Integer type;
    /**
     * 启用状态：true可用，false禁用
     */
    private Boolean disable = true;



}
