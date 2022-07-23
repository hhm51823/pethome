package cn.raths.serve.controller;

import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.jwt.UserInfo;
import cn.raths.basic.utils.LoginContext;
import cn.raths.org.domain.Employee;
import cn.raths.pet.domain.Pet;
import cn.raths.pet.domain.PetDetail;
import cn.raths.serve.domain.ProductDetail;
import cn.raths.serve.dto.PurchaseDto;
import cn.raths.serve.service.IProductService;
import cn.raths.serve.domain.Product;
import cn.raths.serve.query.ProductQuery;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import cn.raths.user.domain.User;
import cn.raths.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    public IProductService productService;

    @Autowired
    public IUserService userService;


    /**
     * 保存和修改公用的
     * @param product  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody Product product){
        try {
            if( product.getId()!=null)
                productService.update(product);
            else
                productService.save(product);
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
            productService.remove(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.error().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public Product get(@PathVariable("id")Long id)
    {
        return productService.loadById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<Product> list(){

        return productService.loadAll();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping()
    public PageList<Product> json(@RequestBody ProductQuery query)
    {
        return productService.queryList(query);
    }
    /**
     * @Title: onsale
     * @Description: 服务批量上架
     * @Author: Lynn
     * @Version: 1.0
     * @Date:  2022/7/16 20:15
     * @Parameters: [ids]
     * @Return cn.raths.basic.utils.AjaxResult
     */
    @PostMapping("/onsale")
    public AjaxResult onsale(@RequestBody List<Product> products)
    {
        try {
            productService.onsale(products);
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
     * @Title: onsale
     * @Description: 服务批量下架
     * @Author: Lynn
     * @Version: 1.0
     * @Date:  2022/7/16 20:14
     * @Parameters: [ids]
     * @Return cn.raths.basic.utils.AjaxResult
     */
    @PostMapping("/offsale")
    public AjaxResult offsale(@RequestBody List<Long> ids)
    {
        try {
            productService.offsale(ids);
            return AjaxResult.getAjaxResult();
        }catch (BusinessException e){
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    // 获取详情
    @GetMapping("/detail/{id}")
    public ProductDetail detail(@PathVariable("id")Long id)
    {
        return productService.loaddetail(id);
    }

    // purchase
    @PostMapping("/purchase")
    public AjaxResult purchase(@RequestBody PurchaseDto purchaseDto, HttpServletRequest request)
    {
        try {
            UserInfo loginUser = LoginContext.getLoginUser(request);
            User user = userService.loadByLogininfoId(loginUser.getLogininfo().getId());
            purchaseDto.setUserId(user.getId());
            productService.purchase(purchaseDto);
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
