package com.lab8.galaxy.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Newtable)实体类
 *
 * @author xy
 * @since 2024-01-03 15:33:06
 */
@Data
public class Newtable implements Serializable {
    private static final long serialVersionUID = -30878559856231393L;
    
    private Integer id;
    
    private String name;


}

