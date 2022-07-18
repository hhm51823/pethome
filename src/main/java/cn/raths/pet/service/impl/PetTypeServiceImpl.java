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
    public void remove(Long id) {
        PetType loadById = petTypeMapper.loadById(id);
        List<PetType> petTypes = petTypeMapper.loadAll();
        for (PetType petType : petTypes) {
            if (petType.getPid() == loadById.getId()) {
                petType.setDirPath(petType.getDirPath().replace("/" + petType.getPid() + "/","/"));
                petType.setPid(loadById.getPid());

                petTypeMapper.update(petType);
            }
        }
        petTypeMapper.remove(id);
    }

    @Override
    public void update(PetType petType) {
        // 用来判断dirPath是否被修改
        PetType loadById = petTypeMapper.loadById(petType.getId());

        String dirPath = "";
        if(petType.getParent().getId() != null) {
            PetType petTypeTmp = petTypeMapper.loadById(petType.getParent().getId());
            if (petTypeTmp != null) {
                dirPath = petTypeTmp.getDirPath();
            }
        }
        dirPath += "/" + petType.getId();
        petType.setDirPath(dirPath);
        petTypeMapper.update(petType);

        // 修改了父级部门

        if(!loadById.getDirPath().equals(petType.getDirPath())){
            List<PetType> petTypes = petTypeMapper.loadAll();
            for (PetType petTypeTemp : petTypes) {
               /* if (petType.getParent_id() == loadById.getId()) {
                    petType.setDirPath(dirPath + "/" + petType.getId());
                    petTypeMapper.update(petType);
                }*/
                if (petTypeTemp.getDirPath().indexOf(loadById.getDirPath() + "/") >= 0){
                    String newDirPath = petTypeTemp.getDirPath().replace(loadById.getDirPath() + "/", dirPath + "/");
                    System.out.println(petTypeTemp.getDirPath());
                    petTypeTemp.setDirPath(newDirPath);
                    petTypeMapper.update(petTypeTemp);
                }
            }
        }
    }

    @Override
    public void save(PetType petType) {
        petTypeMapper.save(petType);

        String dirPath = "";
        if(petType.getParent().getId() != null) {
            PetType petTypeTmp = petTypeMapper.loadById(petType.getParent().getId());
            if (petTypeTmp != null) {
                dirPath = petTypeTmp.getDirPath();
            }
        }
        dirPath += "/" + petType.getId();
        petType.setDirPath(dirPath);
        petTypeMapper.update(petType);
    }

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
