package cn.raths.org.controller;

import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import cn.raths.org.domain.Department;
import cn.raths.org.query.DepartmentQuery;
import cn.raths.org.service.IDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@Api(value = "部门的APT", description = "部门相关的CRUD")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    /**
    * @Title: loadById
    * @Description: 根据ID查询
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/6/24 13:07
    * @Parameters: [id]
    * @Return cn.raths.org.domain.Department
    */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询")
    public Department loadById(@ApiParam(value = "部门主键", required = true) @PathVariable("id") Long id){
        return departmentService.loadById(id);
    }

    /**
    * @Title: loadAll
    * @Description: 查询所有部门
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/6/24 13:07
    * @Parameters: []
    * @Return java.util.List<cn.raths.org.domain.Department>
    */
    @GetMapping
    @ApiOperation(value = "查询所有")
    public List<Department> loadAll(){
        return departmentService.loadAll();
    }

    /**
    * @Title: addOrUpt
    * @Description: 新增或修改部门
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/6/24 13:07
    * @Parameters: [department]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @PutMapping
    @ApiOperation(value = "新增或修改部门")
    public AjaxResult addOrUpt(@ApiParam(value="部门对象",required = true) @RequestBody Department department){
        try {
            if(department.getId() == null){
                departmentService.save(department);
            }else {
                departmentService.update(department);
            }
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    /**
    * @Title: remove
    * @Description: 根据ID删除
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/6/24 13:08
    * @Parameters: [id]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除")
    public AjaxResult remove(@ApiParam(value="主键ID",required = true) @PathVariable("id") Long id){
        try {
            departmentService.remove(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

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
    public PageList<Department> queryList(@ApiParam(value="高级查询条件对象",required = true) @RequestBody DepartmentQuery departmentQuery){
        return departmentService.queryList(departmentQuery);
    }
}