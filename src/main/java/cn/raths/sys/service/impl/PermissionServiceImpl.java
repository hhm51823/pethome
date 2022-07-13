package cn.raths.sys.service.impl;

import cn.raths.basic.service.impl.BaseServiceImpl;
import cn.raths.sys.domain.Permission;
import cn.raths.sys.mapper.PermissionMapper;
import cn.raths.sys.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements IPermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    public List<Long> loadByRoleId(Long rId){
        List<Permission> permissionList = permissionMapper.loadByRoleId(rId);
        List<Long> permissionIds = permissionList.stream().map(Permission::getId).collect(Collectors.toList());
        return permissionIds;
    }
}
