package cn.raths.org.mapper;

import cn.raths.org.domain.Shop;
import cn.raths.basic.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lynn
 * @since 2022-07-01
 */
@Mapper
public interface ShopMapper extends BaseMapper<Shop> {
    Shop loadByName(String name);
}
