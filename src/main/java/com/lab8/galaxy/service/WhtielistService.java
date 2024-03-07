package com.lab8.galaxy.service;

import com.lab8.galaxy.entity.Whtielist;
import com.lab8.galaxy.entity.WhtielistCsv;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (Whtielist)表服务接口
 *
 * @author xy
 * @since 2024-01-04 18:57:12
 */
public interface WhtielistService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Whtielist queryById(Integer id);

    /**
     * 分页查询
     *
     * @param whtielist 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Whtielist> queryByPage(Whtielist whtielist, PageRequest pageRequest);
    Whtielist launchById(Integer id, String address);
    /**
     * 新增数据
     *
     * @param whtielist 实例对象
     * @return 实例对象
     */
    Whtielist insert(Whtielist whtielist);

    /**
     * 修改数据
     *
     * @param whtielist 实例对象
     * @return 实例对象
     */
    Whtielist update(Whtielist whtielist);
    List<Whtielist> queryAllByPro(Whtielist whtielist);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    WhtielistCsv queryByWhitelist(String address, Integer id);
}
