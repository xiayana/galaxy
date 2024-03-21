package com.lab8.galaxy.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
/**
 * (Tokentable)实体类
 *
 * @author xy
 * @since 2024-03-17 16:30:04
 */
@Data
public class Tokentable implements Serializable {
    private static final long serialVersionUID = -11654947568021523L;
    
    private Integer id;
    
    private String label;
    
    private String address;
    
    private Integer pid;
    
    private Integer inMain;
    
    private Date lastUpdated;


}

