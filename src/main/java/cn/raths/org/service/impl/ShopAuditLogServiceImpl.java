package cn.raths.org.service.impl;

import cn.raths.org.domain.ShopAuditLog;
import cn.raths.org.mapper.ShopAuditLogMapper;
import cn.raths.org.service.IShopAuditLogService;
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
 * @since 2022-07-02
 */
@Service
public class ShopAuditLogServiceImpl extends BaseServiceImpl<ShopAuditLog> implements IShopAuditLogService {

    @Autowired
    private ShopAuditLogMapper shopAuditLogMapper;

    @Override
    public List<ShopAuditLog> getByShopId(Long shopId) {
        return shopAuditLogMapper.getByShopId(shopId);
    }
}
