package com.lab8.galaxy.dao;

import com.lab8.galaxy.entity.Whtielist;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
/**
 * (Whtielist)表数据库访问层
 *
 * @author xy
 * @since 2024-01-04 18:57:11
 */
@Mapper
public interface WhtielistDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Whtielist queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param whtielist 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Whtielist> queryAllByLimit(Whtielist whtielist, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param whtielist 查询条件
     * @return 总行数
     */
    long count(Whtielist whtielist);

    /**
     * 新增数据
     *
     * @param whtielist 实例对象
     * @return 影响行数
     */
    int insert(Whtielist whtielist);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Whtielist> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Whtielist> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Whtielist> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Whtielist> entities);

    /**
     * 修改数据
     *
     * @param whtielist 实例对象
     * @return 影响行数
     */
    int update(Whtielist whtielist);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<Whtielist> queryPid(@Param("pid") Integer pid,@Param("type") int type);

    List<Whtielist> queryAll(Whtielist whtielist);
}

