package cn.raths.serve.service.impl;

import cn.raths.serve.domain.OrderProductLog;
import cn.raths.serve.service.IOrderProductLogService;
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
 * @since 2022-07-21
 */
@Service
public class OrderProductLogServiceImpl extends BaseServiceImpl<OrderProductLog> implements IOrderProductLogService {

}
