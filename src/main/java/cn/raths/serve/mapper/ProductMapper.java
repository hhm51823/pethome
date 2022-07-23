package cn.raths.serve.mapper;

import cn.raths.pet.domain.Pet;
import cn.raths.pet.domain.PetDetail;
import cn.raths.serve.domain.Product;
import cn.raths.basic.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lynn
 * @since 2022-07-21
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    void offsale(List<Long> ids);


    void onsale(List<Long> ids);
}
