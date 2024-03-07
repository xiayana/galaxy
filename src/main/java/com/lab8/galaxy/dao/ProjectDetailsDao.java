package com.lab8.galaxy.dao;

import com.lab8.galaxy.entity.ProjectDetails;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
/**
 * (ProjectDetails)表数据库访问层
 *
 * @author xy
 * @since 2024-01-04 18:18:24
 */
@Mapper
public interface ProjectDetailsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProjectDetails queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param projectDetails 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<ProjectDetails> queryAllByLimit(ProjectDetails projectDetails, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param projectDetails 查询条件
     * @return 总行数
     */
    long count(ProjectDetails projectDetails);

    /**
     * 新增数据
     *
     * @param projectDetails 实例对象
     * @return 影响行数
     */
    int insert(ProjectDetails projectDetails);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProjectDetails> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ProjectDetails> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProjectDetails> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ProjectDetails> entities);

    /**
     * 修改数据
     *
     * @param projectDetails 实例对象
     * @return 影响行数
     */
    int update(ProjectDetails projectDetails);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<ProjectDetails> queryPid(@Param("pid")Integer pid);
}

