package cn.raths.sys.service.impl;

import cn.raths.sys.domain.Role;
import cn.raths.sys.service.IRoleService;
import cn.raths.basic.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

}
