package cn.raths.org.domain;

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
 * @since 2022-07-02
 */
@Data
public class ShopAuditLog extends BaseDomain{


    private Long shopId;
    private Long auditId;
    private Date auditTime = new Date();
    private String note;

}
