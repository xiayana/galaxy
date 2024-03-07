package com.lab8.galaxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lab8.galaxy.dao.OrderlistDao;
import com.lab8.galaxy.dao.ProjectDetailsDao;
import com.lab8.galaxy.dao.WhtielistDao;
import com.lab8.galaxy.entity.*;
import com.lab8.galaxy.dao.ProjectInfoDao;
import com.lab8.galaxy.service.ProjectInfoService;
import com.lab8.galaxy.utils.CurrencyConverter;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * (ProjectInfo)表服务实现类
 *
 * @author xy
 * @since 2024-01-03 16:19:22
 */
@Service("projectInfoService")
public class ProjectInfoServiceImpl implements ProjectInfoService {
    @Resource
    private ProjectInfoDao projectInfoDao;
    @Resource
    private WhtielistDao wtielistDao;
    @Resource
    private ProjectDetailsDao projectDetailsDao;
    @Resource
    private OrderlistDao orderlistDao;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProjectInfo queryById(Integer id) {
        ProjectInfo projectInfo = projectInfoDao.queryById(id);
        if(projectInfo != null){
            List<Whtielist> whtielist = wtielistDao.queryPid(id,1);
            if(whtielist.size()>0){
                projectInfo.setWid(whtielist.get(0).getId());
            }
            List<Whtielist> whtielistPub = wtielistDao.queryPid(id,2);
            if(whtielistPub.size()>0){
                projectInfo.setPubid(whtielistPub.get(0).getId());
            }
            List<ProjectDetails> projectDetailsList = projectDetailsDao.queryPid(id);
            if(projectDetailsList.size()>0){
                projectInfo.setPdid(projectDetailsList.get(0).getId());
            }
        }
        return projectInfo;
    }

    /**
     * 分页查询
     *
     * @param projectInfo 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<ProjectInfo> queryByPage(ProjectInfo projectInfo, PageRequest pageRequest) {
        long total = this.projectInfoDao.count(projectInfo);
        return new PageImpl<>(this.projectInfoDao.queryAllByLimit(projectInfo, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param projectInfo 实例对象
     * @return 实例对象
     */
    @Override
    public ProjectInfo insert(ProjectInfo projectInfo) {
        this.projectInfoDao.insert(projectInfo);
        return projectInfo;
    }

    /**
     * 修改数据
     *
     * @param projectInfo 实例对象
     * @return 实例对象
     */
    @Override
    public ProjectInfo update(ProjectInfo projectInfo) {
        projectInfo.setUpdateAt(new Date());
        this.projectInfoDao.update(projectInfo);
        return this.queryById(projectInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.projectInfoDao.deleteById(id) > 0;
    }
    public String determineProjectStatus(String startTime, String endTime) {
        SimpleDateFormat utcFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date startDate = utcFormatter.parse(startTime);
            Date endDate = utcFormatter.parse(endTime);
            Date now = new Date();

            // 将当前时间转换为 UTC 时间
            SimpleDateFormat localFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            localFormatter.setTimeZone(TimeZone.getDefault());
            String nowAsString = localFormatter.format(now);
            now = utcFormatter.parse(nowAsString);

            if (now.before(startDate)) {
                return "Upcoming";
            } else if (now.after(endDate)) {
                return "Ended";
            } else {
                return "Active";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "Unknown"; // 或者根据需要处理异常
        }
    }

    @Override
    public PageInfo<ProjectInfo> queryAll(ProjectInfo projectInfo) {
        PageHelper.startPage(projectInfo.getPageNum(), projectInfo.getPageSize());
        List<ProjectInfo> list = projectInfoDao.queryAll(projectInfo);

        list.forEach(project -> {
            Whtielist query = new Whtielist();
            query.setPid(project.getId());
            List<Whtielist> wlist = wtielistDao.queryAll(query);

            wlist.forEach(whtielist -> {
                if (whtielist.getType() != null) {
                    if (whtielist.getStarttime() != null && whtielist.getEnttime() != null) {
                        String status = determineProjectStatus(whtielist.getStarttime(), whtielist.getEnttime());
                        if (whtielist.getType().equals(1)) {
                            project.setWhitelistCount(whtielist.getTokennumber().toString());
                            project.setWhitelistStage(status);
                        } else if (whtielist.getType().equals(2)) {
                            project.setPublicCount(whtielist.getTokennumber().toString());
                            project.setPublicStage(status);
                        }
                    }
                }
            });
        });

        return new PageInfo<>(list);
    }

    @Override
    public Object launchById(Integer id, String address) {
        ProjectInfo projectInfo = projectInfoDao.queryById(id);
        if(projectInfo != null){
            List<Whtielist> whtielist = wtielistDao.queryPid(id,1);
            if(whtielist.size()>0){
                projectInfo.setWid(whtielist.get(0).getId());
            }
            List<Whtielist> whtielistPub = wtielistDao.queryPid(id,2);
            if(whtielistPub.size()>0){
                projectInfo.setPubid(whtielistPub.get(0).getId());
            }
            List<ProjectDetails> projectDetailsList = projectDetailsDao.queryPid(id);
            if(projectDetailsList.size()>0){
                projectInfo.setPdid(projectDetailsList.get(0).getId());
            }
        }
/*        if(address !=null && !address.equals("")){
            Orderlist orderlist = new Orderlist();
            orderlist.setPid(id);
            orderlist.setFromaddr(address);
            List<Orderlist> list = orderlistDao.queryAll(orderlist);
            Integer singlePersonPurchased = 0;
            for (Orderlist orderlist1 : list){
                singlePersonPurchased += orderlist1.getBuyAmount();
            }
            projectInfo.setSinglePersonPurchased(singlePersonPurchased);
        }*/
        return projectInfo;
    }

    @Override
    public Dashboard dashboard() {
        Dashboard dashboard = new Dashboard();
        ProjectInfo projectInfo = new ProjectInfo();
        Long proCount = projectInfoDao.countSb();
        dashboard.setProjectCount(proCount);
        Long usersCount = orderlistDao.usersCount();
        dashboard.setUsersCount(usersCount);
        Long rCount = orderlistDao.raisedCount();
        double d = 0;
        if(rCount !=null){
            try {
                d = CurrencyConverter.convertCongToUSD(rCount);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        dashboard.setRaisedCount(d);
        return dashboard;
    }

    @Override
    public PageInfo<ProjectInfo> queryAllOa(ProjectInfo projectInfo) {
        PageHelper.startPage(projectInfo.getPageNum(), projectInfo.getPageSize());
        List<ProjectInfo> list = projectInfoDao.queryAllOa(projectInfo);

        list.forEach(project -> {
            Whtielist query = new Whtielist();
            query.setPid(project.getId());
            List<Whtielist> wlist = wtielistDao.queryAll(query);

            wlist.forEach(whtielist -> {
                if (whtielist.getType() != null) {
                    if (whtielist.getStarttime() != null && whtielist.getEnttime() != null) {
                        String status = determineProjectStatus(whtielist.getStarttime(), whtielist.getEnttime());
                        if (whtielist.getType().equals(1)) {
                            project.setWhitelistCount(whtielist.getTokennumber().toString());
                            project.setWhitelistStage(status);
                        } else if (whtielist.getType().equals(2)) {
                            project.setPublicCount(whtielist.getTokennumber().toString());
                            project.setPublicStage(status);
                        }
                    }
                }
            });
        });

        return new PageInfo<>(list);
    }
}
