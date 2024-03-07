package com.lab8.galaxy.controller;

import com.lab8.galaxy.entity.ResultData;
import com.lab8.galaxy.entity.UserInfo;
import com.lab8.galaxy.service.UserInfoService;
import com.lab8.galaxy.utils.JwtUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (UserInfo)表控制层
 *
 * @author xy
 * @since 2024-01-06 17:03:24
 */
@RestController
@RequestMapping("userInfo")
public class UserInfoController {
    /**
     * 服务对象
     */
    @Resource
    private UserInfoService userInfoService;
    @PostMapping("/login")
    public ResultData login(@RequestBody UserInfo loginUser) {
        ResultData resultData = new ResultData();
        UserInfo user = userInfoService.loginUser(loginUser.getUsernumber(), loginUser.getUserpwd());
        if (user != null) {
            String token = JwtUtils.generateToken(user);
            user.setUserToken(token);
            user.setUserpwd("");
            resultData.setData(user);
            return resultData; // 返回JWT
        }else{
            resultData.setCode(1);
            resultData.setMsg("账号不存在或密码不正确");
        }
        return resultData;
    }
    /**
     * 分页查询
     *
     * @param userInfo 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<UserInfo>> queryByPage(UserInfo userInfo, PageRequest pageRequest) {
        return ResponseEntity.ok(this.userInfoService.queryByPage(userInfo, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<UserInfo> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.userInfoService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param userInfo 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<UserInfo> add(UserInfo userInfo) {
        return ResponseEntity.ok(this.userInfoService.insert(userInfo));
    }

    /**
     * 编辑数据
     *
     * @param userInfo 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<UserInfo> edit(UserInfo userInfo) {
        return ResponseEntity.ok(this.userInfoService.update(userInfo));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.userInfoService.deleteById(id));
    }

}

