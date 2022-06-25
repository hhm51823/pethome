package cn.raths.org.service.impl;

import cn.raths.basic.utils.PageList;
import cn.raths.org.domain.Department;
import cn.raths.org.mapper.DepartmentMapper;
import cn.raths.org.mapper.DepartmentMapper2;
import cn.raths.org.query.DepartmentQuery;
import cn.raths.org.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceIMpl implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private DepartmentMapper2 departmentMapper2;

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
        // 用来判断dirPath是否被修改
        Department loadById = departmentMapper.loadById(department.getId());

        String dirPath = "";
        if(department.getParent() != null){
            Department deptTmp = departmentMapper.loadById(department.getParent().getId());
            if(deptTmp != null){
                dirPath = deptTmp.getDirPath();
            }
        }
        dirPath += "/" + department.getId();
        department.setDirPath(dirPath);
        departmentMapper.update(department);

        // 修改了父级部门

        if(!loadById.getDirPath().equals(department.getDirPath())){
            System.out.println("===================");
            List<Department> depts = departmentMapper.loadAll();
            List<Department> children = loadById.getChildren();
            for (Department dept : depts) {
                if (dept.getParent_id() == loadById.getId()) {
                    children.add(dept);
                }
            }
            System.out.println(children);
            System.out.println("===================");
            for (Department child : children) {
                System.out.println(child);
                child.setDirPath(dirPath + "/" + child.getId());
                departmentMapper.update(child);
            }
        }
    }

    @Override
    public void save(Department department) {

        departmentMapper.save(department);

        String dirPath = "";
        if(department.getParent() != null){
            Department deptTmp = departmentMapper.loadById(department.getParent().getId());
            if(deptTmp != null){
                dirPath = deptTmp.getDirPath();
            }
        }
        dirPath += "/" + department.getId();
        department.setDirPath(dirPath);
        System.out.println(department);
        departmentMapper.update(department);
    }

    @Override
    public PageList<Department> queryList(DepartmentQuery departmentQuery) {
        List<Department> rows = departmentMapper.queryList(departmentQuery);
        Integer total = departmentMapper.queryCount(departmentQuery);
        return new PageList<>(total,rows);
    }

    @Override
    public PageList<Department> queryList2(DepartmentQuery departmentQuery) {
        List<Department> rows = departmentMapper2.queryList2(departmentQuery);
        Integer total = departmentMapper2.queryCount2(departmentQuery);
        return new PageList<>(total,rows);
    }

    @Override
    public void patchDel(Long[] ids) {
        departmentMapper.patchDel(ids);
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

    @Override
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
