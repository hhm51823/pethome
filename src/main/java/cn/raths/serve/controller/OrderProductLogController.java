package cn.raths.serve.controller;

import cn.raths.serve.service.IOrderProductLogService;
import cn.raths.serve.domain.OrderProductLog;
import cn.raths.serve.query.OrderProductLogQuery;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderProductLog")
public class OrderProductLogController {
    @Autowired
    public IOrderProductLogService orderProductLogService;


    /**
     * 保存和修改公用的
     * @param orderProductLog  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody OrderProductLog orderProductLog){
        try {
            if( orderProductLog.getId()!=null)
                orderProductLogService.update(orderProductLog);
            else
                orderProductLogService.save(orderProductLog);
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
            orderProductLogService.remove(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.error().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public OrderProductLog get(@PathVariable("id")Long id)
    {
        return orderProductLogService.loadById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<OrderProductLog> list(){

        return orderProductLogService.loadAll();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping()
    public PageList<OrderProductLog> json(@RequestBody OrderProductLogQuery query)
    {
        return orderProductLogService.queryList(query);
    }
}
