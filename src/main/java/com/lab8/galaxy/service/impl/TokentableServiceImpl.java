package com.lab8.galaxy.service.impl;

import com.lab8.galaxy.entity.Tokentable;
import com.lab8.galaxy.dao.TokentableDao;
import com.lab8.galaxy.service.TokentableService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Tokentable)表服务实现类
 *
 * @author xy
 * @since 2024-03-17 17:22:53
 */
@Service("tokentableService")
public class TokentableServiceImpl implements TokentableService {
    @Resource
    private TokentableDao tokentableDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Tokentable queryById(Integer id) {
        return this.tokentableDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param tokentable 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Tokentable> queryByPage(Tokentable tokentable, PageRequest pageRequest) {
        long total = this.tokentableDao.count(tokentable);
        return new PageImpl<>(this.tokentableDao.queryAllByLimit(tokentable, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param tokentable 实例对象
     * @return 实例对象
     */
    @Override
    public Tokentable insert(Tokentable tokentable) {
        this.tokentableDao.insert(tokentable);
        return tokentable;
    }

    /**
     * 修改数据
     *
     * @param tokentable 实例对象
     * @return 实例对象
     */
    @Override
    public Tokentable update(Tokentable tokentable) {
        this.tokentableDao.update(tokentable);
        return this.queryById(tokentable.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tokentableDao.deleteById(id) > 0;
    }

    @Override
    public List<Tokentable> queryAll(Tokentable tokentable) {
        return tokentableDao.queryAll(tokentable);
    }
}
