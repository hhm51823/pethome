package cn.raths.org.service;

import cn.raths.org.domain.Shop;
import cn.raths.basic.service.IBaseService;

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
}
