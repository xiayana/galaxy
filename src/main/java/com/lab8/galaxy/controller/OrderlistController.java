package com.lab8.galaxy.controller;

import com.github.pagehelper.PageInfo;
import com.lab8.galaxy.common.ResponseCode;
import com.lab8.galaxy.entity.*;
import com.lab8.galaxy.service.OrderlistService;
import com.lab8.galaxy.service.WhtielistService;
import com.lab8.galaxy.utils.CSVExportUtility;
import com.lab8.galaxy.utils.CurrencyConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

/**
 * (Orderlist)表控制层
 *
 * @author xy
 * @since 2024-01-06 14:52:10
 */
@Slf4j
@RestController
@RequestMapping("orderlist")
public class OrderlistController {
    /**
     * 服务对象
     */
    @Resource
    private OrderlistService orderlistService;
    @Resource
    private WhtielistService whtielistService;
    /**
     * 分页查询
     *
     * @param orderlist 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Orderlist>> queryByPage(Orderlist orderlist, PageRequest pageRequest) {
        return ResponseEntity.ok(this.orderlistService.queryByPage(orderlist, pageRequest));
    }

    @GetMapping("selectOrder")
    public ResultData selectOrder(Integer pageNum, Integer pageSize, String orderid, String projectname, String address, String stage) {
        log.info("Request arrives -selectOrder-");

        ResultData resultData = new ResultData();
        Orderlist orderlist = new Orderlist();

        if (pageNum != null) {
            orderlist.setPageNum(pageNum);
        }
        if (pageSize != null) {
            orderlist.setPageSize(pageSize);
        }
        if (orderid != null) {
            orderlist.setOrderid(orderid);
        }
        if (projectname != null) {
            orderlist.setProjectname(projectname);
        }
        if (stage != null) {
            orderlist.setStage(stage);
        }
        if (address != null) {
            orderlist.setFromaddr(address);
      /*    orderlist.setFundaddr(address);
            orderlist.setReceivedAddr(address);
            orderlist.setTransmitAddr(address);*/
        }
        PageInfo<Orderlist> list = null;
        try {
            list = orderlistService.queryAllOa(orderlist);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        resultData.setData(list);
        return resultData;
    }

    @GetMapping("selectCOrder")
    public ResultData selectCOrder(Integer pageNum,Integer pageSize,String fromAddr,Integer pid) {
        log.info("Request arrives orderlist -selectCOrder-");

        ResultData resultData = new ResultData();
        Orderlist orderlist = new Orderlist();
        if(pageNum != null){
            orderlist.setPageNum(pageNum);
        }
        if(pageSize != null){
            orderlist.setPageSize(pageSize);
        }
        if(pid != null){
            orderlist.setPid(pid);
        }
        log.info("11fromAddr :{}",fromAddr);
        if(fromAddr != null && !fromAddr.equals("")){
            log.info("fromAddr :{}",fromAddr);
            orderlist.setFromaddr(fromAddr);
            PageInfo<Orderlist> list = orderlistService.queryAll(orderlist);
            resultData.setData(list);
        }else{
            resultData.setCode(ResponseCode.PARAMETER_IS_NULL);
            resultData.setMsg("参数为空");
        }
        return resultData;
    }
    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Orderlist> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.orderlistService.queryById(id));
    }
    @GetMapping("selectFaddress")
    public ResultData selectFaddress( Integer pid) {
        log.info("Request arrives orderlist -selectFaddress-");

        ResultData resultData = new ResultData();
      String address = orderlistService.selectFaddress(pid);
        resultData.setData(address);
        return resultData;
    }
    @PostMapping("/isRepeated")
    public ResultData isRepeated(@RequestBody  Orderlist orderlist) {
        ResultData resultData = new ResultData();
        try{
            if (orderlist.getStage() == null || orderlist.getStage().equals("") ||
                    orderlist.getBuyAmount() == null ||
                    orderlist.getPid() == null ||
                    orderlist.getFromaddr() == null || orderlist.getFromaddr().equals("")) {
                resultData.setCode(ResponseCode.PARAMETER_IS_NULL);
                resultData.setMsg("failed");
                log.info("参数为空");
                return resultData;
            }

            List<Orderlist> orderlistVerify = orderlistService.queryAllByPro(orderlist);
            Whtielist whtielist = new Whtielist();
            whtielist.setPid(orderlist.getPid());
            if(orderlist.getStage().equals("whitelist")){
                WhtielistCsv whitelistCsvUser = whtielistService.queryByWhitelist(orderlist.getFromaddr(),orderlist.getPid());
                if(whitelistCsvUser == null ){
                    resultData.setCode(ResponseCode.NOT_WHITELISTED_USER);
                    resultData.setMsg("failed");
                    log.info("非白名单");
                    return resultData;
                }
                if(whitelistCsvUser.getShare() != 0) {
                    long l = orderlistService.queryAmountRepeat(whitelistCsvUser.getAddr(),orderlist.getPid(),orderlist.getStage());
                    if(l != 0 ){
                        resultData.setCode(ResponseCode.WHITELIST_USERS_REPEAT_PURCHASES);
                        resultData.setMsg("failed");
                        log.info("白名单用户重复购买");
                    }
                }
                whtielist.setType(1);
            }else{
                whtielist.setType(2);
            }
            List<Whtielist> whtielistList = whtielistService.queryAllByPro(whtielist);
            Integer singlePersonPurchased = 0;
            for (Orderlist orderlist1 : orderlistVerify){
                if (!orderlist1.getStatus().equals("failed")) {
                    singlePersonPurchased += orderlist1.getBuyAmount();
                }
            }
            singlePersonPurchased+=orderlist.getBuyAmount();
            if(whtielistList.size()>0){
                log.info("getHposa:{}",Integer.valueOf(whtielistList.get(0).getHposa()));
                log.info("singlePersonPurchased:{}",singlePersonPurchased);
                if(Integer.valueOf(whtielistList.get(0).getHposa())<singlePersonPurchased){
                    resultData.setCode(ResponseCode.EXCEED_MAX_PURCHASE_PER_ADDRESS);
                    resultData.setMsg("购买总份额超出单地址最大购买份额！");
                    orderlist.setStatus("failed");
                    orderlist.setFailuredescription("购买总份额超出单地址最大购买份额");

                    log.info("购买总份额超出单地址最大购买份额");
                }else if(Integer.valueOf(whtielistList.get(0).getMposa())>orderlist.getBuyAmount()){
                    resultData.setCode(ResponseCode.BELOW_MIN_PURCHASE_PER_TRANSACTION);
                    resultData.setMsg("单次购买不得少于单次地址最小购买份额！");
                    orderlist.setStatus("failed");
                    orderlist.setFailuredescription("单次购买不得少于单次地址最小购买份额");
                    log.info("单次购买不得少于单次地址最小购买份额");
                }
                Long l = orderlistService.totalPersonPurchased(orderlist.getPid(),orderlist.getStage());
                long purchasedAmount = (l != null) ? l : 0;
                long remainingAmount = whtielistList.get(0).getTokennumber() - purchasedAmount;
                if (remainingAmount < orderlist.getBuyAmount()) {
                    resultData.setCode(ResponseCode.EXCEED_TOTAL_PURCHASE_QUOTA);
                    resultData.setMsg("购买已超出总份额");
                    orderlist.setStatus("failed");
                    orderlist.setFailuredescription("购买已超出总份额");
                    log.info("购买已超出总份额");
                }
            }
            double hf = CurrencyConverter.calculateFee(orderlist.getSize());
            log.info("计算结果数：{},前端传递数：{},size数：{}",hf,orderlist.getHandlingfee(),orderlist.getSize());
            if(hf == -1){
                resultData.setCode(ResponseCode.FAIL_CODE);
                resultData.setMsg("系统异常");
                orderlist.setStatus("failed");
                orderlist.setFailuredescription("第三方接口调用异常");
                log.info("系统异常");
            }
            if(orderlist.getType().equals("2")){
                hf = hf * orderlist.getBuyAmount();
                log.info("NFT计算结果数：{}",hf);
            }
            if(orderlist.getHandlingfee() < hf){
                resultData.setCode(ResponseCode.LESS_THAN_THE_SPECIFIED_HANDLING_FEE);
                resultData.setMsg("小于指定手续费");
                orderlist.setStatus("failed");
                orderlist.setFailuredescription("小于指定手续费");
                log.info("小于指定手续费");
            }
        }catch (Exception e){
            resultData.setCode(ResponseCode.FAIL_CODE);
            resultData.setMsg("failed");
            log.error("系统异常:{}__{}",e.getMessage(),e);
        }

        return resultData;
    }
    /**
     * 新增数据
     *
     * @param orderlist 实体
     * @return 新增结果
     */
    @PostMapping("/oderListSave")
    public ResultData add(@RequestBody  Orderlist orderlist) {
        log.info("Request arrives orderlist -oderListSave-");
        ResultData resultData = new ResultData();

        try {
            if(orderlist.getPid() == null
                    || orderlist.getTxHash() == null || orderlist.getTxHash().equals("")
                    || orderlist.getProjectname() == null || orderlist.getProjectname().equals("")
                    || orderlist.getType() == null || orderlist.getType().equals("")
                    || orderlist.getTokenname() == null || orderlist.getTokenname().equals("")
                    || orderlist.getFromaddr() == null || orderlist.getFromaddr().equals("")
                    || orderlist.getSize() == null || orderlist.getHandlingfee() == null){
                resultData.setCode(1);
                resultData.setMsg("参数为空");
                log.info("参数为空");
                return resultData;
            }

        if(orderlist.getType().equals("2")){
/*            if(orderlist.getBuyAmount() != 1){
                resultData.setCode(ResponseCode.NFT_SINGLE_ORDER_LIMIT);
                resultData.setMsg("NFT下每个订单只能购买份额1");
                log.info("NFT下每个订单只能购买份额1");
                orderlist.setStatus("fail");
            }*/
            orderlist.setType("NFT");
        }else{
            orderlist.setType("FT");
        }
        if(orderlist.getPid() == null){
            resultData.setCode(ResponseCode.PID_IS_NULL);
            resultData.setMsg("订单保存失败");
            log.info("pid不可为空");
            orderlist.setStatus("failed");
            return resultData;
        }
        Orderlist orderlistQuery = new Orderlist();
        orderlistQuery.setFromaddr(orderlist.getFromaddr());
        orderlistQuery.setStage(orderlist.getStage());
        orderlistQuery.setPid(orderlist.getPid());
        List<Orderlist> orderlistVerify = orderlistService.queryAllByPro(orderlistQuery);
        Whtielist whtielist = new Whtielist();
        whtielist.setPid(orderlist.getPid());
        if(orderlist.getStage().equals("whitelist")){
            WhtielistCsv whitelistCsvUser = whtielistService.queryByWhitelist(orderlist.getFromaddr(),orderlist.getPid());
            if(whitelistCsvUser == null){
                resultData.setCode(ResponseCode.NOT_WHITELISTED_USER);
                resultData.setMsg("订单保存失败");
                log.info("非白名单用户");
                orderlist.setStatus("failed");
                orderlist.setFailuredescription("非白名单用户");

            }
            log.info("getShare :{}",whitelistCsvUser.getShare());
            log.info("getBuyAmount :{}",orderlist.getBuyAmount());
            if(whitelistCsvUser.getShare() != 0) {
                 long l = orderlistService.queryAmountRepeat(whitelistCsvUser.getAddr(),orderlist.getPid(),orderlist.getStage());
                 if(l != 0 ){
                     resultData.setCode(ResponseCode.WHITELIST_USERS_REPEAT_PURCHASES);
                     resultData.setMsg("订单保存失败");
                     log.info("白名单用户重复购买");
                     orderlist.setStatus("failed");
                     orderlist.setFailuredescription("白名单用户重复购买");

                 }
                 if(!whitelistCsvUser.getShare().equals(orderlist.getBuyAmount())){
                    resultData.setCode(ResponseCode.INCORRECT_PURCHASE_SHARE);
                    resultData.setMsg("订单保存失败");
                    log.info("购买数不等于规定购买数");
                    orderlist.setStatus("failed");
                     orderlist.setFailuredescription("购买数不等于规定购买数");
                }
            }
            whtielist.setType(1);
        }else{
            whtielist.setType(2);
        }
        List<Whtielist> whtielistList = whtielistService.queryAllByPro(whtielist);
        Integer singlePersonPurchased = 0;
        for (Orderlist orderlist1 : orderlistVerify){
            if (!orderlist1.getStatus().equals("failed")) {
                singlePersonPurchased += orderlist1.getBuyAmount();
            }
        }
        singlePersonPurchased+=orderlist.getBuyAmount();
        if(whtielistList.size()>0){
            log.info("getHposa:{}",Integer.valueOf(whtielistList.get(0).getHposa()));
            log.info("singlePersonPurchased:{}",singlePersonPurchased);
            if(Integer.valueOf(whtielistList.get(0).getHposa())<singlePersonPurchased){
                resultData.setCode(ResponseCode.EXCEED_MAX_PURCHASE_PER_ADDRESS);
                resultData.setMsg("购买总份额超出单地址最大购买份额！");
                orderlist.setStatus("failed");
                orderlist.setFailuredescription("购买总份额超出单地址最大购买份额");

                log.info("购买总份额超出单地址最大购买份额");
            }else if(Integer.valueOf(whtielistList.get(0).getMposa())>orderlist.getBuyAmount()){
                resultData.setCode(ResponseCode.BELOW_MIN_PURCHASE_PER_TRANSACTION);
                resultData.setMsg("单次购买不得少于单次地址最小购买份额！");
                orderlist.setStatus("failed");
                orderlist.setFailuredescription("单次购买不得少于单次地址最小购买份额");
                log.info("单次购买不得少于单次地址最小购买份额");
            }
            Long l = orderlistService.totalPersonPurchased(orderlist.getPid(),orderlist.getStage());
            long purchasedAmount = (l != null) ? l : 0;
            long remainingAmount = whtielistList.get(0).getTokennumber() - purchasedAmount;
            if (remainingAmount < orderlist.getBuyAmount()) {
                resultData.setCode(ResponseCode.EXCEED_TOTAL_PURCHASE_QUOTA);
                resultData.setMsg("购买已超出总份额");
                orderlist.setStatus("failed");
                orderlist.setFailuredescription("购买已超出总份额");
                log.info("购买已超出总份额");
            }
        }
            double hf = CurrencyConverter.calculateFee(orderlist.getSize());
            log.info("计算结果数：{},前端传递数：{},size数：{}",hf,orderlist.getHandlingfee(),orderlist.getSize());
            if(hf == -1){
                resultData.setCode(ResponseCode.FAIL_CODE);
                resultData.setMsg("系统异常");
                orderlist.setStatus("failed");
                orderlist.setFailuredescription("第三方接口调用异常");
                log.info("系统异常");
            }
            if(orderlist.getType().equals("2")){
                hf = hf * orderlist.getBuyAmount();
                log.info("NFT计算结果数：{}",hf);
            }
            if(orderlist.getHandlingfee() < hf){
                resultData.setCode(ResponseCode.LESS_THAN_THE_SPECIFIED_HANDLING_FEE);
                resultData.setMsg("小于指定手续费");
                orderlist.setStatus("failed");
                orderlist.setFailuredescription("小于指定手续费");
                log.info("小于指定手续费");
            }
        resultData.setData(this.orderlistService.insert(orderlist));
    }catch (DataAccessException e) {
            Throwable rootCause = e.getRootCause();
            if (rootCause instanceof SQLException) {
                SQLException sqlEx = (SQLException) rootCause;
                if (sqlEx.getMessage().contains("duplicate key value violates unique constraint")) {
                    // 处理违反唯一性约束的异常
                    resultData.setCode(ResponseCode.DUPLICATE_TXHASH_VALUES);
                    resultData.setMsg("系统错误");
                    log.info("catchError :{},{}",e.getMessage(),e);
                    return resultData;
                }
            }
            // 其他数据库错误
            resultData.setCode(ResponseCode.DATABASE_EXCEPTIONS);
            resultData.setMsg("系统错误");
            log.info("catchError :{},{}",e.getMessage(),e);
            return resultData;
        } catch (Exception e){
            resultData.setCode(ResponseCode.FAIL_CODE);
            resultData.setMsg("系统错误");
            log.info("catchError :{},{}",e.getMessage(),e);
            return resultData;
    }
        return resultData;
    }

    /**
     * 编辑数据
     *
     * @param orderlist 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Orderlist> edit(Orderlist orderlist) {
        return ResponseEntity.ok(this.orderlistService.update(orderlist));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.orderlistService.deleteById(id));
    }

    public void exportOrderlists() {
        Orderlist orderlist = new Orderlist();
        List<Orderlist> list  = orderlistService.queryAllByPro(orderlist);
        String filename = "orderlists.csv";
        CSVExportUtility.exportListToCSV(list,filename);
    }
    @PostMapping("/export/orderlists")
    public ResponseEntity<byte[]> exportUsers(@RequestBody  Orderlist orderlist) {
        try {
            List<Orderlist> list = orderlistService.queryAllByPro(orderlist);
            StringBuilder csvData = new StringBuilder("\uFEFF");
            csvData.append("ID,Order ID,Status,Project Name,Type,Token Name,Stage,From Address,Fund Address,Received Address,Transmit Address,Amount,Project Quota,Platform Quota,Created Time,Updated Time,Amount Float,Buy Amount,Project ID,Transaction Hash,Has Incribed,Price,Failure Description,Handling Fee\n");
            for (Orderlist order : list) {
                csvData.append(String.valueOf(order.getId())).append(",");
                csvData.append(order.getOrderid()).append(",");
                csvData.append(order.getStatus()).append(",");
                csvData.append(order.getProjectname()).append(",");
                csvData.append(order.getType()).append(",");
                csvData.append(order.getTokenname()).append(",");
                csvData.append(order.getStage()).append(",");
                csvData.append(order.getFromaddr()).append(",");
                csvData.append(order.getFundaddr()).append(",");
                csvData.append(order.getReceivedAddr()).append(",");
                csvData.append(order.getTransmitAddr()).append(",");
                ;
                csvData.append(String.valueOf(order.getAmount())).append(",");
                csvData.append(String.valueOf(order.getProjectquota())).append(",");
                csvData.append(String.valueOf(order.getPlatformquota())).append(",");
                csvData.append(String.valueOf(order.getCreatetime())).append(",");
                csvData.append(String.valueOf(order.getUpdatetime())).append(",");
                csvData.append(String.valueOf(order.getAmountFloat())).append(",");
                csvData.append(String.valueOf(order.getBuyAmount())).append(",");
                csvData.append(String.valueOf(order.getPid())).append(",");
                csvData.append(order.getTxHash()).append(",");
                csvData.append(order.getHasIncribled()).append(",");
                csvData.append(String.valueOf(order.getPrice())).append(",");
                csvData.append(order.getFailuredescription()).append(",");
                csvData.append(String.valueOf(order.getHandlingfee())).append("\n");
            }
            byte[] csvBytes = csvData.toString().getBytes(StandardCharsets.UTF_8);
            String fileName = String.valueOf(System.currentTimeMillis()) + ".csv";
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=utf-8");

            log.info("CSV file {} has been created successfully.", fileName);
            return ResponseEntity.ok().headers(headers).body(csvBytes);
        }catch (Exception e) {
            log.error("Error occurred while exporting CSV file: {}", e.getMessage(), e); // Log the full stack trace
            // Return a response entity with status 500 Internal Server Error
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

