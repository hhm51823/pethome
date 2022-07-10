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
 * @since 2022-07-10
 */
@Data
public class Wxuser extends BaseDomain{


    private String openid;
    private String nickname;
    private Integer sex;
    private String address;
    private String headimgurl;
    private String unionid;
    private Long userId;

}
