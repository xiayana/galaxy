package com.lab8.galaxy.service;

import com.github.pagehelper.PageInfo;
import com.lab8.galaxy.entity.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * (Registration)表服务接口
 *
 * @author xy
 * @since 2024-03-11 17:01:23
 */
public interface RegistrationService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Registration queryById(Integer id);

    /**
     * 分页查询
     *
     * @param registration 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Registration> queryByPage(Registration registration, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param registration 实例对象
     * @return 实例对象
     */
    Registration insert(Registration registration);

    /**
     * 修改数据
     *
     * @param registration 实例对象
     * @return 实例对象
     */
    Registration update(Registration registration);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<Registration> queryByCode(Registration registration);

    Integer selectCount();

    BigDecimal selectCountAmount();

    PageInfo<Registration> queryAllPage(Registration registration);

    Integer queryByRank(Integer id);
}
