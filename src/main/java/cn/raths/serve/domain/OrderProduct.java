package cn.raths.serve.domain;

import java.math.BigDecimal;
import java.util.Date;
import cn.raths.basic.domain.BaseDomain;
import cn.raths.org.domain.Shop;
import cn.raths.user.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lynn
 * @since 2022-07-21
 */
@Data
public class OrderProduct extends BaseDomain{


    /**
     * 摘要
     */
    private String digest;
    /**
     * 待支付0  完成1 取消-1 ,-2订单审核失败，-3拒绝 ,-4 员工取消
     */
    private Integer state = -1;
    private BigDecimal price;
    private String orderSn;
    private String paySn;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date lastPayTime;
    /**
     * 购买次数
     */
    private Integer num;
    /**
     * 上门地址
     */
    private String address;
    private Long productId;
    private Long userId;
    private Long shopId;
    private Long employeeId;

    private User user;
    private Shop shop;

}
