package cn.raths.org.mapper;

import cn.raths.org.domain.ShopAuditLog;
import cn.raths.basic.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lynn
 * @since 2022-07-02
 */
@Mapper
public interface ShopAuditLogMapper extends BaseMapper<ShopAuditLog> {

    List<ShopAuditLog> getByShopId(Long shopId);
}
