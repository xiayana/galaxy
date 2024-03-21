package com.lab8.galaxy.service;

import com.lab8.galaxy.entity.Usdcount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

/**
 * (Usdcount)表服务接口
 *
 * @author xy
 * @since 2024-03-17 14:44:23
 */
public interface UsdcountService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Usdcount queryById(Integer id);

    /**
     * 分页查询
     *
     * @param usdcount 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Usdcount> queryByPage(Usdcount usdcount, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param usdcount 实例对象
     * @return 实例对象
     */
    Usdcount insert(Usdcount usdcount);

    /**
     * 修改数据
     *
     * @param usdcount 实例对象
     * @return 实例对象
     */
    Usdcount update(Usdcount usdcount);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    BigDecimal sumCount();
}
