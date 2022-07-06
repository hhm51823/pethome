package cn.raths.user.mapper;

import cn.raths.user.domain.User;
import cn.raths.basic.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lynn
 * @since 2022-07-06
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User loadByPhone(String Phone);

    User loadByEamil(String email);
}
