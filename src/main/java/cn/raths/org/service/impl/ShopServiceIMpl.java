package cn.raths.org.service.impl;

import cn.raths.basic.utils.PageList;
import cn.raths.org.domain.Shop;
import cn.raths.org.mapper.ShopMapper;
import cn.raths.org.query.ShopQuery;
import cn.raths.org.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ShopServiceIMpl implements IShopService {

    @Autowired
    private ShopMapper shopMapper;

    private final Path path = Paths.get("upload");

    @Override
    public List<Shop> loadAll() {
        return shopMapper.loadAll();
    }

    @Override
    public Shop loadById(Long id) {
        return shopMapper.loadById(id);
    }

    @Override
    public void remove(Long id) {
        shopMapper.remove(id);
    }

    @Override
    public void update(Shop shop){
        //Files.copy(multipartFile.getInputStream(),this.path.resolve(multipartFile.getOriginalFilename()));
        shopMapper.update(shop);
    }

    @Override
    public void save(Shop shop) {
        shopMapper.save(shop);
    }

    @Override
    public PageList<Shop> queryList(ShopQuery shopQuery) {
        List<Shop> rows = shopMapper.queryList(shopQuery);
        Integer total = shopMapper.queryCount(shopQuery);
        return new PageList<>(total,rows);
    }
    @Override
    public void patchDel(Long[] ids) {
        shopMapper.patchDel(ids);
    }
}
