package com.lab8.galaxy.controller;

import com.github.pagehelper.PageInfo;
import com.lab8.galaxy.common.ResponseCode;
import com.lab8.galaxy.entity.*;
import com.lab8.galaxy.service.OrderlistService;
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

