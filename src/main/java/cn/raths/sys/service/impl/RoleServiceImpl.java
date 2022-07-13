package cn.raths.sys.service.impl;

import cn.raths.basic.utils.PageList;
import cn.raths.sys.domain.Role;
import cn.raths.sys.mapper.RoleMapper;
import cn.raths.sys.query.RoleQuery;
import cn.raths.sys.service.IRoleService;
import cn.raths.basic.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-11
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void remove(Long id) {
        roleMapper.remove(id);
        removeMenuPermission(id);
    }
    public void removeMenuPermission(Long id){
        roleMapper.removeRoleMenu(id);
        roleMapper.removeRolePermission(id);
    }
    public void saveMenuPermission(Role role){
        if (role.getMenus() != null){
            roleMapper.saveRoleMenu(role.getId(),role.getMenus());
        }
        if (role.getPermissions() != null){
            roleMapper.saveRolePermission(role.getId(),role.getPermissions());
        }
    }
    @Override
    public void update(Role role) {
        roleMapper.update(role);
        removeMenuPermission(role.getId());
        saveMenuPermission(role);
    }

    @Override
    public void save(Role role) {
        roleMapper.save(role);
        saveMenuPermission(role);
    }

    @Override
    public List<Role> loadByEmpId(Long id) {
        return roleMapper.loadByEmpId(id);
    }
}
