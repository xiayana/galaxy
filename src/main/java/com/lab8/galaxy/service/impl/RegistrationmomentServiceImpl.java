package com.lab8.galaxy.service.impl;

import com.lab8.galaxy.entity.Registrationmoment;
import com.lab8.galaxy.dao.RegistrationmomentDao;
import com.lab8.galaxy.service.RegistrationmomentService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (Registrationmoment)表服务实现类
 *
 * @author xy
 * @since 2024-03-17 14:10:36
 */
@Service("registrationmomentService")
public class RegistrationmomentServiceImpl implements RegistrationmomentService {
    @Resource
    private RegistrationmomentDao registrationmomentDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Registrationmoment queryById(Integer id) {
        return this.registrationmomentDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param registrationmoment 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Registrationmoment> queryByPage(Registrationmoment registrationmoment, PageRequest pageRequest) {
        long total = this.registrationmomentDao.count(registrationmoment);
        return new PageImpl<>(this.registrationmomentDao.queryAllByLimit(registrationmoment, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param registrationmoment 实例对象
     * @return 实例对象
     */
    @Override
    public Registrationmoment insert(Registrationmoment registrationmoment) {
        this.registrationmomentDao.insert(registrationmoment);
        return registrationmoment;
    }

    /**
     * 修改数据
     *
     * @param registrationmoment 实例对象
     * @return 实例对象
     */
    @Override
    public Registrationmoment update(Registrationmoment registrationmoment) {
        this.registrationmomentDao.update(registrationmoment);
        return this.queryById(registrationmoment.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.registrationmomentDao.deleteById(id) > 0;
    }
}
