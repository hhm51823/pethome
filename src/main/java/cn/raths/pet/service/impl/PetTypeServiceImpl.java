package cn.raths.pet.service.impl;

import cn.raths.basic.service.impl.BaseServiceImpl;
import cn.raths.pet.domain.PetType;
import cn.raths.pet.mapper.PetTypeMapper;
import cn.raths.pet.service.IPetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-16
 */
@Service
public class PetTypeServiceImpl extends BaseServiceImpl<PetType> implements IPetTypeService {

    @Autowired
    private PetTypeMapper petTypeMapper;
    
    @Override
    public List<PetType> loadTree() {
        List<PetType> petTypes = petTypeMapper.loadAll();

        //  petType -> petType 给value赋值为当前对象
        Map<Long, PetType> petTypeMap = petTypes.stream().collect(Collectors.toMap(PetType::getId, petType -> petType));

        ArrayList<PetType> petTypeTree = new ArrayList<>();

        for (PetType petType : petTypes) {
            if(petType.getPid() == null){
                petTypeTree.add(petType);
            }else {
                petTypeMap.get(petType.getPid()).getChildren().add(petType);
            }
        }

        return petTypeTree;
    }
}
