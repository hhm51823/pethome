package cn.raths.org.controller;

import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import cn.raths.org.domain.Shop;
import cn.raths.org.query.ShopQuery;
import cn.raths.org.service.IShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/shop")
@Api(value = "部门的APT", description = "部门相关的CRUD")
public class ShopController {

    @Autowired
    private IShopService shopService;

    /**
    * @Title: loadById
    * @Description: 根据ID查询
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/6/24 13:07
    * @Parameters: [id]
    * @Return cn.raths.org.domain.Shop
    */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询")
    public Shop loadById(@ApiParam(value = "部门主键", required = true) @PathVariable("id") Long id){
        return shopService.loadById(id);
    }

    /**
    * @Title: loadAll
    * @Description: 查询所有部门
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/6/24 13:07
    * @Parameters: []
    * @Return java.util.List<cn.raths.org.domain.Shop>
    */
    @GetMapping
    @ApiOperation(value = "查询所有")
    public List<Shop> loadAll(){
        return shopService.loadAll();
    }

    /**
    * @Title: addOrUpt
    * @Description: 新增或修改部门
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/6/24 13:07
    * @Parameters: [shop]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @PutMapping
    @ApiOperation(value = "新增或修改部门")
    public AjaxResult addOrUpt(@ApiParam(value="部门对象",required = true) @RequestBody Shop shop){
        try {
            if(shop.getId() == null){
                shopService.save(shop);
            }else {
                shopService.update(shop);
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
            shopService.remove(id);
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
    * @Parameters: [shopQuery]
    * @Return cn.raths.basic.utils.PageList<cn.raths.org.domain.Shop>
    */
    @PostMapping
    @ApiOperation(value = "高级查询")
    public PageList<Shop> queryList(@ApiParam(value="高级查询条件对象",required = true) @RequestBody ShopQuery shopQuery){
        return shopService.queryList(shopQuery);
    }

    /**
    * @Title: patchDel
    * @Description: 批量删除
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/6/26 10:04
    * @Parameters: [ids]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @PatchMapping
    @ApiOperation(value = "根据ID批量删除")
    public AjaxResult patchDel(@ApiParam(value="主键ID数组",required = true) @RequestBody Long[] ids){
        try {
            shopService.patchDel(ids);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }
}
