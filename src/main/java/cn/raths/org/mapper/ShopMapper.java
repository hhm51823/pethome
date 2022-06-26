package cn.raths.org.mapper;

import cn.raths.org.domain.Shop;
import cn.raths.org.query.ShopQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopMapper {
    // 查询所有
    List<Shop> loadAll();

    // 根据id查询
    Shop loadById(Long id);

    // 根据id删除
    void remove(Long id);

    // 修改
    void update(Shop shop);

    // 添加
    void save(Shop shop);

    // 高级查询
    List<Shop> queryList(ShopQuery shopQuery);

    // 当前查询总条数
    Integer queryCount(ShopQuery shopQuery);

    // 根据id批量删除
    void patchDel(Long[] ids);
}
