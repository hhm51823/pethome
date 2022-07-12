package cn.raths.org.controller;

import cn.raths.basic.annotation.PreAuthorize;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import cn.raths.org.domain.Employee;
import cn.raths.org.query.EmployeeQuery;
import cn.raths.org.service.IEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@Api(value = "部门的APT", description = "部门相关的CRUD")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    /**
    * @Title: loadById
    * @Description: 根据ID查询
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/6/24 13:07
    * @Parameters: [id]
    * @Return cn.raths.org.domain.Employee
    */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询")
    public Employee loadById(@ApiParam(value = "部门主键", required = true) @PathVariable("id") Long id){
        return employeeService.loadById(id);
    }

    /**
    * @Title: loadAll
    * @Description: 查询所有部门
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/6/24 13:07
    * @Parameters: []
    * @Return java.util.List<cn.raths.org.domain.Employee>
    */
    @GetMapping
    @ApiOperation(value = "查询所有")
    @PreAuthorize(name = "员工列表", sn = "employee:loadAll")
    public List<Employee> loadAll(){
        return employeeService.loadAll();
    }

    /**
    * @Title: addOrUpt
    * @Description: 新增或修改部门
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/6/24 13:07
    * @Parameters: [employee]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @PutMapping
    @ApiOperation(value = "新增或修改部门")
    @PreAuthorize(name = "新增或修改员工", sn = "employee:saveOrUpt")
    public AjaxResult addOrUpt(@ApiParam(value="部门对象",required = true) @RequestBody Employee employee){
        try {
            if(employee.getId() == null){
                employeeService.save(employee);
            }else {
                employeeService.update(employee);
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
    @PreAuthorize(name = "删除员工", sn = "employee:delete")
    public AjaxResult remove(@ApiParam(value="主键ID",required = true) @PathVariable("id") Long id){
        try {
            employeeService.remove(id);
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
    * @Parameters: [employeeQuery]
    * @Return cn.raths.basic.utils.PageList<cn.raths.org.domain.Employee>
    */
    @PostMapping
    @ApiOperation(value = "高级查询")
    @PreAuthorize(name = "高级查询员工列别哦啊", sn = "employee:list")
    public PageList<Employee> queryList(@ApiParam(value="高级查询条件对象",required = true) @RequestBody EmployeeQuery employeeQuery){
        return employeeService.queryList(employeeQuery);
    }

    @PatchMapping
    @ApiOperation(value = "根据ID批量删除")
    @PreAuthorize(name = "批量删除员工", sn = "employee:patchDel")
    public AjaxResult patchDel(@ApiParam(value="主键ID数组",required = true) @RequestBody Long[] ids){
        try {
            employeeService.patchDel(ids);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }
}
