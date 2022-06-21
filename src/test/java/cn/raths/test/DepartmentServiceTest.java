package cn.raths.test;

import cn.raths.base.BaseTest;
import cn.raths.org.mapper.DepartmentMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class DepartmentServiceTest extends BaseTest {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Test
    public void loadAll() {
        departmentMapper.loadAll().forEach(System.out::println);
    }
}