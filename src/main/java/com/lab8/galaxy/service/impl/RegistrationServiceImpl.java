package com.lab8.galaxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lab8.galaxy.entity.Registration;
import com.lab8.galaxy.dao.RegistrationDao;
import com.lab8.galaxy.service.RegistrationService;
import com.lab8.galaxy.utils.RandomCodeUtil;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * (Registration)表服务实现类
 *
 * @author xy
 * @since 2024-03-11 17:01:23
 */
@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {
    @Resource
    private RegistrationDao registrationDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Registration queryById(Integer id) {
        return this.registrationDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param registration 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Registration> queryByPage(Registration registration, PageRequest pageRequest) {
        long total = this.registrationDao.count(registration);
        return new PageImpl<>(this.registrationDao.queryAllByLimit(registration, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param registration 实例对象
     * @return 实例对象
     */
    @Override
    public Registration insert(Registration registration) {
        registration.setPersonalInvitationCode(RandomCodeUtil.generateRandomCode());
        Registration registration1 = new Registration();
        registration1.setPersonalInvitationCode(registration.getParentUserCode());
      List<Registration> registrationList =  registrationDao.queryAll(registration1);
      if(!registrationList.isEmpty()){
          registration.setParentUserAddress(registrationList.get(0).getUserAddress());
      }
        registration.setIsActive(2);
        registration.setUsdccount(BigDecimal.valueOf(0));
        registration.setEthcount(BigDecimal.valueOf(0));
        registration.setTriascount(BigDecimal.valueOf(0));
        this.registrationDao.insert(registration);
        return registration;
    }
    @Override
    public PageInfo<Registration> queryAllPage(Registration registration){
        if(registration.getPageNum() == null ){
            registration.setPageNum(1);
        }
        if(registration.getPageSize() == null ){
            registration.setPageSize(10);
        }
        PageHelper.startPage(registration.getPageNum(), registration.getPageSize());
        List<Registration> list = registrationDao.queryAllPagePaiHang(registration);
        PageInfo<Registration> personPageInfo = new PageInfo<>(list);
        return personPageInfo;
    }

    @Override
    public Integer queryByRank(Integer id) {
        return registrationDao.queryByRank(id);
    }

    /**
     * 修改数据
     *
     * @param registration 实例对象
     * @return 实例对象
     */
    @Override
    public Registration update(Registration registration) {
        this.registrationDao.update(registration);
        return this.queryById(registration.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.registrationDao.deleteById(id) > 0;
    }

    @Override
    public List<Registration> queryByCode(Registration registration) {
        List<Registration> list = registrationDao.queryAll(registration);
        return list;
    }

    @Override
    public Integer selectCount() {
        Registration registration = new Registration();
        Long l = registrationDao.count(registration);
        return Integer.valueOf(String.valueOf(l));
    }

    @Override
    public BigDecimal selectCountAmount() {
        BigDecimal bg = registrationDao.sumDepositAmount();
        return bg;
    }
}
