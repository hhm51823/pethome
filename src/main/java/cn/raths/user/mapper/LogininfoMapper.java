package cn.raths.user.mapper;

import cn.raths.user.domain.Logininfo;
import cn.raths.basic.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lynn
 * @since 2022-07-06
 */
@Mapper
public interface LogininfoMapper extends BaseMapper<Logininfo> {

    Logininfo loadByAccount(@Param("account") String account, @Param("type") String type);

    Logininfo loadByPhone(@Param("phone") String phone,  @Param("type") Integer type);
}
