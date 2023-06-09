package cn.raths.sys.mapper;

import cn.raths.sys.domain.Permission;
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
public interface PermissionMapper extends BaseMapper<Permission> {

    Permission loadBySn(String sn);

    List<Permission> loadByRoleId(Long rId);

    List<String> loadPermissionByLogininfoId(Long logininfoId);
}
