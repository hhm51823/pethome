package cn.raths.org.mapper;

import cn.raths.org.domain.Employee;
import cn.raths.org.query.EmployeeQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
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
    List<Employee> queryList(EmployeeQuery employeeQuery);

    // 当前查询总条数
    Integer queryCount(EmployeeQuery employeeQuery);

    // 根据id批量删除
    void patchDel(Long[] ids);
}
