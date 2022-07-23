package cn.raths.pet.controller;

import cn.raths.basic.dto.ShopRegisterDto;
import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.jwt.UserInfo;
import cn.raths.basic.utils.LoginContext;
import cn.raths.org.domain.Employee;
import cn.raths.org.dto.HandlerMsgDto;
import cn.raths.org.service.IEmployeeService;
import cn.raths.pet.service.ISearchMasterMsgService;
import cn.raths.pet.domain.SearchMasterMsg;
import cn.raths.pet.query.SearchMasterMsgQuery;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import cn.raths.user.domain.User;
import cn.raths.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/searchMasterMsg")
public class SearchMasterMsgController {
    @Autowired
    public ISearchMasterMsgService searchMasterMsgService;

    @Autowired
    public IUserService userService;

    @Autowired
    public IEmployeeService employeeService;


    /**
     * 保存和修改公用的
     * @param searchMasterMsg  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody SearchMasterMsg searchMasterMsg){
        try {
            if( searchMasterMsg.getId()!=null)
                searchMasterMsgService.update(searchMasterMsg);
            else
                searchMasterMsgService.save(searchMasterMsg);
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
            searchMasterMsgService.remove(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.error().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public SearchMasterMsg get(@PathVariable("id")Long id)
    {
        return searchMasterMsgService.loadById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<SearchMasterMsg> list(){

        return searchMasterMsgService.loadAll();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping()
    public PageList<SearchMasterMsg> json(@RequestBody SearchMasterMsgQuery query)
    {
        return searchMasterMsgService.queryList(query);
    }

    @PostMapping("/publish")
    public AjaxResult publish(@RequestBody SearchMasterMsg SearchMasterMsg, HttpServletRequest request)
    {
        try {
            UserInfo loginUser = LoginContext.getLoginUser(request);
            User user = userService.loadByLogininfoId(loginUser.getLogininfo().getId());
            SearchMasterMsg.setUserId(user.getId());
            searchMasterMsgService.publish(SearchMasterMsg);
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
    * @Title: user
    * @Description: 返回给前端用户
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/20 12:48
    * @Parameters: [query]
    * @Return cn.raths.basic.utils.PageList<cn.raths.pet.domain.SearchMasterMsg>
    */
    @PostMapping("/user")
    public PageList<SearchMasterMsg> user(@RequestBody SearchMasterMsgQuery query, HttpServletRequest request)
    {
        UserInfo loginUser = LoginContext.getLoginUser(request);
        User user = userService.loadByLogininfoId(loginUser.getLogininfo().getId());
        // 只需传递用户id即可
        query.setUserId(user.getId());
        return searchMasterMsgService.queryList(query);
    }

    /**
    * @Title: toAudit
    * @Description: 待审核的订单，只有平台管理元能看
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/20 12:51
    * @Parameters: [query, request]
    * @Return cn.raths.basic.utils.PageList<cn.raths.pet.domain.SearchMasterMsg>
    */
    @PostMapping("/toAudit")
    public PageList<SearchMasterMsg> toAudit(@RequestBody SearchMasterMsgQuery query)
    {
        // 状态为零的所有订单
        query.setState(0);
        return searchMasterMsgService.queryList(query);
    }

    /**
    * @Title: tohandle
    * @Description: 待处理的订单，状态为1审核通过。店铺员工只能查看自己店铺的订单
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/20 12:53
    * @Parameters: [query, request]
    * @Return cn.raths.basic.utils.PageList<cn.raths.pet.domain.SearchMasterMsg>
    */
    @PostMapping("/toHandle")
    public PageList<SearchMasterMsg> toHandle(@RequestBody SearchMasterMsgQuery query, HttpServletRequest request)
    {
        UserInfo loginUser = LoginContext.getLoginUser(request);
        Employee employee = employeeService.loadByLogininfoId(loginUser.getLogininfo().getId());

        // 状态为1的订单
        query.setState(1);
        query.setShopId(employee.getShopId());

        return searchMasterMsgService.queryList(query);
    }

    /**
    * @Title: pool
    * @Description: 查询寻主池的数据
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/20 12:59
    * @Parameters: [query, request]
    * @Return cn.raths.basic.utils.PageList<cn.raths.pet.domain.SearchMasterMsg>
    */
    @PostMapping("/pool")
    public PageList<SearchMasterMsg> pool(@RequestBody SearchMasterMsgQuery query, HttpServletRequest request)
    {
        UserInfo loginUser = LoginContext.getLoginUser(request);
        Employee employee = employeeService.loadByLogininfoId(loginUser.getLogininfo().getId());

        // 状态为零的所有订单
        query.setState(1);
        // shopId为空，在SQL语句中判断

        return searchMasterMsgService.queryList(query);
    }

    /**
    * @Title: finish
    * @Description: 已完成订单
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/20 13:00
    * @Parameters: [query, request]
    * @Return cn.raths.basic.utils.PageList<cn.raths.pet.domain.SearchMasterMsg>
    */
    @PostMapping("/finish")
    public PageList<SearchMasterMsg> finish(@RequestBody SearchMasterMsgQuery query, HttpServletRequest request)
    {
        UserInfo loginUser = LoginContext.getLoginUser(request);
        Employee employee = employeeService.loadByLogininfoId(loginUser.getLogininfo().getId());

        // 状态为2的所有订单
        query.setState(2);

        return searchMasterMsgService.queryList(query);
    }


    /**
    * @Title: accept
    * @Description: 接受订单
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/20 15:04
    * @Parameters: [handlerMsgDto]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @PostMapping("/accept")
    public AjaxResult accept(@RequestBody HandlerMsgDto handlerMsgDto)
    {
        try {
            searchMasterMsgService.accept(handlerMsgDto);
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
    @PostMapping("/reject/{msgId}")
    public AjaxResult reject(@PathVariable("msgId") Long msgId)
    {
        try {
            searchMasterMsgService.reject(msgId);
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
