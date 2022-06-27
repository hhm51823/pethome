package cn.raths.org.domain;

import cn.raths.basic.domain.BaseDomain;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Department extends BaseDomain {

    /** 部门编号 */
    private String sn;
    /** 部门名称 */
    private String name;
    /** 上级路径 */
    private String dirPath;
    /** 状态 */
    private Integer state;
    /** 部门管理员 */
    private Integer manager_id;
    /** 上级部门id */
    private Long parent_id;

    /** 经理 */
    private Employee manager;

    /** 上级部门 */
    private Department parent;

    // 数据不为null则返回json数据，为null则不返回
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Department> children = new ArrayList<>();
}
