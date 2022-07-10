package cn.raths.user.service.impl;

import cn.raths.user.domain.Wxuser;
import cn.raths.user.service.IWxuserService;
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
 * @since 2022-07-10
 */
@Service
public class WxuserServiceImpl extends BaseServiceImpl<Wxuser> implements IWxuserService {

}
