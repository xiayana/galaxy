package com.lab8.galaxy.controller;

import com.github.pagehelper.PageInfo;
import com.lab8.galaxy.entity.Dashboard;
import com.lab8.galaxy.entity.ProjectInfo;
import com.lab8.galaxy.entity.ResultData;
import com.lab8.galaxy.service.ProjectInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (ProjectInfo)表控制层
 *
 * @author xy
 * @since 2024-01-03 16:19:22
 */
@Slf4j
@RestController
@RequestMapping("projectInfo")
public class ProjectInfoController {
    /**
     * 服务对象
     */
    @Resource
    private ProjectInfoService projectInfoService;
    /**
     * 分页查询
     *
     * @param projectInfo 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping("queryByPage")
    public ResponseEntity<Page<ProjectInfo>> queryByPage(ProjectInfo projectInfo, PageRequest pageRequest) {
        return ResponseEntity.ok(this.projectInfoService.queryByPage(projectInfo, pageRequest));
    }
    @GetMapping("test")
    public ResultData test() {
        log.info("Request arrives projectInfo -test-");

        ResultData resultData = new ResultData();
        resultData.setData("每天都在思考一个问题，如何才能不劳而获？");
        return resultData;
    }
    @GetMapping("selectInfo")
    public ResultData testSelect(Integer pageNum,Integer pageSize , Integer id, String projectname ,String projecttype , Integer status) {
        log.info("Request arrives projectInfo -selectInfo-");

        ResultData resultData = new ResultData();
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setPageNum(pageNum);
        projectInfo.setPageSize(pageSize);
        if(id !=null){
            projectInfo.setId(id);
        }
        if(projectname !=null){
            projectInfo.setProjectname(projectname);
        }
        if(projecttype !=null){
            projectInfo.setProjecttype(projecttype);
        }
        if(status !=null){
            projectInfo.setStatus(status);
        }

        PageInfo<ProjectInfo> list = projectInfoService.queryAll(projectInfo);
        resultData.setData(list);
        return resultData;
    }
    @GetMapping("selectOaInfo")
    public ResultData selectOaInfo(Integer pageNum,Integer pageSize , Integer id, String projectname ,String projecttype , Integer status) {
        log.info("Request arrives projectInfo -selectOaInfo-");

        ResultData resultData = new ResultData();
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setPageNum(pageNum);
        projectInfo.setPageSize(pageSize);
        if(id !=null){
            projectInfo.setId(id);
        }
        if(projectname !=null){
            projectInfo.setProjectname(projectname);
        }
        if(projecttype !=null){
            projectInfo.setProjecttype(projecttype);
        }
        if(status !=null){
            projectInfo.setStatus(status);
        }

        PageInfo<ProjectInfo> list = projectInfoService.queryAllOa(projectInfo);
        resultData.setData(list);
        return resultData;
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
        resultData.setData(this.projectInfoService.queryById(id));
        return resultData;
    }
    @GetMapping("launchById")
    public ResultData launchById(Integer id,String address) {
        log.info("Request arrives projectInfo -launchById-");

        ResultData resultData = new ResultData();
        resultData.setData(this.projectInfoService.launchById(id,address));
        return resultData;
    }
    @GetMapping("dashboard")
    public ResultData dashboard() {
        log.info("Request arrives projectInfo -dashboard-");

        ResultData resultData = new ResultData();
        Dashboard dashboardResult =projectInfoService.dashboard();
        resultData.setData(dashboardResult);
        return resultData;
    }
    /**
     * 新增数据
     *
     * @param projectInfo 实体
     * @return 新增结果
     */
    @PostMapping("/bitMatchSave")
    public ResultData add(@RequestBody  ProjectInfo projectInfo) {
        log.info("Request arrives projectInfo -bitMatchSave-");

        ResultData resultData = new ResultData();
        projectInfo.setStatus(2);

        resultData.setData(this.projectInfoService.insert(projectInfo));
        return resultData;
    }
/*    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("projecttype") String projecttype, @RequestParam("projectname")  String projectname, @RequestParam("projectdescription") String projectdescription,
            @RequestParam("website") String website, @RequestParam("discord") String discord, @RequestParam("twitter") String twitter,
            @RequestParam("gitbook") String gitbook, @RequestParam("telegram") String telegram, @RequestParam("github") String github,
            @RequestParam("projectlogo") MultipartFile projectlogo, @RequestParam("projecthead") MultipartFile projecthead, @RequestParam("projectnft") MultipartFile projectnft) {

        try {
            ProjectInfo projectInfo = new ProjectInfo();
            projectInfo.setProjecttype(projecttype);
            projectInfo.setProjectname(projectname);
            projectInfo.setProjectdescription(projectdescription);
            projectInfo.setWebsite(website);
            projectInfo.setDiscord(discord);
            projectInfo.setTwitter(twitter);
            projectInfo.setGithub(github);
            projectInfo.setGitbook(gitbook);
            projectInfo.setTelegram(telegram);
           String projectlogoSuffix = FileUtil.getLastPartAfterDot(projectlogo.getOriginalFilename());
            String projectlogoPath = FileUtil.saveFile(FileUtil.getNewUUID(),projectlogo,"D:\\8lab\\oaTest",projectlogoSuffix);
            projectInfo.setProjectlogo(projectlogoPath);
            String projectheadSuffix = FileUtil.getLastPartAfterDot(projecthead.getOriginalFilename());
            String projectheadPath = FileUtil.saveFile(FileUtil.getNewUUID(),projecthead,"D:\\8lab\\oaTest",projectheadSuffix);
            projectInfo.setProjecthead(projectheadPath);
            if(projecttype.equals("2")){
                String projectnftSuffix = FileUtil.getLastPartAfterDot(projectnft.getOriginalFilename());
                String projectnftPath = FileUtil.saveFile(FileUtil.getNewUUID(),projectnft,"D:\\8lab\\oaTest",projectnftSuffix);
                projectInfo.setProjectnft(projectnftPath);
            }
            projectInfoService.insert(projectInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Files uploaded successfully";
    }*/
    /**
     * 编辑数据
     *
     * @param projectInfo 实体
     * @return 编辑结果
     */
    @PostMapping("/editProjectInfo")
    public ResultData edit(@RequestBody ProjectInfo projectInfo) {
        log.info("Request arrives projectInfo -editProjectInfo-");

        ResultData resultData = new ResultData();
        resultData.setData(this.projectInfoService.update(projectInfo));
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
        return ResponseEntity.ok(this.projectInfoService.deleteById(id));
    }

}

