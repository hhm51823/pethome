package cn.raths.user.controller;

import cn.raths.user.service.ILogininfoService;
import cn.raths.user.domain.Logininfo;
import cn.raths.user.query.LogininfoQuery;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logininfo")
public class LogininfoController {
    @Autowired
    public ILogininfoService logininfoService;


    /**
     * 保存和修改公用的
     * @param logininfo  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody Logininfo logininfo){
        try {
            if( logininfo.getId()!=null)
                logininfoService.update(logininfo);
            else
                logininfoService.save(logininfo);
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
            logininfoService.remove(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.error().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public Logininfo get(@PathVariable("id")Long id)
    {
        return logininfoService.loadById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<Logininfo> list(){

        return logininfoService.loadAll();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<Logininfo> json(@RequestBody LogininfoQuery query)
    {
        return logininfoService.queryList(query);
    }
}
