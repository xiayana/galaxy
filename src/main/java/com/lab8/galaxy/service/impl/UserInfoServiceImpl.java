package com.lab8.galaxy.service.impl;

import com.lab8.galaxy.entity.UserInfo;
import com.lab8.galaxy.dao.UserInfoDao;
import com.lab8.galaxy.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * (UserInfo)表服务实现类
 *
 * @author xy
 * @since 2024-01-06 17:03:25
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserInfo queryById(Integer id) {
        return this.userInfoDao.queryById(id);
    }

    public UserInfo loginUser(String userNumber, String userPwd) {
        UserInfo user = userInfoDao.findByUserNumber(userNumber);
        if (user != null && user.getUserpwd().equals(userPwd)) {
            return user;
        }
        return null; // 或抛出异常
    }
    /**
     * 分页查询
     *
     * @param userInfo 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<UserInfo> queryByPage(UserInfo userInfo, PageRequest pageRequest) {
        long total = this.userInfoDao.count(userInfo);
        return new PageImpl<>(this.userInfoDao.queryAllByLimit(userInfo, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    @Override
    public UserInfo insert(UserInfo userInfo) {
        this.userInfoDao.insert(userInfo);
        return userInfo;
    }

    /**
     * 修改数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    @Override
    public UserInfo update(UserInfo userInfo) {
        this.userInfoDao.update(userInfo);
        return this.queryById(userInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userInfoDao.deleteById(id) > 0;
    }
}
