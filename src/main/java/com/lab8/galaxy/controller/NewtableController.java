package com.lab8.galaxy.controller;

import com.lab8.galaxy.entity.Newtable;
import com.lab8.galaxy.entity.ResultData;
import com.lab8.galaxy.service.NewtableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * (Newtable)表控制层
 *
 * @author xy
 * @since 2024-01-03 15:33:06
 */
@RestController
@RequestMapping("newtable")
public class NewtableController {
    /**
     * 服务对象
     */
    @Autowired
    private NewtableService newtableService;

    /**
     * 分页查询
     *
     * @param newtable 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Newtable>> queryByPage(Newtable newtable, PageRequest pageRequest) {
        return ResponseEntity.ok(this.newtableService.queryByPage(newtable, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Newtable> queryById(@PathVariable("id")  Integer id) {
        return ResponseEntity.ok(this.newtableService.queryById(id));
    }

    @GetMapping("test")
    public ResultData test() {
        ResultData resultData = new ResultData();
        newtableService.createProjectDetails();
        return resultData;
    }

    /**
     * 新增数据
     *
     * @param newtable 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Newtable> add(Newtable newtable) {
        return ResponseEntity.ok(this.newtableService.insert(newtable));
    }

    /**
     * 编辑数据
     *
     * @param newtable 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Newtable> edit(Newtable newtable) {
        return ResponseEntity.ok(this.newtableService.update(newtable));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.newtableService.deleteById(id));
    }

    public static void main(String[] args) {
            System.out.println(Math.floor(0.00008 * Math.pow(10, 8) / 4));
    }


}

