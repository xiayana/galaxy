package com.lab8.galaxy.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
/**
 * (Orders)实体类
 *
 * @author xy
 * @since 2024-03-12 14:35:11
 */
@Data
public class Orders implements Serializable {
    private static final long serialVersionUID = 188191604845732905L;
    
    private Integer id;
    /**
     * 发起地址
     */
    private String initiatorAddress;
    /**
     * 接收地址
     */
    private String receiverAddress;
    /**
     * 类型
     */
    private String type;
    /**
     * 数额
     */
    private BigDecimal amount;
    /**
     * 状态
     */
    private String status;
    /**
     * 插入时间
     */
    private Date insertTime;
    /**
     * 插入时间
     */
    private String txhash;

}

