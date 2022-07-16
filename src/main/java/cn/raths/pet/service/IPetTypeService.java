package cn.raths.pet.service;

import cn.raths.pet.domain.PetType;
import cn.raths.basic.service.IBaseService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-16
 */
public interface IPetTypeService extends IBaseService<PetType> {

    List<PetType> loadTree();
}
