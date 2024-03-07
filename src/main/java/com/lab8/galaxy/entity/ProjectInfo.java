package com.lab8.galaxy.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (ProjectInfo)实体类
 *
 * @author xy
 * @since 2024-01-03 16:19:22
 */
@Data
public class ProjectInfo implements Serializable {
    private static final long serialVersionUID = -63024609379706186L;
    
    private Integer id;
    private String projecttype;//项目类型1FT,2NFT
    private String projectname;//项目名称
    private String projecttokenname;//tokenName
    private String projectdescription;//项目简介
    private String projectlogo;//logo Base64
    private String logoSuffix;
    private String projecthead;//头像 Base64
    private String headSuffix;
    private String projectnft;//2类型 NFT Base64
    private String nftSuffix;
    private String website;//同表单名
    private String discord;
    
    private String twitter;
    
    private String gitbook;
    
    private String telegram;
    
    private String github;
    private Integer status;//1上架 2下架
    
    private Date createdAt;
    private Date updateAt;

    private String whitelistCount;//白名单发售数量
    private String whitelistStage;//当前状态Upcoming：时间未开始，Active：开始售卖，Ended：售卖时间到期结束
    private String publicCount;//公共发售数量
    private String publicStage;//当前状态Upcoming：时间未开始，Active：开始售卖，Ended：售卖时间到期结束

    private Integer pageNum;
    private Integer pageSize;
    private Integer wid;// 白名单主键id
    private Integer pubid;//公共信息主键id
    private Integer pdid;//项目详情主键id
    private Integer singlePersonPurchased;//担任已购买份额
    private Integer size;

}

