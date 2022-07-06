package cn.raths.org.controller;

import cn.raths.basic.exception.BusinessException;
import cn.raths.org.domain.ShopAuditLog;
import cn.raths.org.service.IShopService;
import cn.raths.org.domain.Shop;
import cn.raths.org.query.ShopQuery;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    public IShopService shopService;


    /**
     * 保存和修改公用的
     * @param shop  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody Shop shop){
        try {
            if( shop.getId()!=null)
                shopService.update(shop);
            else
                shopService.save(shop);
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
            shopService.remove(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.error().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public Shop get(@PathVariable("id")Long id)
    {
        Shop shop = shopService.loadById(id);

        return shop;
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<Shop> list(){
        return shopService.loadAll();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping()
    public PageList<Shop> json(@RequestBody ShopQuery query)
    {
        return shopService.queryList(query);
    }

    /**
    * @Title: settlement
    * @Description: 店铺入驻
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/6 18:18
    * @Parameters: [shop]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @PostMapping("/settlement")
    public AjaxResult settlement(@RequestBody Shop shop)
    {
        try {
            shopService.settlement(shop);
            return AjaxResult.getAjaxResult();
        }catch (BusinessException e){
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    /**
    * @Title: reject
    * @Description: 驳回店铺入驻请求
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/2 11:42
    * @Parameters: [shop]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @PostMapping("/audit/reject")
    public AjaxResult reject(@RequestBody ShopAuditLog shopAuditLog)
    {
        try {
            shopService.reject(shopAuditLog);
            return AjaxResult.getAjaxResult();
        }catch (BusinessException e){
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    /**
    * @Title: pass
    * @Description: 通过店铺入驻请求
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/2 11:42
    * @Parameters: [shop]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @PostMapping("/audit/pass")
    public AjaxResult pass(@RequestBody ShopAuditLog shopAuditLog)
    {
        try {
            shopService.pass(shopAuditLog);
            return AjaxResult.getAjaxResult();
        }catch (BusinessException e){
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    /**
    * @Title: prohibit
    * @Description: 拒绝店铺入驻请求
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/2 11:43
    * @Parameters: [shop]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @PostMapping("/audit/prohibit")
    public AjaxResult prohibit(@RequestBody ShopAuditLog shopAuditLog)
    {
        try {
            shopService.prohibit(shopAuditLog);
            return AjaxResult.getAjaxResult();
        }catch (BusinessException e){
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    @GetMapping("/activation/{id}")
    public String activation(@PathVariable("id")Long id)
    {
        return shopService.activation(id);
    }

    @GetMapping("/export/excel")
    public void exportExcel(HttpServletResponse response)
    {
        shopService.exportExcel(response);
    }

    @PostMapping("/import/excel")
    public AjaxResult importExcel(@RequestPart(value = "file", required = true)MultipartFile file)
    {
        try {
            shopService.importExcel(file);
            return AjaxResult.getAjaxResult();
        }catch (BusinessException e){
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    @GetMapping("/echarts")
    public Map<String,Object> echarts()
    {
        return shopService.echarts();
    }

}
