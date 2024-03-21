package com.lab8.galaxy.service;

import com.lab8.galaxy.entity.Registrationmoment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (Registrationmoment)表服务接口
 *
 * @author xy
 * @since 2024-03-17 14:10:36
 */
public interface RegistrationmomentService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Registrationmoment queryById(Integer id);

    /**
     * 分页查询
     *
     * @param registrationmoment 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Registrationmoment> queryByPage(Registrationmoment registrationmoment, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param registrationmoment 实例对象
     * @return 实例对象
     */
    Registrationmoment insert(Registrationmoment registrationmoment);

    /**
     * 修改数据
     *
     * @param registrationmoment 实例对象
     * @return 实例对象
     */
    Registrationmoment update(Registrationmoment registrationmoment);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
