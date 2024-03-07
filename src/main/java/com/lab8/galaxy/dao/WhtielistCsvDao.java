package com.lab8.galaxy.dao;

import com.lab8.galaxy.entity.WhtielistCsv;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
/**
 * (WhtielistCsv)表数据库访问层
 *
 * @author xy
 * @since 2024-01-05 16:16:30
 */
@Mapper
public interface WhtielistCsvDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WhtielistCsv queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param whtielistCsv 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<WhtielistCsv> queryAllByLimit(WhtielistCsv whtielistCsv, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param whtielistCsv 查询条件
     * @return 总行数
     */
    long count(WhtielistCsv whtielistCsv);

    /**
     * 新增数据
     *
     * @param whtielistCsv 实例对象
     * @return 影响行数
     */
    int insert(WhtielistCsv whtielistCsv);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<WhtielistCsv> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<WhtielistCsv> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<WhtielistCsv> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<WhtielistCsv> entities);

    /**
     * 修改数据
     *
     * @param whtielistCsv 实例对象
     * @return 影响行数
     */
    int update(WhtielistCsv whtielistCsv);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    Integer updateByStatus( @Param("pid") Integer pid, @Param("status") Integer status,@Param("updateStatus")  Integer updateStatus);

    Date queryByPid(@Param("pid") Integer pid);

    WhtielistCsv queryByWhitelist(@Param("addr") String addr, @Param("pid") Integer id);

    Long existByStatus(@Param("pid")  Integer pid, @Param("status") Integer i);

    Integer deleteDataWithStatus3(@Param("pid")  Integer pid,@Param("status")  int i);
}

