package cn.raths.pet.mapper;

import cn.raths.pet.domain.Pet;
import cn.raths.basic.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lynn
 * @since 2022-07-16
 */
@Mapper
public interface PetMapper extends BaseMapper<Pet> {

    void onsale(List<Long> ids);

    void offsale(List<Long> ids);
}
