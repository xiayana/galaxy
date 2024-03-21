package com.lab8.galaxy.service.impl;

import com.lab8.galaxy.dao.TokentableDao;
import com.lab8.galaxy.entity.Blockchain;
import com.lab8.galaxy.dao.BlockchainDao;
import com.lab8.galaxy.service.BlockchainService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Blockchain)表服务实现类
 *
 * @author xy
 * @since 2024-03-12 15:04:24
 */
@Service("blockchainService")
public class BlockchainServiceImpl implements BlockchainService {
    @Resource
    private BlockchainDao blockchainDao;
    @Resource
    private TokentableDao tokentableDao;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Blockchain queryById(Integer id) {
        return this.blockchainDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param blockchain 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Blockchain> queryByPage(Blockchain blockchain, PageRequest pageRequest) {
        long total = this.blockchainDao.count(blockchain);
        return new PageImpl<>(this.blockchainDao.queryAllByLimit(blockchain, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param blockchain 实例对象
     * @return 实例对象
     */
    @Override
    public Blockchain insert(Blockchain blockchain) {
        this.blockchainDao.insert(blockchain);
        return blockchain;
    }

    /**
     * 修改数据
     *
     * @param blockchain 实例对象
     * @return 实例对象
     */
    @Override
    public Blockchain update(Blockchain blockchain) {
        this.blockchainDao.update(blockchain);
        return this.queryById(blockchain.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.blockchainDao.deleteById(id) > 0;
    }

    @Override
    public List<Blockchain> queryAll(Blockchain blockchain) {
        List<Blockchain> list = blockchainDao.queryAlla(blockchain);
        for(Blockchain blockchain1 : list){
            blockchain1.setBitcoin(tokentableDao.queryByAll(blockchain1.getId()));
        }
        return list;
    }

    @Override
    public List<Blockchain> queryVerify(Blockchain blockchain) {
        return blockchainDao.queryVerify(blockchain);
    }
}
