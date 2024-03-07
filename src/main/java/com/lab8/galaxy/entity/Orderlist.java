package com.lab8.galaxy.entity;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * (Orderlist)实体类
 *
 * @author xy
 * @since 2024-01-06 14:52:10
 */
@Data
@NoArgsConstructor  // 空构造方法
public class Orderlist implements Serializable {
    private static final long serialVersionUID = -99130122499807443L;
    
    private Integer id;
    /**
     * 订单ID
     */
    private String orderid;
    /**
     * 订单状态
     */
    private String status;
    /**
     * 项目名称
     */
    @NonNull
    private String projectname;
    /**
     * 项目类型，例如FT， NFT
     */
    private String type;
    /**
     * Token名称
     */
    private String tokenname;
    /**
     * 项目阶段：白名单阶段，公售阶段等；
     */
    private String stage;
    /**
     * 购买方地址
     */
    @NonNull
    private String fromaddr;
    /**
     * 基金会接收BTC地址
     */
    private String fundaddr;
    /**
     * 最终资产接收地址
     */
    private String receivedAddr;
    /**
     * 中转地址
     */
    private String transmitAddr;

    /**
     * 转账BTC金额；单位：聪
     */
    @NonNull
    private Long amount;
    /**
     * 项目方配额
     */
    private Float projectquota;
    /**
     * 平台方配额
     */
    private Float platformquota;

    /**
     * 订单创建时间
     */
    private Date createtime;
    /**
     * 订单更新时间
     */
    private Date updatetime;
    private String amountFloat;
    private List<Transaction>  tlist;
    private Integer buyAmount;
    @NonNull
    private Integer pid;
    private String txHash;
    private String hasIncribled;
    private Integer pageNum;
    private Integer pageSize;

    private Long price;//价值
    private String failuredescription;//失败说明

    private Integer size;//手续费字节数
    private Double handlingfee;//手续费
    private Double shi1;
}

