package cn.raths.pet.service.impl;

import cn.raths.pet.domain.PetOnlineAuditLog;
import cn.raths.pet.service.IPetOnlineAuditLogService;
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
 * @since 2022-07-16
 */
@Service
public class PetOnlineAuditLogServiceImpl extends BaseServiceImpl<PetOnlineAuditLog> implements IPetOnlineAuditLogService {

}
