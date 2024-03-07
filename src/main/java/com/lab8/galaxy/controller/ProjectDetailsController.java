package com.lab8.galaxy.controller;

import com.lab8.galaxy.entity.ProjectDetails;
import com.lab8.galaxy.entity.ResultData;
import com.lab8.galaxy.service.ProjectDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (ProjectDetails)表控制层
 *
 * @author xy
 * @since 2024-01-04 18:18:24
 */
@Slf4j
@RestController
@RequestMapping("projectDetails")
public class ProjectDetailsController {
    /**
     * 服务对象
     */
    @Resource
    private ProjectDetailsService projectDetailsService;

    /**
     * 分页查询
     *
     * @param projectDetails 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<ProjectDetails>> queryByPage(ProjectDetails projectDetails, PageRequest pageRequest) {
        return ResponseEntity.ok(this.projectDetailsService.queryByPage(projectDetails, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResultData queryById(@PathVariable("id") Integer id) {
        ResultData resultData = new ResultData();
        resultData.setData(this.projectDetailsService.queryById(id));
        return resultData;
    }

    /**
     * 新增数据
     *
     * @param projectDetails 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResultData add(@RequestBody ProjectDetails projectDetails) {
        log.info("Request arrives projectDetails -add-");

        ResultData resultData = new ResultData();
        resultData.setData(this.projectDetailsService.insert(projectDetails));
        return resultData;
    }

    /**
     * 编辑数据
     *
     * @param projectDetails 实体
     * @return 编辑结果
     */
    @PostMapping("/edit")
    public ResultData edit(@RequestBody  ProjectDetails projectDetails) {
        log.info("Request arrives projectDetails -edit-");

        ResultData resultData = new ResultData();
        resultData.setData(this.projectDetailsService.update(projectDetails));
        return resultData;
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.projectDetailsService.deleteById(id));
    }

}

