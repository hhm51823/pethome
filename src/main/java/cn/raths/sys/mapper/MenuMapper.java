package cn.raths.sys.mapper;

import cn.raths.sys.domain.Menu;
import cn.raths.basic.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lynn
 * @since 2022-07-11
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> loadMenuByLogininfoId(Long logininfoId);
}
