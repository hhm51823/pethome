package cn.raths.basic.loginInfoMsg;

import cn.raths.org.domain.Employee;
import cn.raths.user.domain.User;

/**
* @Description: 登录后保存User或employee
* @Author: Lynn
* @Version: 1.0
* @Date:  2022/7/16 21:47
*/
public enum LoginInfoMsg {
    INSTANCE;
    private Employee employee;
    private User user;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}