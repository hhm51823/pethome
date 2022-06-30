package cn.raths.sys.mapper;

import cn.raths.basic.mapper.BaseMapper;
import cn.raths.sys.domain.TemdictionaryDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TemdictionaryDetailMapper extends BaseMapper<TemdictionaryDetail> {


    List<TemdictionaryDetail> loadByTypesId(Long types_id);

    void patchDelByTypesId(Long[] typesIds);

    void removeByTypesId(Long types_id);
}
