package com.lab8.galaxy.service;

import com.lab8.galaxy.entity.Newtable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (Newtable)表服务接口
 *
 * @author xy
 * @since 2024-01-03 15:33:29
 */
public interface NewtableService {

    /**
     * 通过ID查询单条数据
     *
     * @param  主键
     * @return 实例对象
     */
    Newtable queryById(Integer id );

    /**
     * 分页查询
     *
     * @param newtable 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Newtable> queryByPage(Newtable newtable, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param newtable 实例对象
     * @return 实例对象
     */
    Newtable insert(Newtable newtable);

    /**
     * 修改数据
     *
     * @param newtable 实例对象
     * @return 实例对象
     */
    Newtable update(Newtable newtable);

    /**
     * 通过主键删除数据
     *
     * @param  主键
     * @return 是否成功
     */
    boolean deleteById(Integer id );

    void dbtest();

    void createProjectDetails();
}
