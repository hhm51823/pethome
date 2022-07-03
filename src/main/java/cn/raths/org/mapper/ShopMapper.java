package cn.raths.org.mapper;

import cn.raths.org.domain.Shop;
import cn.raths.basic.mapper.BaseMapper;
import cn.raths.org.vo.ShopVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    void batchSave(List<Shop> shops);

    List<ShopVo> echarts();
}
