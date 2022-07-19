package cn.raths.order.domain;

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
 * @since 2022-07-19
 */
@Data
public class OrderPetAcquisition extends BaseDomain{


    /**
     * 订单唯一编号
     */
    private String orderSn;
    /**
     * 摘要
     */
    private String digest;
    private Date lastcomfirmtime;
    /**
     * 0待处理 1完成 -1取消
     */
    private Integer state;
    private BigDecimal price;
    /**
     * 0垫付 1余额 2银行转账
     */
    private Integer paytype = 0;
    private Long petId;
    private Long userId;
    private Long shopId;
    /**
     * 收购宠物的地址
     */
    private String address;
    /**
     * 店员id
     */
    private Long empId;
    private Long searchMasterMsgId;



}
