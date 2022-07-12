package cn.raths.sys.domain;

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
 * @since 2022-07-11
 */
@Data
public class Permission extends BaseDomain{


    private String name;
    private String url;
    private String descs;
    private String sn;

}
