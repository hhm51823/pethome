package cn.raths.pet.domain;

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
public class SearchMasterMsg extends BaseDomain{


    private String title;
    /**
     * 0 待审核 1 审核通过 -1 驳回 2已完成寻主
     */
    private Integer state = 0;
    /**
     * 宠物名称
     */
    private String name;
    private Integer age;
    private Boolean gender;
    /**
     * 毛色
     */
    private String coatColor;
    /**
     * fastdfs地址1,fastdfs地址1
     */
    private String resources;
    /**
     * 类型
     */
    private Long petType;
    private BigDecimal price;
    private String address;
    private Long userId;
    /**
     * 店铺id 消息分配的店铺
     */
    private Long shopId;
    private String note;


}
