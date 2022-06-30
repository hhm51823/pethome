package cn.raths.sys.controller;

import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import cn.raths.sys.domain.TemdictionaryDetail;
import cn.raths.sys.query.TemdictionaryDetailQuery;
import cn.raths.sys.service.ITemdictionaryDetailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/temdictionaryDetail")
public class TemdictionaryDetailController {

    @Autowired
    private ITemdictionaryDetailService temdictionaryDetailService;

    /**
     * @Title: loadById
     * @Description: 根据ID查询
     * @Author: Lynn
     * @Version: 1.0
     * @Date:  2022/6/24 13:07
     * @Parameters: [id]
     * @Return cn.raths.org.domain.TemdictionaryDetail
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询")
    public TemdictionaryDetail loadById(@ApiParam(value = "部门主键", required = true) @PathVariable("id") Long id){
        return temdictionaryDetailService.loadById(id);
    }

    /**
     * @Title: loadAll
     * @Description: 查询所有部门
     * @Author: Lynn
     * @Version: 1.0
     * @Date:  2022/6/24 13:07
     * @Parameters: []
     * @Return java.util.List<cn.raths.org.domain.TemdictionaryDetail>
     */
    @GetMapping
    @ApiOperation(value = "查询所有")
    public List<TemdictionaryDetail> loadAll(){
        return temdictionaryDetailService.loadAll();
    }

    /**
     * @Title: addOrUpt
     * @Description: 新增或修改部门
     * @Author: Lynn
     * @Version: 1.0
     * @Date:  2022/6/24 13:07
     * @Parameters: [temdictionaryDetail]
     * @Return cn.raths.basic.utils.AjaxResult
     */
    @PutMapping
    @ApiOperation(value = "新增或修改部门")
    public AjaxResult addOrUpt(@ApiParam(value="部门对象",required = true) @RequestBody TemdictionaryDetail temdictionaryDetail){
        try {
            if(temdictionaryDetail.getId() == null){
                temdictionaryDetailService.save(temdictionaryDetail);
            }else {
                temdictionaryDetailService.update(temdictionaryDetail);
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
            temdictionaryDetailService.remove(id);
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
     * @Parameters: [temdictionaryDetailQuery]
     * @Return cn.raths.basic.utils.PageList<cn.raths.org.domain.TemdictionaryDetail>
     */
    @PostMapping
    @ApiOperation(value = "高级查询")
    public PageList<TemdictionaryDetail> queryList(@ApiParam(value="高级查询条件对象",required = true) @RequestBody TemdictionaryDetailQuery temdictionaryDetailQuery){
        return temdictionaryDetailService.queryList(temdictionaryDetailQuery);
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
            temdictionaryDetailService.patchDel(ids);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    @GetMapping("/children/{types_id}")
    @ApiOperation(value = "根据ID查询")
    public List<TemdictionaryDetail> loadByTypesId(@ApiParam(value = "部门主键", required = true) @PathVariable("types_id") Long types_id){
        return temdictionaryDetailService.loadByTypesId(types_id);
    }

    @PatchMapping("/types_id")
    @ApiOperation(value = "根据types_id批量删除")
    public AjaxResult patchDelByTypesId(@ApiParam(value="types_id数组",required = true) @RequestBody Long[] typesIds){
        try {
            temdictionaryDetailService.patchDelByTypesId(typesIds);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    @DeleteMapping("/types_id/{types_id}")
    @ApiOperation(value = "根据types_id删除")
    public AjaxResult removeByTypesId(@ApiParam(value="types_id",required = true) @PathVariable("types_id") Long types_id){
        try {
            temdictionaryDetailService.removeByTypesId(types_id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }
}
