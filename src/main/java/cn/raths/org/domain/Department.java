package cn.raths.org.domain;

import lombok.Data;

@Data
public class Department {
    /** 主键id */
    private String id;
    /** 部门编号 */
    private String sn;
    /** 部门名称 */
    private String name;
    /** 上级路径 */
    private String dirPath;
    /** 状态 */
    private String state;
    /** 部门管理员 */
    private String manager_id;
    /** 上级部门id */
    private String parent_id;

    /** 经理 */
    private Employee manager;

    /** 上级部门 */
    private Department parent;
}
