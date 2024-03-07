package com.lab8.galaxy.controller;

import com.github.pagehelper.PageInfo;
import com.lab8.galaxy.entity.ResultData;
import com.lab8.galaxy.entity.Transaction;
import com.lab8.galaxy.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Transaction)表控制层
 *
 * @author xy
 * @since 2024-01-08 16:48:41
 */
@Slf4j
@RestController
@RequestMapping("transaction")
public class TransactionController {
    /**
     * 服务对象
     */
    @Resource
    private TransactionService transactionService;

    /**
     * 分页查询
     *
     * @param transaction 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Transaction>> queryByPage(Transaction transaction, PageRequest pageRequest) {
        return ResponseEntity.ok(this.transactionService.queryByPage(transaction, pageRequest));
    }
    @GetMapping("selectTran")
    public ResultData selectTran(Integer pageNum, Integer pageSize ,String orderid, String address , String projectname) {
        log.info("Request arrives transaction -selectTran-");

        ResultData resultData = new ResultData();
        Transaction transaction = new Transaction();
        transaction.setPageNum(pageNum);
        transaction.setPageSize(pageSize);
        if(address !=null){
            transaction.setAddress(address);
        }
        if(orderid !=null){
            transaction.setOrderid(orderid);
        }
        if(projectname !=null){
            transaction.setProjectName(projectname);
        }
        PageInfo<Transaction> list = transactionService.queryAll(transaction);
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
    public ResponseEntity<Transaction> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.transactionService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param transaction 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Transaction> add(Transaction transaction) {
        return ResponseEntity.ok(this.transactionService.insert(transaction));
    }

    /**
     * 编辑数据
     *
     * @param transaction 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Transaction> edit(Transaction transaction) {
        return ResponseEntity.ok(this.transactionService.update(transaction));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.transactionService.deleteById(id));
    }

}

