package com.lab8.galaxy.controller;

import com.lab8.galaxy.common.ResponseCode;
import com.lab8.galaxy.entity.Blockchain;
import com.lab8.galaxy.entity.ResultData;
import com.lab8.galaxy.entity.Tokentable;
import com.lab8.galaxy.service.BlockchainService;
import com.lab8.galaxy.service.TokentableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Blockchain)表控制层
 *
 * @author xy
 * @since 2024-03-17 16:38:07
 */
@RestController
@RequestMapping("blockchain")
public class BlockchainController {
    /**
     * 服务对象
     */
    @Resource
    private BlockchainService blockchainService;
    @Resource
    private TokentableService tokentableService;
    /**
     * 分页查询
     *
     * @param blockchain 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Blockchain>> queryByPage(Blockchain blockchain, PageRequest pageRequest) {
        return ResponseEntity.ok(this.blockchainService.queryByPage(blockchain, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Blockchain> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.blockchainService.queryById(id));
    }
    @GetMapping("selectblockchain")
    public ResultData queryById() {
        ResultData resultData = new ResultData();
        Blockchain blockchain = new Blockchain();
        List<Blockchain> list =  blockchainService.queryAll(blockchain);
        resultData.setData(list);
        return resultData;
    }

    @PostMapping("verifyAddress")
    public ResultData verifyAddress(@RequestBody  Blockchain blockchain) {
        ResultData resultData = new ResultData();
        blockchain.setLastUpdated(null);
        List<Blockchain> list =  blockchainService.queryVerify(blockchain);
        if(list.isEmpty() || list.size() > 1){
            resultData.setCode(ResponseCode.FAIL_CODE_ONE);
            resultData.setMsg("failed");

            return resultData;
        }else{
            for(Tokentable tokentable : blockchain.getBitcoin()){
                tokentable.setLastUpdated(null);
                List<Tokentable> tlist = tokentableService.queryAll(tokentable);
                if(tlist.isEmpty()){
                    resultData.setCode(ResponseCode.FAIL_CODE_ONE);
                    resultData.setMsg("failed");
                    return resultData;
                }
            }
        }
        return resultData;
    }
    /**
     * 新增数据
     *
     * @param blockchain 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Blockchain> add(Blockchain blockchain) {
        return ResponseEntity.ok(this.blockchainService.insert(blockchain));
    }

    /**
     * 编辑数据
     *
     * @param blockchain 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Blockchain> edit(Blockchain blockchain) {
        return ResponseEntity.ok(this.blockchainService.update(blockchain));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.blockchainService.deleteById(id));
    }

}

