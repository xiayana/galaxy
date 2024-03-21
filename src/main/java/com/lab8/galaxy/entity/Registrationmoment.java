package com.lab8.galaxy.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
/**
 * (Registrationmoment)实体类
 *
 * @author xy
 * @since 2024-03-17 14:10:36
 */
@Data
public class Registrationmoment implements Serializable {
    private static final long serialVersionUID = -50107187407920044L;
    
    private Integer id;
    
    private String userAddress;
    
    private Integer isActive;
    
    private Long personalScore;
    
    private String invitedIds;
    
    private String depositAmount;
    
    private String personalInvitationCode;
    
    private String parentUserAddress;
    
    private Date createdAt;
    
    private Date updatedAt;
    
    private String twitterAddress;
    
    private String parentUserCode;
    
    private String twitterScreenName;
    
    private BigDecimal ethcount;
    
    private BigDecimal usdccount;
    
    private BigDecimal triascount;

    private BigDecimal usdtcount;

}

