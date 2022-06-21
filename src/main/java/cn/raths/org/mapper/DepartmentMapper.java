package cn.raths.org.mapper;

import cn.raths.org.domain.Department;
import cn.raths.org.query.DepartmentQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface DepartmentMapper {
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
    List<Department> queryList(DepartmentQuery departmentQuery);

    // 当前查询总条数
    Integer queryCount(DepartmentQuery departmentQuery);
}
