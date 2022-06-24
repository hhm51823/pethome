package cn.raths.org.mapper;

import cn.raths.org.domain.Department;
import cn.raths.org.query.DepartmentQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper2 {

    // 高级查询
    List<Department> queryList2(DepartmentQuery departmentQuery);

    // 当前查询总条数
    Integer queryCount2(DepartmentQuery departmentQuery);
}
