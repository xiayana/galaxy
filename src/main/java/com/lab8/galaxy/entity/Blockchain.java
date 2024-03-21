package com.lab8.galaxy.entity;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
/**
 * (Blockchain)实体类
 *
 * @author xy
 * @since 2024-03-17 16:56:04
 */
@Data
public class Blockchain implements Serializable {
    private static final long serialVersionUID = 445072060323520817L;
    
    private Integer id;
    /**
     * 地址
     */
    private String address;
    /**
     * 最后更新时间
     */
    private Date lastUpdated;
    /**
     * 链名
     */
    private String label;
    /**
     * lianid
     */
    private Integer chainId;
    private List<Tokentable> bitcoin;


}

