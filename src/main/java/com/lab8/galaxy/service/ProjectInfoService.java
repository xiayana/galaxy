package com.lab8.galaxy.service;

import com.github.pagehelper.PageInfo;
import com.lab8.galaxy.entity.Dashboard;
import com.lab8.galaxy.entity.ProjectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (ProjectInfo)表服务接口
 *
 * @author xy
 * @since 2024-01-03 16:19:22
 */
public interface ProjectInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ProjectInfo queryById(Integer id);

    /**
     * 分页查询
     *
     * @param projectInfo 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<ProjectInfo> queryByPage(ProjectInfo projectInfo, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param projectInfo 实例对象
     * @return 实例对象
     */
    ProjectInfo insert(ProjectInfo projectInfo);

    /**
     * 修改数据
     *
     * @param projectInfo 实例对象
     * @return 实例对象
     */
    ProjectInfo update(ProjectInfo projectInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    PageInfo<ProjectInfo> queryAll(ProjectInfo projectInfo);

    Object launchById(Integer id, String address);

    Dashboard dashboard();

    PageInfo<ProjectInfo> queryAllOa(ProjectInfo projectInfo);
}
