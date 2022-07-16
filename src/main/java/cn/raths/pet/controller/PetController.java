package cn.raths.pet.controller;

import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.loginInfoMsg.LoginInfoMsg;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import cn.raths.pet.domain.Pet;
import cn.raths.pet.domain.PetDetail;
import cn.raths.pet.query.PetQuery;
import cn.raths.pet.service.IPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    public IPetService petService;


    /**
     * 保存和修改公用的
     * @param pet  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody Pet pet){
        try {
            if( pet.getId()!=null)
                petService.update(pet);
            else
                petService.save(pet);
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
            petService.remove(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.error().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public Pet get(@PathVariable("id")Long id)
    {
        return petService.loadById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<Pet> list(){

        return petService.loadAll();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping
    public PageList<Pet> json(@RequestBody PetQuery query)
    {
        if(LoginInfoMsg.INSTANCE.getEmployee() != null){
            query.setShopId(LoginInfoMsg.INSTANCE.getEmployee().getShopId());
        }
        return petService.queryList(query);
    }

    /**
    * @Title: onsale
    * @Description: 宠物批量上架
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/16 20:15
    * @Parameters: [ids]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @PostMapping("/onsale")
    public AjaxResult onsale(@RequestBody List<Pet> pets)
    {
        try {
            petService.onsale(pets);
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
    * @Description: 宠物批量下架
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
            petService.offsale(ids);
            return AjaxResult.getAjaxResult();
        }catch (BusinessException e){
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    //detail
    //获取用户
    @GetMapping("/detail/{id}")
    public PetDetail detail(@PathVariable("id")Long id)
    {
        return petService.loaddetail(id);
    }

}
