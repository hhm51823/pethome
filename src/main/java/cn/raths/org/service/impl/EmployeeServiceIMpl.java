package cn.raths.org.service.impl;

import cn.raths.basic.utils.PageList;
import cn.raths.org.domain.Employee;
import cn.raths.org.mapper.EmployeeMapper;
import cn.raths.org.query.EmployeeQuery;
import cn.raths.org.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceIMpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public List<Employee> loadAll() {
        return employeeMapper.loadAll();
    }

    @Override
    public Employee loadById(Long id) {
        return employeeMapper.loadById(id);
    }

    @Override
    public void remove(Long id) {
        employeeMapper.remove(id);
    }

    @Override
    public void update(Employee employee) {
        employeeMapper.update(employee);
    }

    @Override
    public void save(Employee employee) {
        employeeMapper.save(employee);
    }

    @Override
    public PageList<Employee> queryList(EmployeeQuery employeeQuery) {
        List<Employee> rows = employeeMapper.queryList(employeeQuery);
        Integer total = employeeMapper.queryCount(employeeQuery);
        return new PageList<>(total,rows);
    }
    @Override
    public void patchDel(Long[] ids) {
        employeeMapper.patchDel(ids);
    }
}
