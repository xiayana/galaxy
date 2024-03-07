package com.lab8.galaxy.dao;

import com.lab8.galaxy.entity.Newtable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (Newtable)表数据库访问层
 *
 * @author xy
 * @since 2024-01-03 15:33:06
 */
@Mapper
public interface NewtableDao {

    /**
     * 通过ID查询单条数据
     *
     * @param  主键
     * @return 实例对象
     */
    Newtable queryById( );

    /**
     * 查询指定行数据
     *
     * @param newtable 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Newtable> queryAllByLimit(Newtable newtable, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param newtable 查询条件
     * @return 总行数
     */
    long count(Newtable newtable);

    /**
     * 新增数据
     *
     * @param newtable 实例对象
     * @return 影响行数
     */
    int insert(Newtable newtable);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Newtable> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Newtable> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Newtable> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Newtable> entities);

    /**
     * 修改数据
     *
     * @param newtable 实例对象
     * @return 影响行数
     */
    int update(Newtable newtable);

    /**
     * 通过主键删除数据
     *
     * @param  主键
     * @return 影响行数
     */
    int deleteById( );

    void dbtest();

    void createProjectDetails();
}

