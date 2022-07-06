package cn.raths.user.controller;

import cn.raths.basic.dto.EmailRegisterDto;
import cn.raths.basic.dto.RegisterDto;
import cn.raths.basic.exception.BusinessException;
import cn.raths.org.domain.ShopAuditLog;
import cn.raths.user.service.IUserService;
import cn.raths.user.domain.User;
import cn.raths.user.query.UserQuery;
import cn.raths.basic.utils.AjaxResult;
import cn.raths.basic.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    public IUserService userService;


    /**
     * 保存和修改公用的
     * @param user  传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody User user){
        try {
            if( user.getId()!=null)
                userService.update(user);
            else
                userService.save(user);
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
            userService.remove(id);
            return AjaxResult.getAjaxResult();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.error().setMessage("删除对象失败！"+e.getMessage());
        }
    }
	
    //获取用户
    @GetMapping("/{id}")
    public User get(@PathVariable("id")Long id)
    {
        return userService.loadById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping()
    public List<User> list(){

        return userService.loadAll();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping("/list")
    public PageList<User> json(@RequestBody UserQuery query)
    {
        return userService.queryList(query);
    }

    /**
    * @Title: registerPhone
    * @Description: 电话号码注册
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/6 16:22
    * @Parameters: [registerDto]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @PostMapping("/register/phone")
    public AjaxResult registerPhone(@RequestBody RegisterDto registerDto)
    {
        try {
            userService.registerPhone(registerDto);
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
    * @Title: registerEmail
    * @Description: 邮箱注册方法
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/6 16:24
    * @Parameters: [emailRegisterDto]
    * @Return cn.raths.basic.utils.AjaxResult
    */
    @PostMapping("/register/email")
    public AjaxResult registerEmail(@RequestBody EmailRegisterDto emailRegisterDto)
    {
        try {
            userService.registerEmail(emailRegisterDto);
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
