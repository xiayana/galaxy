package com.lab8.galaxy.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
/**
 * (Transaction)实体类
 *
 * @author xy
 * @since 2024-01-08 16:48:41
 */
@Data
public class Transaction implements Serializable {
    private static final long serialVersionUID = 232060557147517591L;
    
    private Integer id;
    /**
     * 订单ID
     */
    private String orderid;
    /**
     * 比特币地址
     */
    private String address;
    /**
     * 地址(address字段)说明信息
     */
    private String addrdesc;
    /**
     * address关联的订单交易，所生成的比特币链上Hash
     */
    private String txhash;
    /**
     * txHash所对应的交易的链上状态； pending： 进入内存池； minging：落块3个以内；succ: 落块3个以上；
     */
    private String txstatus;
    /**
     * FromAddress：用户付款地址； FundAddress：基金会收款地址；TransmitAddress：中转地址；ReceiveAddress: 资产接收地址；
     */
    private String addresstype;
    /**
     * 记录创建时间
     */
    private Date createtime;
    /**
     * 最近的更新时间
     */
    private Date updatetime;
    /**
     * address字段是发送方，则该值为1；否则为0；
     */
    private Integer addressissender;
    /**
     * address字段是接收方，则该值为1；否则为0；
     */
    private Integer addressisreceiver;
    /**
     * 项目发售阶段：白名单、公售；
     */
    private String stage;
    /**
     * 交易额度，单位是聪；
     */
    private Long amount;
    private String amountFloat;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * tokenName
     */
    private String tokenName;
    private Integer pageNum;
    private Integer pageSize;
}

