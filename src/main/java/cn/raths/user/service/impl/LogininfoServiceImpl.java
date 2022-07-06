package cn.raths.user.service.impl;

import cn.raths.user.domain.Logininfo;
import cn.raths.user.service.ILogininfoService;
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
 * @since 2022-07-06
 */
@Service
public class LogininfoServiceImpl extends BaseServiceImpl<Logininfo> implements ILogininfoService {

}
