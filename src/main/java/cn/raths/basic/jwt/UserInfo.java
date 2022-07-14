package cn.raths.basic.jwt;

import cn.raths.sys.domain.Menu;
import cn.raths.user.domain.Logininfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserInfo {
    private Logininfo logininfo;

    private List<String> permissions = new ArrayList<>();

    private List<Menu> menus = new ArrayList<>();

}
