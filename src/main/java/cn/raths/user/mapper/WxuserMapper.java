package cn.raths.user.mapper;

import cn.raths.user.domain.Wxuser;
import cn.raths.basic.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lynn
 * @since 2022-07-10
 */
@Mapper
public interface WxuserMapper extends BaseMapper<Wxuser> {

    Wxuser loadByOpenId(String openId);
}
