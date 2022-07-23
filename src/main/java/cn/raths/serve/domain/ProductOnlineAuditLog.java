package cn.raths.serve.domain;

import java.util.Date;
import cn.raths.basic.domain.BaseDomain;
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
public class ProductOnlineAuditLog extends BaseDomain{


    /**
     * 消息id
     */
    private Long productId;
    /**
     * 状态 0失败 1成功
     */
    private Integer state;
    /**
     * 审核人 如果为null系统审核
     */
    private Long auditId;
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date auditTime = new Date();
    /**
     * 备注
     */
    private String note;

    private Product product;


}
