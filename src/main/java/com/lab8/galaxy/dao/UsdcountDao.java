package com.lab8.galaxy.dao;

import com.lab8.galaxy.entity.Usdcount;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
/**
 * (Usdcount)表数据库访问层
 *
 * @author xy
 * @since 2024-03-17 14:44:23
 */
@Mapper
public interface UsdcountDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Usdcount queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param usdcount 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Usdcount> queryAllByLimit(Usdcount usdcount, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param usdcount 查询条件
     * @return 总行数
     */
    long count(Usdcount usdcount);

    /**
     * 新增数据
     *
     * @param usdcount 实例对象
     * @return 影响行数
     */
    int insert(Usdcount usdcount);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Usdcount> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Usdcount> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Usdcount> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Usdcount> entities);

    /**
     * 修改数据
     *
     * @param usdcount 实例对象
     * @return 影响行数
     */
    int update(Usdcount usdcount);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    BigDecimal sumCount();

    BigDecimal sumCountA();
}

