package cn.raths.sys.controller;

import cn.raths.basic.annotation.PreAuthorize;
import cn.raths.sys.service.IMenuService;
import cn.raths.sys.domain.Menu;
import cn.raths.sys.query.MenuQuery;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    public IMenuService menuService;


    /**
     * 保存和修改公用的
     * @param menu  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody Menu menu){
        try {
            if( menu.getId()!=null)
                menuService.update(menu);
            else
                menuService.save(menu);
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
            menuService.remove(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.error().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public Menu get(@PathVariable("id")Long id)
    {
        return menuService.loadById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<Menu> list(){

        return menuService.loadAll();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<Menu> json(@RequestBody MenuQuery query)
    {
        return menuService.queryList(query);
    }


    @PatchMapping
    @PreAuthorize(name = "批量删除菜单", sn = "menu:patchDel")
    public AjaxResult patchDel(@ApiParam(value="主键ID数组",required = true) @RequestBody Long[] ids){
        try {
            menuService.patchDel(ids);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }
    @GetMapping("/tree")
    public List<Menu> menuTree(){
        return menuService.menuTree();
    }
}
