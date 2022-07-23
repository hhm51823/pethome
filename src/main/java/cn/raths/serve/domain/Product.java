package cn.raths.serve.domain;

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
 * @since 2022-07-21
 */
@Data
public class Product extends BaseDomain{


    /**
     * 服务名称
     */
    private String name;
    /**
     * 成本价
     */
    private BigDecimal costprice;
    /**
     * 售价
     */
    private BigDecimal saleprice;
    /**
     * 存放fastdfs地址，多个资源使用，分割
     */
    private String resources;
    /**
     * 销售数量
     */
    private Long salecount = 100L;
    /**
     * 状态：0下架 1上架
     */
    private Long state = 0L;
    /**
     * 下架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date offsaletime;
    /**
     * 上架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date onsaletime;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createtime = new Date();
    private Long shopId;

    private ProductDetail detail;
    private Shop shop;


}
