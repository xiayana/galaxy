package com.lab8.galaxy.controller;

import com.github.pagehelper.PageInfo;
import com.lab8.galaxy.common.ResponseCode;
import com.lab8.galaxy.entity.*;
import com.lab8.galaxy.service.OrdersService;
import com.lab8.galaxy.service.RegistrationService;
import com.lab8.galaxy.service.RegistrationmomentService;
import com.lab8.galaxy.service.UsdcountService;
import com.lab8.galaxy.utils.PowerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * (Registration)表控制层
 *
 * @author xy
 * @since 2024-03-11 17:01:22
 */
@Slf4j
@RestController
@RequestMapping("registration")
public class RegistrationController {
    /**
     * 服务对象
     */
    @Resource
    private RegistrationService registrationService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private RegistrationmomentService registrationmomentService;
    @Autowired
    private UsdcountService usdcountService;
    /**
     * 分页查询
     *
     * @param registration 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Registration>> queryByPage(Registration registration, PageRequest pageRequest) {
        return ResponseEntity.ok(this.registrationService.queryByPage(registration, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Registration> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.registrationService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param registration 实体
     * @return 新增结果
     */
    @PostMapping("addRegistration")
    public ResultData add(@RequestBody  Registration registration) {
        ResultData resultData = new ResultData();
        if(registration.getParentUserCode() == null && registration.getParentUserCode().equals("") ||
                (registration.getUserAddress() == null && registration.getUserAddress().equals("")) ||
                (registration.getTwitterAddress() ==null && registration.getTwitterAddress().equals("")) ||
                (registration.getTwitterScreenName() == null && registration.getTwitterScreenName().equals(""))){
            resultData.setCode(ResponseCode.FAIL_CODE_ONE);
            resultData.setMsg("入参不全");
        return resultData;
        }
        try{
            Registration registration1 = new Registration();
            registration1.setPersonalInvitationCode(registration.getParentUserCode());
            List<Registration> registrationList = registrationService.queryByCode(registration1);
            if(registrationList.size() == 0){
                resultData.setCode(ResponseCode.FAIL_CODE_ONE);
                return resultData;
            }
            resultData.setData(this.registrationService.insert(registration));
            return resultData;
        }catch (Exception e){
            log.error("e ：{}",e.getMessage());
            resultData.setCode(1);
        }
        return resultData;
    }
    @GetMapping("selectCount")
    public ResultData selectCount() {
        ResultData resultData = new ResultData();
        Integer ig = registrationService.selectCount();
        resultData.setData(ig);
        return resultData;
    }
    @GetMapping("usableNot")
    public ResultData usableNot(String address) {
        ResultData resultData = new ResultData();
        Registration registration1 = new Registration();
        registration1.setUserAddress(address);
        List<Registration> registrationList = registrationService.queryByCode(registration1);
        if(registrationList.size() == 0){
            resultData.setData(true);
        }else{
            resultData.setData(false);
        }
        return resultData;
    }
    @GetMapping("selectPage")
    public ResultData selectCount(int pageSize ,int pageNum ,String addr) {
        Registration registration = new Registration();
        registration.setPageNum(pageNum);
        registration.setPageSize(pageSize);
        ResultData resultData = new ResultData();
        PageInfo<Registration> list = registrationService.queryAllPage(registration);
        // 计算当前页的第一个排名
        int firstRankInPage = (pageNum - 1) * pageSize + 1;

        // 给每个条目设置排名
        List<Registration> registrations = list.getList();
        for (int i = 0; i < registrations.size(); i++) {
            registrations.get(i).setRank(firstRankInPage + i);
        }
        resultData.setData(list);
        return resultData;
    }
    @GetMapping("selectAddr")
    public ResultData selectCount(String addr) {
        ResultData resultData = new ResultData();
        Registration registration = new Registration();
        registration.setUserAddress(addr);
        List<Registration> list = registrationService.queryByCode(registration);

        if(!list.isEmpty()){
            Registration registration1 = new Registration();
            registration1.setParentUserCode(list.get(0).getPersonalInvitationCode());
            registration1.setIsActive(1);
            List<Registration> listCode = registrationService.queryByCode(registration1);
            for(Registration registration2 :list){
                if(!listCode.isEmpty()){
                    registration2.setListCode(listCode.size());
                }else{
                    registration2.setListCode(0);
                }
            }

           Integer ig =  registrationService.queryByRank(list.get(0).getId());
            list.get(0).setRank(ig+1);
            resultData.setData(list.get(0));
        }else{
            resultData.setCode(1);
            resultData.setMsg("未注册");
        }
        return resultData;
    }
    @GetMapping("numberPeople")
    public ResultData numberPeople(String code) {
        ResultData resultData = new ResultData();
        if (code == null || code.isEmpty()) {
            resultData.setCode(1);
            resultData.setMsg("参数为空");
            return  resultData;
        }
        Registration registration = new Registration();
        registration.setParentUserCode(code);
        registration.setIsActive(1);
        List<Registration> listCode = registrationService.queryByCode(registration);
        if(!listCode.isEmpty()){
            resultData.setData(listCode.size());
        }else{
            resultData.setData(0);
        }
        return resultData;
    }
    @GetMapping("selectCountAmount")
    public ResultData selectCountAmount() {
        ResultData resultData = new ResultData();
        BigDecimal bd = usdcountService.sumCount();
        resultData.setData(bd);
        return resultData;
    }
    /**
     * 编辑数据
     *
     * @param registration 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Registration> edit(Registration registration) {
        return ResponseEntity.ok(this.registrationService.update(registration));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.registrationService.deleteById(id));
    }
    @GetMapping("selectCode")
    public ResultData selectCOrder(String code) {
        log.info("Request arrives  -selectCode-");
        code = code.toLowerCase();
        ResultData resultData = new ResultData();
        if(code != null && !code.equals("")){
            Registration registration = new Registration();
            registration.setPersonalInvitationCode(code);
            List<Registration> registrationList = registrationService.queryByCode(registration);
            if(registrationList.size() == 0){
                resultData.setCode(ResponseCode.FAIL_CODE_ONE);
            return resultData;
            }
            resultData.setData(registrationList);
        }else{
            resultData.setCode(ResponseCode.PARAMETER_IS_NULL);
            resultData.setMsg("参数为空");
        }
        return resultData;
    }

    @GetMapping("test")
    public ResultData test() {
        log.info("Request arrives  -test-");
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
    @GetMapping("test2")
    public ResultData test2() {
        log.info("Request arrives  -test-");
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
            registrationmoment.setUsdtcount(reg.getUsdtcount());
            registrationmomentService.insert(registrationmoment);
        }
        return resultData;
    }
    @GetMapping("test3")
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
        resultData.setData(totalU); // 将最终的总和设置为响应数据
        return resultData;
    }

    public static long calculateDaysDifference(Date pastDate) {
        // 将 Date 转换为 LocalDate
        LocalDate pastLocalDate = pastDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentLocalDate = LocalDate.now();

        // 检查传入的日期是否晚于当前时间
        if (pastLocalDate.isAfter(currentLocalDate)) {
            throw new IllegalArgumentException("The date provided must be before the current date.");
        }

        // 计算两个日期之间的天数差异
        return ChronoUnit.DAYS.between(pastLocalDate, currentLocalDate);
    }
}

