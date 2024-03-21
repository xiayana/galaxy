package com.lab8.galaxy.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (UserInfo)实体类
 *
 * @author xy
 * @since 2024-01-06 17:03:24
 */
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 982136708072967182L;
    
    private Integer id;
    
    private String usernumber;
    
    private String userpwd;
    
    private String logintime;
    private String userToken;

}

