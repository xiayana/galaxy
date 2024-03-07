package com.lab8.galaxy.service;

import com.github.pagehelper.PageInfo;
import com.lab8.galaxy.entity.Orderlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.List;

/**
 * (Orderlist)表服务接口
 *
 * @author xy
 * @since 2024-01-06 14:52:10
 */
public interface OrderlistService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Orderlist queryById(Integer id);

    /**
     * 分页查询
     *
     * @param orderlist 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Orderlist> queryByPage(Orderlist orderlist, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param orderlist 实例对象
     * @return 实例对象
     */
    Orderlist insert(Orderlist orderlist);

    /**
     * 修改数据
     *
     * @param orderlist 实例对象
     * @return 实例对象
     */
    Orderlist update(Orderlist orderlist);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    PageInfo<Orderlist>  queryAll(Orderlist orderlist);

    List<Orderlist> queryAllByPro(Orderlist orderlistQuery);

    String selectFaddress(Integer pid);

    Long totalPersonPurchased(Integer pid, String stage);

    long queryAmountRepeat(String addr, Integer pid, String stage);

    PageInfo<Orderlist> queryAllOa(Orderlist orderlist) throws IOException, InterruptedException;
}
