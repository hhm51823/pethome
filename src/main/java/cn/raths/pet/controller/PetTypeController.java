package cn.raths.pet.controller;

import cn.raths.org.domain.Department;
import cn.raths.pet.service.IPetTypeService;
import cn.raths.pet.domain.PetType;
import cn.raths.pet.query.PetTypeQuery;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/petType")
public class PetTypeController {
    @Autowired
    public IPetTypeService petTypeService;


    /**
     * 保存和修改公用的
     * @param petType  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody PetType petType){
        try {
            if( petType.getId()!=null)
                petTypeService.update(petType);
            else
                petTypeService.save(petType);
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
            petTypeService.remove(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.error().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public PetType get(@PathVariable("id")Long id)
    {
        return petTypeService.loadById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<PetType> list(){

        return petTypeService.loadAll();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<PetType> json(@RequestBody PetTypeQuery query)
    {
        return petTypeService.queryList(query);

    }

    @GetMapping("/tree")
    public List<PetType> loadTree(){
        return petTypeService.loadTree();
    }
}
