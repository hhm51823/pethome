package cn.raths.sys.service.impl;

import cn.raths.basic.service.impl.BaseServiceImpl;
import cn.raths.sys.domain.TemdictionaryDetail;
import cn.raths.sys.mapper.TemdictionaryDetailMapper;
import cn.raths.sys.service.ITemdictionaryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemdictionaryDetailServiceImpl extends BaseServiceImpl<TemdictionaryDetail> implements ITemdictionaryDetailService {

    @Autowired
    private TemdictionaryDetailMapper temdictionaryDetailMapper;
    @Override
    public List<TemdictionaryDetail> loadByTypesId(Long types_id) {
        return temdictionaryDetailMapper.loadByTypesId(types_id);
    }

    @Override
    public void patchDelByTypesId(Long[] typesIds) {
        temdictionaryDetailMapper.patchDelByTypesId(typesIds);
    }

    @Override
    public void removeByTypesId(Long types_id) {
        temdictionaryDetailMapper.removeByTypesId(types_id);
    }
}
