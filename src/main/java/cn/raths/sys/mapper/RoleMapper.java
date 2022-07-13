package cn.raths.sys.mapper;

import cn.raths.basic.utils.PageList;
import cn.raths.sys.domain.Role;
import cn.raths.basic.mapper.BaseMapper;
import cn.raths.sys.query.RoleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
public interface RoleMapper extends BaseMapper<Role> {

    void removeRoleMenu(Long roleId);

    void removeRolePermission(Long roleId);

    void saveRoleMenu(@Param("rId") Long id, @Param("mIds") List<Long> menus);

    void saveRolePermission(@Param("rId") Long id, @Param("pIds") List<Long> permissions);

    List<Role> loadByEmpId(Long eId);
}
