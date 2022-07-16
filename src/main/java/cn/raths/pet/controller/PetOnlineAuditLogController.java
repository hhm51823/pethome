package cn.raths.pet.controller;

import cn.raths.pet.service.IPetOnlineAuditLogService;
import cn.raths.pet.domain.PetOnlineAuditLog;
import cn.raths.pet.query.PetOnlineAuditLogQuery;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/petOnlineAuditLog")
public class PetOnlineAuditLogController {
    @Autowired
    public IPetOnlineAuditLogService petOnlineAuditLogService;


    /**
     * 保存和修改公用的
     * @param petOnlineAuditLog  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody PetOnlineAuditLog petOnlineAuditLog){
        try {
            if( petOnlineAuditLog.getId()!=null)
                petOnlineAuditLogService.update(petOnlineAuditLog);
            else
                petOnlineAuditLogService.save(petOnlineAuditLog);
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
            petOnlineAuditLogService.remove(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.error().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public PetOnlineAuditLog get(@PathVariable("id")Long id)
    {
        return petOnlineAuditLogService.loadById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<PetOnlineAuditLog> list(){

        return petOnlineAuditLogService.loadAll();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<PetOnlineAuditLog> json(@RequestBody PetOnlineAuditLogQuery query)
    {
        return petOnlineAuditLogService.queryList(query);
    }
}
