package cn.raths.pet.domain;

import java.math.BigDecimal;
import java.util.Date;
import cn.raths.basic.domain.BaseDomain;
import cn.raths.org.domain.Shop;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lynn
 * @since 2022-07-16
 */
@Data
public class Pet extends BaseDomain{


    private String name;
    private BigDecimal costprice;
    private BigDecimal saleprice;
    /**
     * 类型id
     */
    private Long typeId;
    private String resources;
    /**
     * 状态：0下架 1上架
     */
    private Integer state = 0;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date offsaletime;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date onsaletime;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createtime = new Date();
    /**
     * 店铺Id 如果被领养店铺id为null
     */
    private Long shopId;
    /**
     * 如果被领养为领养用户id
     */
    private Long userId;
    private Long searchMasterMsgId;

    private PetDetail detail;

    private PetType type;
    private Shop shop;
}
