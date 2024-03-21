package com.lab8.galaxy.controller;

import com.lab8.galaxy.entity.*;
import com.lab8.galaxy.service.OrdersService;
import com.lab8.galaxy.service.RegistrationService;
import com.lab8.galaxy.service.RegistrationmomentService;
import com.lab8.galaxy.service.UsdcountService;
import com.lab8.galaxy.utils.PowerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static com.lab8.galaxy.controller.RegistrationController.calculateDaysDifference;

@Slf4j
@Component
public class TaskMain {


    @Resource
    private RegistrationService registrationService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private RegistrationmomentService registrationmomentService;
    @Autowired
    private UsdcountService usdcountService;

/*
    @Scheduled(cron = "0 0 * * * ?")//整点执行
    public void seleniumCrawlerNews() {
        log.info("Running task...seleniumCrawlerNews");
        newsService.seleniumCrawlerNews();
    }
    @Scheduled(cron = "0 20 * * * ?")//顺延20分执行
    public void seleniumCrawlerWallStreet() {
        log.info("Running task...httpGetCrawler");
        newsService.httpGetCrawler();
    }
    @Scheduled(cron = "0 45 * * * ?")// 45分执行
    public void seleniumCompensate() {
        log.info("Running task...seleniumCompensate");
        newsService.compensate();
    }*/
@Scheduled(cron = "0 58 23 * * ?")
public void seleniumTask() {
    log.info("Running task...seleniumTask");
    // 这里是你要定期执行的任务 6665.6004  1410.0001  1570.0101
    test1();
    System.out.println("Running task...1");
    test2();
    System.out.println("Running task...2");
    test3();
    System.out.println("Running task...3");
}

  /*  @Scheduled(fixedRate = 60000)  // 参数是毫秒，3600000毫秒等于1小时
    public void test() {
        // 这里是你要定期执行的任务
        test1();
        System.out.println("Running task...1");
        test2();
        System.out.println("Running task...2");
        test3();
        System.out.println("Running task...3");
    }*/
    public ResultData test1() {
        log.info("Request arrives  -test1-");
        ResultData resultData = new ResultData();
        Registration registration = new Registration();
        List<Registration> registrationList = registrationService.queryByCode(registration);
        for(Registration reg : registrationList){
            BigDecimal ethU = reg.getEthcount() == null ? BigDecimal.ZERO : reg.getEthcount().multiply(new BigDecimal("4000"));
            BigDecimal usdcU = reg.getUsdccount() == null ? BigDecimal.ZERO : reg.getUsdccount().multiply(new BigDecimal("1"));
            BigDecimal triasU = reg.getTriascount() == null ? BigDecimal.ZERO : reg.getTriascount().multiply(new BigDecimal("15"));
            BigDecimal usdtU = reg.getUsdtcount() == null ? BigDecimal.ZERO : reg.getUsdtcount().multiply(new BigDecimal("1"));


            BigDecimal totalU = ethU.add(usdcU).add(triasU).add(usdtU);
            BigDecimal bg = PowerUtils.calculateScore(totalU);
            int intValue = 123; // 示例int值
            BigDecimal bigDecimalValue = new BigDecimal(intValue);
            Orders orders =  ordersService.queryTimeByAddress(reg.getUserAddress());
            int i1 = 0;
            int i2 = 0;
            if(orders !=null ){
                long l = calculateDaysDifference(orders.getInsertTime());
                i1 = PowerUtils.calculateTimeScore((int) l);
            }
            Registration registration1 = new Registration();
            registration1.setParentUserCode(reg.getPersonalInvitationCode());
            registration1.setIsActive(1);
            List<Registration> listCode = registrationService.queryByCode(registration1);
            if(!listCode.isEmpty()){
                i2 = PowerUtils.calculateInvitationScore(listCode.size());
            }
            BigDecimal b1 = new BigDecimal(i1);
            BigDecimal b2 = new BigDecimal(i2);
            BigDecimal bCount = bg.add(b1).add(b2);
            long longValue = bCount.longValue(); // 转换为long
            reg.setPersonalScore(longValue+10);
            registrationService.update(reg);
        }
        return resultData;
    }
    public ResultData test2() {
        log.info("Request arrives  -test2-");
        ResultData resultData = new ResultData();
        Registration registration = new Registration();
        registration.setIsActive(1);
        List<Registration> registrationList = registrationService.queryByCode(registration);
        for(Registration reg : registrationList) {
            Registrationmoment registrationmoment = new Registrationmoment();
            registrationmoment.setIsActive(reg.getIsActive());
            registrationmoment.setUsdccount(reg.getUsdccount());
            registrationmoment.setTriascount(reg.getTriascount());
            registrationmoment.setEthcount(reg.getEthcount());
            registrationmoment.setPersonalScore(reg.getPersonalScore());
            registrationmoment.setUpdatedAt(reg.getCreatedAt());
            registrationmoment.setUserAddress(reg.getUserAddress());
            registrationmomentService.insert(registrationmoment);
        }
        return resultData;
    }
    public ResultData test3() {
        log.info("Request arrives - test3 -");
        ResultData resultData = new ResultData();
        Registration registration = new Registration();
        List<Registration> registrationList = registrationService.queryByCode(registration);
        BigDecimal totalU = BigDecimal.ZERO; // 初始化总数
        for(Registration reg : registrationList) {
            BigDecimal ethU = reg.getEthcount() == null ? BigDecimal.ZERO : reg.getEthcount().multiply(new BigDecimal("4000"));
            BigDecimal usdcU = reg.getUsdccount() == null ? BigDecimal.ZERO : reg.getUsdccount().multiply(new BigDecimal("1"));
            BigDecimal triasU = reg.getTriascount() == null ? BigDecimal.ZERO : reg.getTriascount().multiply(new BigDecimal("15"));
            BigDecimal usdtU = reg.getUsdtcount() == null ? BigDecimal.ZERO : reg.getUsdtcount().multiply(new BigDecimal("1"));

            // 为每个用户计算总和
            BigDecimal userTotal = ethU.add(usdcU).add(triasU).add(usdtU);
            // 累加到所有用户的总和
            totalU = totalU.add(userTotal);
        }
        Usdcount usdcount = new Usdcount();
        usdcount.setUsdcountNumber(totalU);
        usdcountService.insert(usdcount);
        resultData.setData(totalU); //
        return resultData;
    }
}
