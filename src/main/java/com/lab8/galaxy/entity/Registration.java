package com.lab8.galaxy.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
/**
 * (Registration)实体类
 *
 * @author xy
 * @since 2024-03-11 17:01:22
 */
@Data
public class Registration implements Serializable {
    private static final long serialVersionUID = -15223899244780014L;
    
    private Integer id;
    /**
     * 用户地址
     */
    private String userAddress;
    /**
     * 是否生效
     */
    private Integer isActive; //1生效2未生效
    /**
     * 个人积分
     */
    private Long personalScore;
    /**
     * 邀请人（数组id）
     */
    private String invitedIds;
    /**
     * 存款数额（USD）
     */
    private String depositAmount;
    /**
     * 个人邀请码
     */
    private String personalInvitationCode;
    /**
     * 上级用户地址（邀请人）
     */
    private String parentUserAddress;
    /**
     * 上级用户邀请码
     */
    private String parentUserCode;
    private Date createdAt;
    
    private Date updatedAt;
    /**
     * 推特用户ID
     */
    private String twitterAddress;
    /**
     * 推特用户名
     */
    private String twitterScreenName;

    private Integer pageNum;
    private Integer pageSize;
    private Integer rank;
    private BigDecimal ethcount;
    private BigDecimal usdccount;
    private BigDecimal triascount;
    private BigDecimal usdtcount;

}

