package cn.raths.pet.domain;


import cn.raths.basic.domain.BaseDomain;
import cn.raths.org.domain.Department;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lynn
 * @since 2022-07-16
 */
@Data
public class PetType extends BaseDomain{


    private String name;
    private String description;
    private String dirPath;
    /**
     * 父类型id
     */
    private Long pid;

    // 数据不为null则返回json数据，为null则不返回
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<PetType> children = new ArrayList<>();

}
