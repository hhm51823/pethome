package cn.raths.basic.service;

import cn.raths.basic.query.BaseQuery;
import cn.raths.basic.utils.PageList;

import java.util.List;

public interface IBaseService<T> {

    // 查询所有
    List<T> loadAll();

    // 根据id查询
    T loadById(Long id);

    // 根据id删除
    void remove(Long id);

    // 修改
    void update(T t);

    // 添加
    void save(T t);

    // 高级查询
    PageList<T> queryList(BaseQuery baseQuery);

    // 批量删除
    void patchDel(Long[] ids);
}
