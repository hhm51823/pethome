package cn.raths.pet.service.impl;

import cn.raths.basic.loginInfoMsg.LoginInfoMsg;
import cn.raths.basic.utils.BaiduAuditUtils;
import cn.raths.pet.domain.Pet;
import cn.raths.pet.domain.PetDetail;
import cn.raths.pet.domain.PetOnlineAuditLog;
import cn.raths.pet.mapper.PetDetailMapper;
import cn.raths.pet.mapper.PetMapper;
import cn.raths.pet.mapper.PetOnlineAuditLogMapper;
import cn.raths.pet.service.IPetService;
import cn.raths.basic.service.impl.BaseServiceImpl;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-16
 */
@Service
public class PetServiceImpl extends BaseServiceImpl<Pet> implements IPetService {

    @Autowired
    private PetMapper petMapper;

    @Autowired
    private PetOnlineAuditLogMapper petOnlineAuditLogMapper;

    @Autowired
    private PetDetailMapper petDetailMapper;


    @Override
    public void remove(Long id) {
        petMapper.remove(id);
        petDetailMapper.removeByPetId(id);
    }

    @Override
    public void update(Pet pet) {
        petMapper.update(pet);
        PetDetail detail = petDetailMapper.loadByPetId(pet.getId());
        petDetailMapper.update(detail);
    }

    @Override
    public void save(Pet pet) {
        petMapper.save(pet);
        PetDetail detail = pet.getDetail();
        detail.setPetId(pet.getId());
        petDetailMapper.save(detail);
    }

    @Override
    public void onsale(List<Pet> pets) {

        List<PetOnlineAuditLog> petOnlineAuditLogs = new ArrayList<>();

        // 保存合规且状态为下架的的id
        List<Long> ids = new ArrayList<>();

        for (Pet pet : pets){
            // 过滤掉已上架的宠物
            if(pet.getState() == 1){
                continue;
            }
            // 用来拼接不合规的信息
            StringBuffer logStr = new StringBuffer();
            // 百度AI审核名称和图片
            if(!BaiduAuditUtils.TextCensor(pet.getName())){
                logStr.append("<名称不合规>：" + pet.getName());
            }
            String[] resources = pet.getResources().split(",");
            for (String path : resources){
                if(!BaiduAuditUtils.petImgCensor(path)){
                    logStr.append("<图片不合规>：" + path);
                }
            }
            if(logStr.length() > 0){
                PetOnlineAuditLog temp = new PetOnlineAuditLog();
                temp.setPetId(pet.getId());
                temp.setNote(logStr.toString());
                petOnlineAuditLogs.add(temp);
                continue;
            }
            ids.add(pet.getId());
        }
        if(ids.size() > 0){
            petMapper.onsale(ids);
        }
        if (petOnlineAuditLogs.size() > 0){
            petOnlineAuditLogMapper.batchSave(petOnlineAuditLogs);
        }


    }

    /**
    * @Title: offsale
    * @Description: 批量下架
    * @Author: Lynn
    * @Version: 1.0
    * @Date:  2022/7/16 20:20
    * @Parameters: [ids]
    * @Return void
    */
    @Override
    public void offsale(List<Long> ids) {
        petMapper.offsale(ids);
    }

    @Override
    public PetDetail loaddetail(Long petId) {
        return petDetailMapper.loadByPetId(petId);
    }
}
