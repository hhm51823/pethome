package cn.raths.sys.service;

import cn.raths.basic.service.IBaseService;
import cn.raths.sys.domain.TemdictionaryDetail;

import java.util.List;

public interface ITemdictionaryDetailService extends IBaseService<TemdictionaryDetail> {

    List<TemdictionaryDetail> loadByTypesId(Long types_id);

    void patchDelByTypesId(Long[] typesIds);

    void removeByTypesId(Long types_id);
}
