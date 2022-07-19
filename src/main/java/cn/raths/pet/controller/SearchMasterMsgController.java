package cn.raths.pet.controller;

import cn.raths.basic.dto.ShopRegisterDto;
import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.jwt.UserInfo;
import cn.raths.basic.utils.LoginContext;
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
}
