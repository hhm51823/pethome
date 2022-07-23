package cn.raths.serve.controller;

import cn.raths.serve.service.IProductDetailService;
import cn.raths.serve.domain.ProductDetail;
import cn.raths.serve.query.ProductDetailQuery;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productDetail")
public class ProductDetailController {
    @Autowired
    public IProductDetailService productDetailService;


    /**
     * 保存和修改公用的
     * @param productDetail  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody ProductDetail productDetail){
        try {
            if( productDetail.getId()!=null)
                productDetailService.update(productDetail);
            else
                productDetailService.save(productDetail);
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
            productDetailService.remove(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.error().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public ProductDetail get(@PathVariable("id")Long id)
    {
        return productDetailService.loadById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<ProductDetail> list(){

        return productDetailService.loadAll();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping()
    public PageList<ProductDetail> json(@RequestBody ProductDetailQuery query)
    {
        return productDetailService.queryList(query);
    }
}
