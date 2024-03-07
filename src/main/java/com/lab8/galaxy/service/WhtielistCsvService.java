package com.lab8.galaxy.service;

import com.lab8.galaxy.entity.WhtielistCsv;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (WhtielistCsv)表服务接口
 *
 * @author xy
 * @since 2024-01-05 16:16:30
 */
public interface WhtielistCsvService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WhtielistCsv queryById(Integer id);

    /**
     * 分页查询
     *
     * @param whtielistCsv 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<WhtielistCsv> queryByPage(WhtielistCsv whtielistCsv, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param whtielistCsv 实例对象
     * @return 实例对象
     */
    WhtielistCsv insert(WhtielistCsv whtielistCsv);
    int insertBatch(List<WhtielistCsv> entities);
    /**
     * 修改数据
     *
     * @param whtielistCsv 实例对象
     * @return 实例对象
     */
    WhtielistCsv update(WhtielistCsv whtielistCsv);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    Integer updateByStatus( Integer pid, Integer status,Integer updateStatus);


    Integer deleteDataWithStatus3(Integer pid, int i);
}
