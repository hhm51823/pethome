package cn.raths.sys.service.impl;

import cn.raths.basic.service.impl.BaseServiceImpl;
import cn.raths.sys.domain.Menu;
import cn.raths.sys.mapper.MenuMapper;
import cn.raths.sys.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-11
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> menuTree() {
        ArrayList<Menu> menuTree = new ArrayList<>();

        List<Menu> menus = menuMapper.loadAll();

        Map<Long, Menu> menuMap = menus.stream().collect(Collectors.toMap(Menu::getId, item -> item));
        for (Menu menu : menus){
            if (menu.getParentId() == null){
                menuTree.add(menu);
            }else {
                menuMap.get(menu.getParentId()).getChildren().add(menu);
            }
        }
        return menuTree;
    }

    @Override
    public void save(Menu menu) {

        menuMapper.save(menu);

        String dirPath = "";
        if(menu.getParent().getId() != null) {
            Menu menuTmp = menuMapper.loadById(menu.getParent().getId());
            if (menuTmp != null) {
                dirPath = menuTmp.getDirPath();
            }
        }
        dirPath += "/" + menu.getId();
        menu.setDirPath(dirPath);
        menuMapper.update(menu);
    }

    @Override
    public void update(Menu menu) {
        // 用来判断dirPath是否被修改
        Menu loadById = menuMapper.loadById(menu.getId());

        String dirPath = "";
        if(menu.getParent().getId() != null) {
            Menu menuTmp = menuMapper.loadById(menu.getParent().getId());
            if (menuTmp != null) {
                dirPath = menuTmp.getDirPath();
            }
        }
        dirPath += "/" + menu.getId();
        menu.setDirPath(dirPath);
        menuMapper.update(menu);

        // 修改了父级部门
        if(StringUtils.isEmpty(loadById.getDirPath())){
            return;
        }
        if(!loadById.getDirPath().equals(menu.getDirPath())){
            List<Menu> menus = menuMapper.loadAll();
            for (Menu menuTmp : menus) {
               /* if (dept.getParent_id() == loadById.getId()) {
                    dept.setDirPath(dirPath + "/" + dept.getId());
                    menuMapper.update(dept);
                }*/
                if(StringUtils.isEmpty(menuTmp.getDirPath())){
                    continue;
                }
                if (menuTmp.getDirPath().indexOf(loadById.getDirPath() + "/") >= 0){
                    String newDirPath = menuTmp.getDirPath().replace(loadById.getDirPath() + "/", dirPath + "/");
                    System.out.println(menuTmp.getDirPath());
                    menuTmp.setDirPath(newDirPath);
                    menuMapper.update(menuTmp);
                }
            }
        }
    }
}
