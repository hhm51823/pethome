package cn.raths.org.mapper;

import cn.raths.basic.mapper.BaseMapper;
import cn.raths.org.domain.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    Employee loadByUsername(String username);

    Employee loadByEmail(String email);

    Employee loadByPhone(String phone);

    void removeEmployeeRole(Long id);

    void saveEmployeeRole(@Param("id") Long id, @Param("rIds") List<Long> menus);

    Employee loadByLogininfoId(Long logininfoId);

    List<Employee> loadByShopId(Long shopId);
}
