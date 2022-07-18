package cn.raths.pet.service;

import cn.raths.pet.domain.PetDetail;
import cn.raths.basic.service.IBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-16
 */
public interface IPetDetailService extends IBaseService<PetDetail> {

    PetDetail loadByPetId(Long id);
}
