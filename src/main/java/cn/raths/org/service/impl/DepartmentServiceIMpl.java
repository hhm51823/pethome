package cn.raths.org.service.impl;

import cn.raths.basic.utils.PageList;
import cn.raths.org.domain.Department;
import cn.raths.org.mapper.DepartmentMapper;
import cn.raths.org.query.DepartmentQuery;
import cn.raths.org.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceIMpl implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> loadAll() {
        return departmentMapper.loadAll();
    }

    @Override
    public Department loadById(Long id) {
        return departmentMapper.loadById(id);
    }

    @Override
    public void remove(Long id) {
        departmentMapper.remove(id);
    }

    @Override
    public void update(Department department) {
        departmentMapper.update(department);
    }

    @Override
    public void save(Department department) {
        departmentMapper.save(department);
    }

    @Override
    public PageList<Department> queryList(DepartmentQuery departmentQuery) {
        List<Department> rows = departmentMapper.queryList(departmentQuery);
        Integer total = departmentMapper.queryCount(departmentQuery);
        return new PageList<>(total,rows);
    }
}
