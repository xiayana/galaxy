package com.lab8.galaxy.dao;

import com.lab8.galaxy.entity.Blockchain;
import com.lab8.galaxy.entity.Tokentable;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
/**
 * (Tokentable)表数据库访问层
 *
 * @author xy
 * @since 2024-03-17 16:30:04
 */
@Mapper
public interface TokentableDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Tokentable queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param tokentable 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Tokentable> queryAllByLimit(Tokentable tokentable, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param tokentable 查询条件
     * @return 总行数
     */
    long count(Tokentable tokentable);

    /**
     * 新增数据
     *
     * @param tokentable 实例对象
     * @return 影响行数
     */
    int insert(Tokentable tokentable);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Tokentable> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Tokentable> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Tokentable> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Tokentable> entities);

    /**
     * 修改数据
     *
     * @param tokentable 实例对象
     * @return 影响行数
     */
    int update(Tokentable tokentable);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<Tokentable> queryByAll(@Param("pid")Integer id);

    List<Tokentable> queryAll(Tokentable tokentable);

    List<Blockchain> queryVerify(Blockchain blockchain);
}

