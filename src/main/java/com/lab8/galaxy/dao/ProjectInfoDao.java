package com.lab8.galaxy.dao;

import com.lab8.galaxy.entity.ProjectInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
/**
 * (ProjectInfo)表数据库访问层
 *
 * @author xy
 * @since 2024-01-03 16:19:22
 */
@Mapper
public interface ProjectInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProjectInfo queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param projectInfo 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<ProjectInfo> queryAllByLimit(ProjectInfo projectInfo, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param projectInfo 查询条件
     * @return 总行数
     */
    long count(ProjectInfo projectInfo);

    /**
     * 新增数据
     *
     * @param projectInfo 实例对象
     * @return 影响行数
     */
    int insert(ProjectInfo projectInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProjectInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ProjectInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ProjectInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ProjectInfo> entities);

    /**
     * 修改数据
     *
     * @param projectInfo 实例对象
     * @return 影响行数
     */
    int update(ProjectInfo projectInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    List<ProjectInfo> queryAll(ProjectInfo projectInfo);

    ProjectInfo queryByIdAndPid(Integer id);

    List<ProjectInfo> queryAllOa(ProjectInfo projectInfo);

    Long countSb();
}
