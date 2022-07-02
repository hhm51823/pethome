package cn.raths.org.service;

import cn.raths.org.domain.Shop;
import cn.raths.basic.service.IBaseService;
import cn.raths.org.domain.ShopAuditLog;

import javax.mail.MessagingException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-01
 */
public interface IShopService extends IBaseService<Shop> {

    void settlement(Shop shop);

    void reject(ShopAuditLog shopAuditLog) throws MessagingException;

    void pass(ShopAuditLog shopAuditLog) throws MessagingException;

    void prohibit(ShopAuditLog shopAuditLog) throws MessagingException;
}
