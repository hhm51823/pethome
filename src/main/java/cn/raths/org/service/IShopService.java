package cn.raths.org.service;

import cn.raths.basic.utils.PageList;
import cn.raths.org.domain.Shop;
import cn.raths.org.query.ShopQuery;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IShopService {
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
    //void save(Shop shop, MultipartFile multipartFile);

    // 高级查询
    PageList<Shop> queryList(ShopQuery shopQuery);

    // 批量删除
    void patchDel(Long[] ids);
}
