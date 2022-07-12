package cn.raths.sys.service;

import cn.raths.sys.domain.Menu;
import cn.raths.basic.service.IBaseService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-11
 */
public interface IMenuService extends IBaseService<Menu> {

    List<Menu> menuTree();
}
