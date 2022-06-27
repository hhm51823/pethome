package cn.raths.org.service.impl;

import cn.raths.basic.service.impl.BaseServiceImpl;
import cn.raths.org.domain.Department;
import cn.raths.org.mapper.DepartmentMapper;
import cn.raths.org.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceIMpl extends BaseServiceImpl<Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;


    @Override
    public void update(Department department) {
        // 用来判断dirPath是否被修改
        Department loadById = departmentMapper.loadById(department.getId());

        String dirPath = "";
        if(department.getParent().getId() != null) {
            Department deptTmp = departmentMapper.loadById(department.getParent().getId());
            if (deptTmp != null) {
                dirPath = deptTmp.getDirPath();
            }
        }
        dirPath += "/" + department.getId();
        department.setDirPath(dirPath);
        departmentMapper.update(department);

        // 修改了父级部门

        if(!loadById.getDirPath().equals(department.getDirPath())){
            List<Department> depts = departmentMapper.loadAll();
            for (Department dept : depts) {
                if (dept.getParent_id() == loadById.getId()) {
                    dept.setDirPath(dirPath + "/" + dept.getId());
                    departmentMapper.update(dept);
                }
            }
        }
    }

    @Override
    public void save(Department department) {

        departmentMapper.save(department);

        String dirPath = "";
        if(department.getParent().getId() != null) {
            Department deptTmp = departmentMapper.loadById(department.getParent().getId());
            if (deptTmp != null) {
                dirPath = deptTmp.getDirPath();
            }
        }
        dirPath += "/" + department.getId();
        department.setDirPath(dirPath);
        System.out.println(department);
        departmentMapper.update(department);
    }


    // 获取部门树数据
    /*@Override
    public List<Department> loadTree() {
        List<Department> departments = departmentMapper.loadAll();

        ArrayList<Department> deptTree = new ArrayList<>();

        for (Department deptTmp : departments) {
            if(deptTmp.getParent_id() == null){
                deptTree.add(deptTmp);
            }else{
                for (Department parent : departments) {
                    if(deptTmp.getParent_id() == parent.getId()){
                        parent.getChildren().add(deptTmp);
                        break;
                    }
                }
            }
        }
        return deptTree;
    }*/


    public List<Department> loadTree() {
        List<Department> departments = departmentMapper.loadAll();

        //  dept -> dept 给value赋值为当前对象
        Map<Long, Department> deptMap = departments.stream().collect(Collectors.toMap(Department::getId, dept -> dept));

        ArrayList<Department> deptTree = new ArrayList<>();

        for (Department dept : departments) {
            if(dept.getParent_id() == null){
                deptTree.add(dept);
            }else {
                deptMap.get(dept.getParent_id()).getChildren().add(dept);
            }
        }

        return deptTree;
    }
}
