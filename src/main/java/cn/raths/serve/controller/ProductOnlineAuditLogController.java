package cn.raths.serve.controller;

import cn.raths.serve.service.IProductOnlineAuditLogService;
import cn.raths.serve.domain.ProductOnlineAuditLog;
import cn.raths.serve.query.ProductOnlineAuditLogQuery;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productOnlineAuditLog")
public class ProductOnlineAuditLogController {
    @Autowired
    public IProductOnlineAuditLogService productOnlineAuditLogService;


    /**
     * 保存和修改公用的
     * @param productOnlineAuditLog  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody ProductOnlineAuditLog productOnlineAuditLog){
        try {
            if( productOnlineAuditLog.getId()!=null)
                productOnlineAuditLogService.update(productOnlineAuditLog);
            else
                productOnlineAuditLogService.save(productOnlineAuditLog);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error().setMessage("保存对象失败！"+e.getMessage());
        }
    }
    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @DeleteMapping(value="/{id}")
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            productOnlineAuditLogService.remove(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.error().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public ProductOnlineAuditLog get(@PathVariable("id")Long id)
    {
        return productOnlineAuditLogService.loadById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<ProductOnlineAuditLog> list(){

        return productOnlineAuditLogService.loadAll();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping()
    public PageList<ProductOnlineAuditLog> json(@RequestBody ProductOnlineAuditLogQuery query)
    {
        return productOnlineAuditLogService.queryList(query);
    }
}
