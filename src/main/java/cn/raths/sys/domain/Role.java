package cn.raths.sys.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class Role extends BaseDomain{

    private String name;
    private String sn;

    private List<Long> menus;
    private List<Long> permissions;

    private List<Menu> ownMenus;

    private List<Long> permissionIds;
}
