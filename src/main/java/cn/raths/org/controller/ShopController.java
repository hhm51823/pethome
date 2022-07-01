package cn.raths.org.controller;

import cn.raths.basic.exception.BusinessException;
import cn.raths.org.service.IShopService;
import cn.raths.org.domain.Shop;
import cn.raths.org.query.ShopQuery;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return shopService.loadById(id);
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
}
