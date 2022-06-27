package cn.raths.basic.service.impl;

import cn.raths.basic.mapper.BaseMapper;
import cn.raths.basic.query.BaseQuery;
import cn.raths.basic.service.IBaseService;
import cn.raths.basic.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class BaseServiceImpl<T> implements IBaseService<T> {

    @Autowired
    private BaseMapper<T> baseMapper;

    @Override
    public List<T> loadAll() {
        return baseMapper.loadAll();
    }

    @Override
    public T loadById(Long id) {
        return baseMapper.loadById(id);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        baseMapper.remove(id);
    }

    @Override
    @Transactional
    public void update(T t) {
        baseMapper.update(t);
    }

    @Override
    @Transactional
    public void save(T t) {
        baseMapper.save(t);
    }
    @Override
    public PageList<T> queryList(BaseQuery baseQuery) {
        List<T> rows = baseMapper.queryList(baseQuery);
        Integer total = baseMapper.queryCount(baseQuery);
        return new PageList<>(total,rows);
    }
    @Override
    @Transactional
    public void patchDel(Long[] ids) {
        baseMapper.patchDel(ids);
    }
}
