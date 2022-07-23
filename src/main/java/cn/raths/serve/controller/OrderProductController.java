package cn.raths.serve.controller;

import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.jwt.UserInfo;
import cn.raths.basic.utils.LoginContext;
import cn.raths.order.dto.OrderConfirmationDto;
import cn.raths.org.domain.Employee;
import cn.raths.org.dto.HandlerMsgDto;
import cn.raths.org.service.IEmployeeService;
import cn.raths.pet.domain.SearchMasterMsg;
import cn.raths.pet.query.SearchMasterMsgQuery;
import cn.raths.serve.dto.HandlerProduceOrderDto;
import cn.raths.serve.service.IOrderProductService;
import cn.raths.serve.domain.OrderProduct;
import cn.raths.serve.query.OrderProductQuery;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/orderProduct")
public class OrderProductController {
    @Autowired
    public IOrderProductService orderProductService;


    @Autowired
    public IEmployeeService employeeService;


    /**
     * 保存和修改公用的
     * @param orderProduct  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody OrderProduct orderProduct){
        try {
            if( orderProduct.getId()!=null)
                orderProductService.update(orderProduct);
            else
                orderProductService.save(orderProduct);
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
    public AjaxResult handleCancel(@PathVariable("id") Long id){
        try {
            orderProductService.handleCancel(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.error().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public OrderProduct get(@PathVariable("id")Long id)
    {
        return orderProductService.loadById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<OrderProduct> list(){

        return orderProductService.loadAll();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping()
    public PageList<OrderProduct> json(@RequestBody OrderProductQuery query)
    {
        return orderProductService.queryList(query);
    }

    @PostMapping("/toHandle")
    public PageList<OrderProduct> toHandle(@RequestBody OrderProductQuery query, HttpServletRequest request)
    {
        UserInfo loginUser = LoginContext.getLoginUser(request);
        Employee employee = employeeService.loadByLogininfoId(loginUser.getLogininfo().getId());

        // 状态为0的订单
        query.setState(0);
        query.setEmpId(query.getEmpId());

        return orderProductService.queryList(query);
    }
    @PostMapping("/finish")
    public PageList<OrderProduct> finish(@RequestBody OrderProductQuery query, HttpServletRequest request)
    {
        UserInfo loginUser = LoginContext.getLoginUser(request);
        Employee employee = employeeService.loadByLogininfoId(loginUser.getLogininfo().getId());

        // 状态为2的所有订单
        query.setState(1);

        return orderProductService.queryList(query);
    }

    @PostMapping("/accept")
    public AjaxResult accept(@RequestBody HandlerProduceOrderDto handlerProduceOrderDto)
    {
        try {
            orderProductService.accept(handlerProduceOrderDto);
            return AjaxResult.getAjaxResult();
        }catch (BusinessException e){
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }
    //reject
    @PostMapping("/reject/{produceOrderId}")
    public AjaxResult reject(@PathVariable("produceOrderId") Long produceOrderId)
    {
        try {
            orderProductService.reject(produceOrderId);
            return AjaxResult.getAjaxResult();
        }catch (BusinessException e) {
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }
    @PostMapping("/emp")
    public PageList<OrderProduct> emp(@RequestBody OrderProductQuery query, HttpServletRequest request)
    {
            UserInfo loginUser = LoginContext.getLoginUser(request);
            Employee employee = employeeService.loadByLogininfoId(loginUser.getLogininfo().getId());
            query.setEmpId(employee.getId());
            return orderProductService.queryList(query);

    }

    @PostMapping("/confirm")
    public AjaxResult confirm(@RequestBody OrderConfirmationDto orderConfirmationDto)
    {
        try {
            orderProductService.confirm(orderConfirmationDto);
            return AjaxResult.getAjaxResult();
        }catch (BusinessException e) {
            e.printStackTrace();
            return AjaxResult.error().setMessage(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }
}
