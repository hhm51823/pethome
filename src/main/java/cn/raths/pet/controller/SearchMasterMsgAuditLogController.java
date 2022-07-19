package cn.raths.pet.controller;

import cn.raths.pet.service.ISearchMasterMsgAuditLogService;
import cn.raths.pet.domain.SearchMasterMsgAuditLog;
import cn.raths.pet.query.SearchMasterMsgAuditLogQuery;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/searchMasterMsgAuditLog")
public class SearchMasterMsgAuditLogController {
    @Autowired
    public ISearchMasterMsgAuditLogService searchMasterMsgAuditLogService;


    /**
     * 保存和修改公用的
     * @param searchMasterMsgAuditLog  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody SearchMasterMsgAuditLog searchMasterMsgAuditLog){
        try {
            if( searchMasterMsgAuditLog.getId()!=null)
                searchMasterMsgAuditLogService.update(searchMasterMsgAuditLog);
            else
                searchMasterMsgAuditLogService.save(searchMasterMsgAuditLog);
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
            searchMasterMsgAuditLogService.remove(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.error().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public SearchMasterMsgAuditLog get(@PathVariable("id")Long id)
    {
        return searchMasterMsgAuditLogService.loadById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<SearchMasterMsgAuditLog> list(){

        return searchMasterMsgAuditLogService.loadAll();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping()
    public PageList<SearchMasterMsgAuditLog> json(@RequestBody SearchMasterMsgAuditLogQuery query)
    {
        return searchMasterMsgAuditLogService.queryList(query);
    }
}
