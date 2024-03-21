package com.lab8.galaxy.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
/**
 * (Usdcount)实体类
 *
 * @author xy
 * @since 2024-03-17 14:44:23
 */
@Data
public class Usdcount implements Serializable {
    private static final long serialVersionUID = 580353198643170308L;
    
    private Integer id;
    
    private BigDecimal usdcountNumber;
    
    private Date createdAt;
    
    private Date updatedAt;


}

