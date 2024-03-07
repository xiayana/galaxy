package com.lab8.galaxy.service.impl;

import com.lab8.galaxy.entity.WhtielistCsv;
import com.lab8.galaxy.dao.WhtielistCsvDao;
import com.lab8.galaxy.service.WhtielistCsvService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * (WhtielistCsv)表服务实现类
 *
 * @author xy
 * @since 2024-01-05 16:16:30
 */
@Service("whtielistCsvService")
public class WhtielistCsvServiceImpl implements WhtielistCsvService {
    @Resource
    private WhtielistCsvDao whtielistCsvDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WhtielistCsv queryById(Integer id) {
        return this.whtielistCsvDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param whtielistCsv 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<WhtielistCsv> queryByPage(WhtielistCsv whtielistCsv, PageRequest pageRequest) {
        long total = this.whtielistCsvDao.count(whtielistCsv);
        return new PageImpl<>(this.whtielistCsvDao.queryAllByLimit(whtielistCsv, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param whtielistCsv 实例对象
     * @return 实例对象
     */
    @Override
    public WhtielistCsv insert(WhtielistCsv whtielistCsv) {
        this.whtielistCsvDao.insert(whtielistCsv);
        return whtielistCsv;
    }

    @Override
    public int insertBatch(List<WhtielistCsv> entities) {
        return whtielistCsvDao.insertBatch(entities);
    }

    /**
     * 修改数据
     *
     * @param whtielistCsv 实例对象
     * @return 实例对象
     */
    @Override
    public WhtielistCsv update(WhtielistCsv whtielistCsv) {
        this.whtielistCsvDao.update(whtielistCsv);
        return this.queryById(whtielistCsv.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.whtielistCsvDao.deleteById(id) > 0;
    }

    @Override
    public Integer updateByStatus( Integer pid, Integer status,Integer updateStatus) {
        Integer ig =  whtielistCsvDao.updateByStatus(pid,status,updateStatus);
        return ig;
    }

    @Override
    public Integer deleteDataWithStatus3(Integer pid, int i) {
        return whtielistCsvDao.deleteDataWithStatus3(pid , i);
    }

}
