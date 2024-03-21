package com.lab8.galaxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lab8.galaxy.entity.Orderlist;
import com.lab8.galaxy.dao.OrderlistDao;
import com.lab8.galaxy.service.OrderlistService;
import com.lab8.galaxy.utils.CurrencyConverter;
import com.lab8.galaxy.utils.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * (Orderlist)表服务实现类
 *
 * @author xy
 * @since 2024-01-06 14:52:10
 */
@Service("orderlistService")
public class OrderlistServiceImpl implements OrderlistService {
    @Resource
    private OrderlistDao orderlistDao;

    private static final long SATOSHIS_PER_BITCOIN = 100_000_000L;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Orderlist queryById(Integer id) {
        return this.orderlistDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param orderlist 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Orderlist> queryByPage(Orderlist orderlist, PageRequest pageRequest) {
        long total = this.orderlistDao.count(orderlist);
        return new PageImpl<>(this.orderlistDao.queryAllByLimit(orderlist, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param orderlist 实例对象
     * @return 实例对象
     */
    @Override
    public Orderlist insert(Orderlist orderlist) {
        if(orderlist.getStatus() ==null || orderlist.getStatus().isEmpty()){
            orderlist.setStatus("pending");
        }
        orderlist.setOrderid(FileUtil.getNewUUID());
        orderlist.setHasIncribled("uninscribed");
        this.orderlistDao.insert(orderlist);
        return orderlist;
    }

    /**
     * 修改数据
     *
     * @param orderlist 实例对象
     * @return 实例对象
     */
    @Override
    public Orderlist update(Orderlist orderlist) {
        this.orderlistDao.update(orderlist);
        return this.queryById(orderlist.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.orderlistDao.deleteById(id) > 0;
    }

    @Override
    public PageInfo<Orderlist> queryAll(Orderlist orderlist) {
        if(orderlist.getPageNum() == null ){
            orderlist.setPageNum(1);
        }
        if(orderlist.getPageSize() == null ){
            orderlist.setPageSize(10);
        }
        PageHelper.startPage(orderlist.getPageNum(), orderlist.getPageSize());
        List<Orderlist> list = orderlistDao.queryAll(orderlist);

        PageInfo<Orderlist> personPageInfo = new PageInfo<>(list);
        return personPageInfo;
    }

    @Override
    public List<Orderlist> queryAllByPro(Orderlist orderlistQuery) {

        return  orderlistDao.queryAll(orderlistQuery);
    }

    @Override
    public String selectFaddress(Integer pid) {
        return null;
    }


    @Override
    public Long totalPersonPurchased(Integer pid, String stage) {
        return orderlistDao.totalPersonPurchased(pid, stage);
    }

    @Override
    public long queryAmountRepeat(String addr, Integer pid, String stage) {
        return orderlistDao.queryAmountRepeat(addr,pid,stage);
    }

    @Override
    public PageInfo<Orderlist> queryAllOa(Orderlist orderlist) throws IOException, InterruptedException {
        if(orderlist.getPageNum() == null ){
            orderlist.setPageNum(1);
        }
        if(orderlist.getPageSize() == null ){
            orderlist.setPageSize(10);
        }
        PageHelper.startPage(orderlist.getPageNum(), orderlist.getPageSize());
        List<Orderlist> list = orderlistDao.queryAllOa(orderlist);

        PageInfo<Orderlist> personPageInfo = new PageInfo<>(list);
        return personPageInfo;
    }

    public String convertToBillion(Long value) {
        if (value != null) {
            BigDecimal result = new BigDecimal(value).divide(new BigDecimal("100000000"));
            return result.toPlainString(); // 返回非科学计数法的字符串表示
        }
        return "0";
    }

}
