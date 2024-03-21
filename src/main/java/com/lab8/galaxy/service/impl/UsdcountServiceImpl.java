package com.lab8.galaxy.service.impl;

import com.lab8.galaxy.entity.Usdcount;
import com.lab8.galaxy.dao.UsdcountDao;
import com.lab8.galaxy.service.UsdcountService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * (Usdcount)表服务实现类
 *
 * @author xy
 * @since 2024-03-17 14:44:23
 */
@Service("usdcountService")
public class UsdcountServiceImpl implements UsdcountService {
    @Resource
    private UsdcountDao usdcountDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Usdcount queryById(Integer id) {
        return this.usdcountDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param usdcount 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Usdcount> queryByPage(Usdcount usdcount, PageRequest pageRequest) {
        long total = this.usdcountDao.count(usdcount);
        return new PageImpl<>(this.usdcountDao.queryAllByLimit(usdcount, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param usdcount 实例对象
     * @return 实例对象
     */
    @Override
    public Usdcount insert(Usdcount usdcount) {
        this.usdcountDao.insert(usdcount);
        return usdcount;
    }

    /**
     * 修改数据
     *
     * @param usdcount 实例对象
     * @return 实例对象
     */
    @Override
    public Usdcount update(Usdcount usdcount) {
        this.usdcountDao.update(usdcount);
        return this.queryById(usdcount.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.usdcountDao.deleteById(id) > 0;
    }

    @Override
    public BigDecimal sumCount() {
        return usdcountDao.sumCountA();
    }
}
