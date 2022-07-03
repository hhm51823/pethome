package cn.raths.org.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import cn.raths.basic.domain.BaseDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date auditTime = new Date();
    private String note;

    private Shop shop;
    private Employee manager;

}
