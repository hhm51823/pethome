package cn.raths.org.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
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
 * @since 2022-07-01
 */
@Data
public class Shop extends BaseDomain{


    private String name;
    private String tel;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registerTime = new Date();
    /**
     * 店铺状态：0待审核，1以审核，-1驳回，2待激活
     */
    private Integer state = 0;
    private String address;
    private String logo;
    private Long adminId;

    private Employee admin;
}
