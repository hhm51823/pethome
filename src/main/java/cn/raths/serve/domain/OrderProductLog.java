package cn.raths.serve.domain;

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
 * @since 2022-07-21
 */
@Data
public class OrderProductLog extends BaseDomain{


    private Long productorderId;
    private Integer state = 0;
    private Long auditId;
    private Date auditTime = new Date();
    private String note;

}
