package cn.raths.order.controller;

import cn.raths.order.service.IOrderPetAcquisitionService;
import cn.raths.order.domain.OrderPetAcquisition;
import cn.raths.order.query.OrderPetAcquisitionQuery;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderPetAcquisition")
public class OrderPetAcquisitionController {
    @Autowired
    public IOrderPetAcquisitionService orderPetAcquisitionService;


    /**
     * 保存和修改公用的
     * @param orderPetAcquisition  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody OrderPetAcquisition orderPetAcquisition){
        try {
            if( orderPetAcquisition.getId()!=null)
                orderPetAcquisitionService.update(orderPetAcquisition);
            else
                orderPetAcquisitionService.save(orderPetAcquisition);
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
            orderPetAcquisitionService.remove(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.error().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public OrderPetAcquisition get(@PathVariable("id")Long id)
    {
        return orderPetAcquisitionService.loadById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<OrderPetAcquisition> list(){

        return orderPetAcquisitionService.loadAll();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping()
    public PageList<OrderPetAcquisition> json(@RequestBody OrderPetAcquisitionQuery query)
    {
        return orderPetAcquisitionService.queryList(query);
    }
}
