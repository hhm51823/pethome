package cn.raths.org.controller;

import cn.raths.org.service.IShopAuditLogService;
import cn.raths.org.domain.ShopAuditLog;
import cn.raths.org.query.ShopAuditLogQuery;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopAuditLog")
public class ShopAuditLogController {
    @Autowired
    public IShopAuditLogService shopAuditLogService;


    /**
     * 保存和修改公用的
     * @param shopAuditLog  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody ShopAuditLog shopAuditLog){
        try {
            if( shopAuditLog.getId()!=null)
                shopAuditLogService.update(shopAuditLog);
            else
                shopAuditLogService.save(shopAuditLog);
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
            shopAuditLogService.remove(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.error().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public ShopAuditLog get(@PathVariable("id")Long id)
    {
        return shopAuditLogService.loadById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<ShopAuditLog> list(){

        return shopAuditLogService.loadAll();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<ShopAuditLog> json(@RequestBody ShopAuditLogQuery query)
    {
        return shopAuditLogService.queryList(query);
    }

    @GetMapping("/shopId/{shopId}")
    public List<ShopAuditLog> getByShopId(@PathVariable("shopId")Long shopId)
    {
        return shopAuditLogService.getByShopId(shopId);
    }
}
