package com.lab8.galaxy.service;

import com.lab8.galaxy.entity.ProjectDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (ProjectDetails)表服务接口
 *
 * @author xy
 * @since 2024-01-04 18:18:24
 */
public interface ProjectDetailsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProjectDetails queryById(Integer id);

    /**
     * 分页查询
     *
     * @param projectDetails 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<ProjectDetails> queryByPage(ProjectDetails projectDetails, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param projectDetails 实例对象
     * @return 实例对象
     */
    ProjectDetails insert(ProjectDetails projectDetails);

    /**
     * 修改数据
     *
     * @param projectDetails 实例对象
     * @return 实例对象
     */
    ProjectDetails update(ProjectDetails projectDetails);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
