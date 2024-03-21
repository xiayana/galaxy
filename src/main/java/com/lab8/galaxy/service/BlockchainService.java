package com.lab8.galaxy.service;

import com.lab8.galaxy.entity.Blockchain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (Blockchain)表服务接口
 *
 * @author xy
 * @since 2024-03-12 15:04:24
 */
public interface BlockchainService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Blockchain queryById(Integer id);

    /**
     * 分页查询
     *
     * @param blockchain 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Blockchain> queryByPage(Blockchain blockchain, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param blockchain 实例对象
     * @return 实例对象
     */
    Blockchain insert(Blockchain blockchain);

    /**
     * 修改数据
     *
     * @param blockchain 实例对象
     * @return 实例对象
     */
    Blockchain update(Blockchain blockchain);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<Blockchain> queryAll(Blockchain blockchain);

    List<Blockchain> queryVerify(Blockchain blockchain);
}
