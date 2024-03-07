package com.lab8.galaxy.service;

import com.github.pagehelper.PageInfo;
import com.lab8.galaxy.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (Transaction)表服务接口
 *
 * @author xy
 * @since 2024-01-08 16:48:41
 */
public interface TransactionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Transaction queryById(Integer id);

    /**
     * 分页查询
     *
     * @param transaction 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Transaction> queryByPage(Transaction transaction, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param transaction 实例对象
     * @return 实例对象
     */
    Transaction insert(Transaction transaction);

    /**
     * 修改数据
     *
     * @param transaction 实例对象
     * @return 实例对象
     */
    Transaction update(Transaction transaction);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    PageInfo<Transaction> queryAll(Transaction transaction);
}
