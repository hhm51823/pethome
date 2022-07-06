package cn.raths.org.mapper;

import cn.raths.basic.mapper.BaseMapper;
import cn.raths.org.domain.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    Employee loadByUsername(String username);

    Employee loadByEmail(String email);

    Employee loadByPhone(String phone);
}
