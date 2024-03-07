package com.lab8.galaxy.service.impl;

import com.lab8.galaxy.dao.OrderlistDao;
import com.lab8.galaxy.dao.ProjectInfoDao;
import com.lab8.galaxy.dao.WhtielistCsvDao;
import com.lab8.galaxy.entity.Orderlist;
import com.lab8.galaxy.entity.Whtielist;
import com.lab8.galaxy.dao.WhtielistDao;
import com.lab8.galaxy.entity.WhtielistCsv;
import com.lab8.galaxy.service.WhtielistService;
import com.lab8.galaxy.utils.DateFormateUtil;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (Whtielist)表服务实现类
 *
 * @author xy
 * @since 2024-01-04 18:57:12
 */
@Service("whtielistService")
public class WhtielistServiceImpl implements WhtielistService {
    @Resource
    private WhtielistDao whtielistDao;
    @Resource
    private OrderlistDao orderlistDao;
    @Resource
    private WhtielistCsvDao whtielistCsvDao;
    @Resource
    private ProjectInfoDao projectInfoDao;
    private static final long SATOSHIS_PER_BITCOIN = 100_000_000L;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Whtielist queryById(Integer id) {
        Whtielist whtielist = whtielistDao.queryById(id);
        whtielist.getPid();
      Date dtime = whtielistCsvDao.queryByPid(whtielist.getPid());
      if(dtime !=null ){
          whtielist.setUploadDate("上次上传时间："+dtime);
      }
        return whtielist;
    }

    /**
     * 分页查询
     *
     * @param whtielist 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Whtielist> queryByPage(Whtielist whtielist, PageRequest pageRequest) {
        long total = this.whtielistDao.count(whtielist);
        return new PageImpl<>(this.whtielistDao.queryAllByLimit(whtielist, pageRequest), pageRequest, total);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Whtielist launchById(Integer id, String address) {
        Whtielist  whtielist = whtielistDao.queryById(id);
        if(address !=null && !address.equals("")){
            Orderlist orderlist = new Orderlist();
            if(whtielist.getType()==1){
                orderlist.setStage("whitelist");
            }else{
                orderlist.setStage("public");
            }
            orderlist.setPid(whtielist.getPid());
            orderlist.setFromaddr(address);
            List<Orderlist> list = orderlistDao.queryAllStatus(orderlist);
            Integer singlePersonPurchased = 0;
            for (Orderlist orderlist1 : list){
                if(orderlist1.getBuyAmount()!= null){
                    singlePersonPurchased += orderlist1.getBuyAmount();
                }
            }
            whtielist.setSinglePersonPurchased(singlePersonPurchased);
        }
        Orderlist orderlistTo = new Orderlist();
        if(whtielist.getType()==1){
            orderlistTo.setStage("whitelist");
        }else{
            orderlistTo.setStage("public");
        }

        Long l = orderlistDao.totalPersonPurchased(whtielist.getPid(),orderlistTo.getStage());
/*        orderlistTo.setPid(whtielist.getPid());
        List<Orderlist> listTo = orderlistDao.queryAll(orderlistTo);
        Integer singlePersonPurchasedTo = 0;
        for (Orderlist orderlist1To : listTo){
            if(orderlist1To.getBuyAmount()!= null){
                singlePersonPurchasedTo += orderlist1To.getBuyAmount();
            }
        }*/
        if(l == null ){
            whtielist.setTotalPersonPurchased(0);
        }else {
            whtielist.setTotalPersonPurchased(Integer.valueOf(l.toString()));
        }
        return whtielist;
    }
    /**
     * 新增数据
     *
     * @param whtielist 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Whtielist insert(Whtielist whtielist) {
        whtielist.setStarttime(DateFormateUtil.convertToUTCString(whtielist.getStarttime(), "yyyy-MM-dd HH:mm:ss"));
        whtielist.setEnttime(DateFormateUtil.convertToUTCString(whtielist.getEnttime(), "yyyy-MM-dd HH:mm:ss"));
       // ProjectInfo projectInfo = projectInfoDao.queryById(whtielist.getPid());
           Long l = btcToSatoshi(Double.valueOf(whtielist.getTargetnumber()));
            whtielist.setPrice(l);

        if(whtielist.getType() == 1 ){
            Long exist =  whtielistCsvDao.existByStatus(whtielist.getPid(),4);
            if(exist > 0 ){
                int i1 =  whtielistCsvDao.updateByStatus(whtielist.getPid(),1,3);
                int i2 =  whtielistCsvDao.updateByStatus(whtielist.getPid(),4,1);
            }
        }
        this.whtielistDao.insert(whtielist);
        return whtielist;
    }
    public static long btcToSatoshi(double btc) {
        return (long) (btc * SATOSHIS_PER_BITCOIN);
    }
    public static long calculate(double A, double B) {
        if (B == 0) {
            throw new IllegalArgumentException("除数 B 不能为 0");
        }
        return (long) Math.ceil(A * Math.pow(10, 8) / B);
    }

    /**
     * 修改数据
     *
     * @param whtielist 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Whtielist update(Whtielist whtielist) {
      //  ProjectInfo projectInfo = projectInfoDao.queryById(whtielist.getPid());
            Long l = btcToSatoshi(Double.valueOf(whtielist.getTargetnumber()));
            whtielist.setPrice(l);
        if(whtielist.getType() == 1 ){
            Long exist =  whtielistCsvDao.existByStatus(whtielist.getPid(),4);
            if(exist > 0 ){
                int i1 =  whtielistCsvDao.updateByStatus(whtielist.getPid(),1,3);
                int i2 =  whtielistCsvDao.updateByStatus(whtielist.getPid(),4,1);
            }
        }
        this.whtielistDao.update(whtielist);
        return this.queryById(whtielist.getId());
    }

    @Override
    public List<Whtielist> queryAllByPro(Whtielist whtielist) {
        return whtielistDao.queryAll(whtielist);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.whtielistDao.deleteById(id) > 0;
    }

    @Override
    public WhtielistCsv queryByWhitelist(String address, Integer id) {
        WhtielistCsv result = whtielistCsvDao.queryByWhitelist(address,id);
        return result;
    }

}
