package com.lab8.galaxy.service;

import com.lab8.galaxy.entity.Tokentable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (Tokentable)表服务接口
 *
 * @author xy
 * @since 2024-03-17 17:22:53
 */
public interface TokentableService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Tokentable queryById(Integer id);

    /**
     * 分页查询
     *
     * @param tokentable 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Tokentable> queryByPage(Tokentable tokentable, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param tokentable 实例对象
     * @return 实例对象
     */
    Tokentable insert(Tokentable tokentable);

    /**
     * 修改数据
     *
     * @param tokentable 实例对象
     * @return 实例对象
     */
    Tokentable update(Tokentable tokentable);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<Tokentable> queryAll(Tokentable tokentable);
}
