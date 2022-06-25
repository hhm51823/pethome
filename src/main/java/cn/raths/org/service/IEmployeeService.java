package cn.raths.org.service;

import cn.raths.basic.utils.PageList;
import cn.raths.org.domain.Employee;
import cn.raths.org.query.EmployeeQuery;

import java.util.List;

public interface IEmployeeService {
    // 查询所有
    List<Employee> loadAll();

    // 根据id查询
    Employee loadById(Long id);

    // 根据id删除
    void remove(Long id);

    // 修改
    void update(Employee employee);

    // 添加
    void save(Employee employee);

    // 高级查询
    PageList<Employee> queryList(EmployeeQuery employeeQuery);

    // 批量删除
    void patchDel(Long[] ids);
}
