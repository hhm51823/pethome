package cn.raths.sys.service;

import cn.raths.basic.utils.PageList;
import cn.raths.sys.domain.Role;
import cn.raths.basic.service.IBaseService;
import cn.raths.sys.query.RoleQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-11
 */
public interface IRoleService extends IBaseService<Role> {

    List<Role> loadByEmpId(Long id);
}
