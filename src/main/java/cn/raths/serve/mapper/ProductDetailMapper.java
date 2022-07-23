package cn.raths.serve.mapper;

import cn.raths.serve.domain.ProductDetail;
import cn.raths.basic.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lynn
 * @since 2022-07-21
 */
@Mapper
public interface ProductDetailMapper extends BaseMapper<ProductDetail> {

    ProductDetail loadByProductId(Long productId);

    void removeByProductId(Long id);
}
