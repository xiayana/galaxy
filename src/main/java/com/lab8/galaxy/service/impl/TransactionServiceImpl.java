package com.lab8.galaxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lab8.galaxy.entity.Transaction;
import com.lab8.galaxy.dao.TransactionDao;
import com.lab8.galaxy.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * (Transaction)表服务实现类
 *
 * @author xy
 * @since 2024-01-08 16:48:41
 */
@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {
    @Resource
    private TransactionDao transactionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Transaction queryById(Integer id) {
        return this.transactionDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param transaction 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Transaction> queryByPage(Transaction transaction, PageRequest pageRequest) {
        long total = this.transactionDao.count(transaction);
        return new PageImpl<>(this.transactionDao.queryAllByLimit(transaction, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param transaction 实例对象
     * @return 实例对象
     */
    @Override
    public Transaction insert(Transaction transaction) {
        this.transactionDao.insert(transaction);
        return transaction;
    }

    /**
     * 修改数据
     *
     * @param transaction 实例对象
     * @return 实例对象
     */
    @Override
    public Transaction update(Transaction transaction) {
        this.transactionDao.update(transaction);
        return this.queryById(transaction.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.transactionDao.deleteById(id) > 0;
    }

    @Override
    public PageInfo<Transaction> queryAll(Transaction transaction) {
        PageHelper.startPage(transaction.getPageNum(), transaction.getPageSize());
        List<Transaction> list = transactionDao.queryAll(transaction);
        for (Transaction transaction1 : list){
            transaction1.setAmountFloat(convertToBillion(transaction1.getAmount()));
        }
        PageInfo<Transaction> personPageInfo = new PageInfo<>(list);
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
