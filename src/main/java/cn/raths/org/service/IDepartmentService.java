package cn.raths.org.service;

import cn.raths.basic.utils.PageList;
import cn.raths.org.domain.Department;
import cn.raths.org.query.DepartmentQuery;

import java.util.List;

public interface IDepartmentService {
    // 查询所有
    List<Department> loadAll();

    // 根据id查询
    Department loadById(Long id);

    // 根据id删除
    void remove(Long id);

    // 修改
    void update(Department department);

    // 添加
    void save(Department department);

    // 高级查询
    PageList<Department> queryList(DepartmentQuery departmentQuery);

    // 高级查询
    PageList<Department> queryList2(DepartmentQuery departmentQuery);

    // 批量删除
    void patchDel(Long[] ids);

    List<Department> loadTree();
}
