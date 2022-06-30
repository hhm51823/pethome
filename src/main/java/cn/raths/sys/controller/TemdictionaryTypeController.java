package cn.raths.sys.controller;

import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import cn.raths.sys.domain.TemdictionaryType;
import cn.raths.sys.query.TemdictionaryTypeQuery;
import cn.raths.sys.service.ITemdictionaryTypeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/temdictionaryType")
public class TemdictionaryTypeController {

    @Autowired
    private ITemdictionaryTypeService temdictionaryTypeService;

    /**
     * @Title: loadById
     * @Description: 根据ID查询
     * @Author: Lynn
     * @Version: 1.0
     * @Date:  2022/6/24 13:07
     * @Parameters: [id]
     * @Return cn.raths.org.domain.TemdictionaryType
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询")
    public TemdictionaryType loadById(@ApiParam(value = "部门主键", required = true) @PathVariable("id") Long id){
        return temdictionaryTypeService.loadById(id);
    }

    /**
     * @Title: loadAll
     * @Description: 查询所有部门
     * @Author: Lynn
     * @Version: 1.0
     * @Date:  2022/6/24 13:07
     * @Parameters: []
     * @Return java.util.List<cn.raths.org.domain.TemdictionaryType>
     */
    @GetMapping
    @ApiOperation(value = "查询所有")
    public List<TemdictionaryType> loadAll(){
        return temdictionaryTypeService.loadAll();
    }

    /**
     * @Title: addOrUpt
     * @Description: 新增或修改部门
     * @Author: Lynn
     * @Version: 1.0
     * @Date:  2022/6/24 13:07
     * @Parameters: [temdictionaryType]
     * @Return cn.raths.basic.utils.AjaxResult
     */
    @PutMapping
    @ApiOperation(value = "新增或修改部门")
    public AjaxResult addOrUpt(@ApiParam(value="部门对象",required = true) @RequestBody TemdictionaryType temdictionaryType){
        try {
            if(temdictionaryType.getId() == null){
                temdictionaryTypeService.save(temdictionaryType);
            }else {
                temdictionaryTypeService.update(temdictionaryType);
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
            temdictionaryTypeService.remove(id);
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
     * @Parameters: [temdictionaryTypeQuery]
     * @Return cn.raths.basic.utils.PageList<cn.raths.org.domain.TemdictionaryType>
     */
    @PostMapping
    @ApiOperation(value = "高级查询")
    public PageList<TemdictionaryType> queryList(@ApiParam(value="高级查询条件对象",required = true) @RequestBody TemdictionaryTypeQuery temdictionaryTypeQuery){
        return temdictionaryTypeService.queryList(temdictionaryTypeQuery);
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
            temdictionaryTypeService.patchDel(ids);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }
}
