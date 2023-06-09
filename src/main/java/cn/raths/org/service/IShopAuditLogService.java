package cn.raths.org.service;

import cn.raths.org.domain.ShopAuditLog;
import cn.raths.basic.service.IBaseService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-02
 */
public interface IShopAuditLogService extends IBaseService<ShopAuditLog> {

    List<ShopAuditLog> getByShopId(Long shopId);
}
