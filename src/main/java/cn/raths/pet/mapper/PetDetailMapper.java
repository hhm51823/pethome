package cn.raths.pet.mapper;

import cn.raths.pet.domain.PetDetail;
import cn.raths.basic.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lynn
 * @since 2022-07-16
 */
@Mapper
public interface PetDetailMapper extends BaseMapper<PetDetail> {

    PetDetail loadByPetId(Long petId);

    void removeByPetId(Long petId);
}
