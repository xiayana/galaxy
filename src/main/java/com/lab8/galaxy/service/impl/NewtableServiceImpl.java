package com.lab8.galaxy.service.impl;

import com.lab8.galaxy.entity.Newtable;
import com.lab8.galaxy.dao.NewtableDao;
import com.lab8.galaxy.service.NewtableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * (Newtable)表服务实现类
 *
 * @author xy
 * @since 2024-01-03 15:33:07
 */
@Service("newtableService")
public class NewtableServiceImpl implements NewtableService {
    @Autowired
    private NewtableDao newtableDao;

    /**
     * 通过ID查询单条数据
     *
     * @param
     * @return 实例对象
     */
    @Override
    public Newtable queryById( Integer id) {
        return this.newtableDao.queryById();
    }

    /**
     * 分页查询
     *
     * @param newtable 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Newtable> queryByPage(Newtable newtable, PageRequest pageRequest) {
        long total = this.newtableDao.count(newtable);
        return new PageImpl<>(this.newtableDao.queryAllByLimit(newtable, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param newtable 实例对象
     * @return 实例对象
     */
    @Override
    public Newtable insert(Newtable newtable) {
        this.newtableDao.insert(newtable);
        return newtable;
    }

    /**
     * 修改数据
     *
     * @param newtable 实例对象
     * @return 实例对象
     */
    @Override
    public Newtable update(Newtable newtable) {
        this.newtableDao.update(newtable);
        return newtable;
    }

    /**
     * 通过主键删除数据
     *
     * @param
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id ) {
        return this.newtableDao.deleteById() > 0;
    }

    @Override
    public void dbtest() {
        newtableDao.dbtest();
    }

    @Override
    public void createProjectDetails() {
        newtableDao.createProjectDetails();
    }
}
