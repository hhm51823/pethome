package cn.raths.pet.controller;

import cn.raths.pet.service.IPetDetailService;
import cn.raths.pet.domain.PetDetail;
import cn.raths.pet.query.PetDetailQuery;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/petDetail")
public class PetDetailController {
    @Autowired
    public IPetDetailService petDetailService;


    /**
     * 保存和修改公用的
     * @param petDetail  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody PetDetail petDetail){
        try {
            if( petDetail.getId()!=null)
                petDetailService.update(petDetail);
            else
                petDetailService.save(petDetail);
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
            petDetailService.remove(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.error().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public PetDetail get(@PathVariable("id")Long id)
    {
        return petDetailService.loadById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<PetDetail> list(){

        return petDetailService.loadAll();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<PetDetail> json(@RequestBody PetDetailQuery query)
    {
        return petDetailService.queryList(query);
    }
}
