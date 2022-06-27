package cn.raths.org.service;

import cn.raths.basic.service.IBaseService;
import cn.raths.org.domain.Department;

import java.util.List;

public interface IDepartmentService extends IBaseService<Department> {

    /**
    * @Title: loadTree
    * @Description: 查询部门树数据
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/6/27 15:17
    * @Parameters: []
    * @Return java.util.List<cn.raths.org.domain.Department>
    */
    List<Department> loadTree();
}
