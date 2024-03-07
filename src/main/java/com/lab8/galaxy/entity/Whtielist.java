package com.lab8.galaxy.entity;

import java.io.Serializable;
import lombok.Data;
/**
 * (Whtielist)实体类
 *
 * @author xy
 * @since 2024-01-04 18:57:11
 */
@Data
public class Whtielist implements Serializable {
    private static final long serialVersionUID = 254546949679653728L;
    
    private Integer id;
    
    private String projectnetwork;//选择网络
    
    private String projectcurrency;//选择币种
    
    private String starttime;//开始时间
    
    private String enttime;//结束时间
    
    private String hposa;//单地址最高份额

    private String popp;//项目方占比

    private Long tokennumber;//发售数量
    
    private String targetnumber;//价格
    
    private String mposa;//单地址最低份额


    private String projectmoneyaddress;//项目方收款地址

    private String uploadDate;//上次上传日期

    private Integer pid;//简介id
    private Integer type;//类型 1白名单 2公共
    private Integer singlePersonPurchased;//单人已购买份额
    private Integer totalPersonPurchased;//总已购买份额
    private Long price;
}

