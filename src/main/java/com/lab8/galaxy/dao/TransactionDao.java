package com.lab8.galaxy.dao;

import com.lab8.galaxy.entity.Transaction;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
/**
 * (Transaction)表数据库访问层
 *
 * @author xy
 * @since 2024-01-08 16:48:41
 */
@Mapper
public interface TransactionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Transaction queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param transaction 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Transaction> queryAllByLimit(Transaction transaction, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param transaction 查询条件
     * @return 总行数
     */
    long count(Transaction transaction);

    /**
     * 新增数据
     *
     * @param transaction 实例对象
     * @return 影响行数
     */
    int insert(Transaction transaction);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Transaction> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Transaction> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Transaction> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Transaction> entities);

    /**
     * 修改数据
     *
     * @param transaction 实例对象
     * @return 影响行数
     */
    int update(Transaction transaction);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<Transaction> queryAll(Transaction transaction);
}

