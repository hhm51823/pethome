package cn.raths.pet.service.impl;

import cn.raths.pet.domain.PetDetail;
import cn.raths.pet.mapper.PetDetailMapper;
import cn.raths.pet.service.IPetDetailService;
import cn.raths.basic.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-16
 */
@Service
public class PetDetailServiceImpl extends BaseServiceImpl<PetDetail> implements IPetDetailService {

    @Autowired
    private PetDetailMapper petDetailMapper;

    @Override
    public PetDetail loadByPetId(Long petId) {
        return petDetailMapper.loadByPetId(petId);
    }
}
