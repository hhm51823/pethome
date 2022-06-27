package cn.raths.basic.mapper;


import cn.raths.basic.query.BaseQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BaseMapper<T> {

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
    List<T> queryList(BaseQuery baseQuery);

    // 当前查询总条数
    Integer queryCount(BaseQuery baseQuery);

    // 根据id批量删除
    void patchDel(Long[] ids);
}
