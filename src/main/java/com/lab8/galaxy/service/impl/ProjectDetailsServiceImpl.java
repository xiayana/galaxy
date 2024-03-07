package com.lab8.galaxy.service.impl;

import com.lab8.galaxy.entity.ProjectDetails;
import com.lab8.galaxy.dao.ProjectDetailsDao;
import com.lab8.galaxy.service.ProjectDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (ProjectDetails)表服务实现类
 *
 * @author xy
 * @since 2024-01-04 18:18:24
 */
@Service("projectDetailsService")
public class ProjectDetailsServiceImpl implements ProjectDetailsService {
    @Resource
    private ProjectDetailsDao projectDetailsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProjectDetails queryById(Integer id) {
        return this.projectDetailsDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param projectDetails 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<ProjectDetails> queryByPage(ProjectDetails projectDetails, PageRequest pageRequest) {
        long total = this.projectDetailsDao.count(projectDetails);
        return new PageImpl<>(this.projectDetailsDao.queryAllByLimit(projectDetails, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param projectDetails 实例对象
     * @return 实例对象
     */
    @Override
    public ProjectDetails insert(ProjectDetails projectDetails) {
        this.projectDetailsDao.insert(projectDetails);
        return projectDetails;
    }

    /**
     * 修改数据
     *
     * @param projectDetails 实例对象
     * @return 实例对象
     */
    @Override
    public ProjectDetails update(ProjectDetails projectDetails) {
        this.projectDetailsDao.update(projectDetails);
        return this.queryById(projectDetails.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.projectDetailsDao.deleteById(id) > 0;
    }
}
