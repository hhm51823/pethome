package cn.raths.org.controller;

import cn.raths.basic.utils.PageList;
import cn.raths.org.domain.Department;
import cn.raths.org.query.DepartmentQuery;
import cn.raths.org.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department2")
public class DepartmentController2 {

    @Autowired
    private IDepartmentService departmentService;
    /**
    * @Title: queryList
    * @Description: 高级查询
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/6/24 13:09
    * @Parameters: [departmentQuery]
    * @Return cn.raths.basic.utils.PageList<cn.raths.org.domain.Department>
    */
    @PostMapping
    @ApiOperation(value = "高级查询")
    public PageList<Department> queryList2(@RequestBody DepartmentQuery departmentQuery){
        System.out.println(departmentQuery);
        return departmentService.queryList2(departmentQuery);
    }
}
