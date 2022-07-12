package cn.raths.sys.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.raths.basic.domain.BaseDomain;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Menu extends BaseDomain{


    private String name;
    private String component;
    private String url;
    private String icon;
    private Integer index;
    private Long parentId;
    private String intro;
    private Boolean state;
    private String dirPath;


    private Menu parent;
    // 保存子菜单
    // 数据不为null则返回json数据，为null则不返回
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Menu> children = new ArrayList<>();
}
