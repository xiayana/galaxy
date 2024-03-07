package com.lab8.galaxy.entity;

import java.io.Serializable;
import lombok.Data;
/**
 * (FundAddress)实体类
 *
 * @author xy
 * @since 2024-01-15 20:25:14
 */
@Data
public class FundAddress implements Serializable {
    private static final long serialVersionUID = -18972591438241555L;
    
    private Integer id;
    
    private Integer pid;
    
    private String address;
    
    private String used;
    
    private String createtime;
    
    private String updatetime;


}

