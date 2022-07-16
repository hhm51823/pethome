package cn.raths.pet.service;

import cn.raths.pet.domain.Pet;
import cn.raths.basic.service.IBaseService;
import cn.raths.pet.domain.PetDetail;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-16
 */
public interface IPetService extends IBaseService<Pet> {

    void onsale(List<Pet> pets);

    void offsale(List<Long> ids);

    PetDetail loaddetail(Long id);
}
