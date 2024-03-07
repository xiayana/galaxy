package com.lab8.galaxy.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * (WhtielistCsv)实体类
 *
 * @author xy
 * @since 2024-01-05 16:16:30
 */
@Data
public class WhtielistCsv implements Serializable {
    private static final long serialVersionUID = -88219997780227283L;
    
    private Integer id;
    
    private String addr;

    private Integer share;
    
    private Integer pid;
    
    private Integer status;

    private Date createdAt;

    // 无参构造函数
    public WhtielistCsv() {
    }
    public WhtielistCsv(String addr,Integer share, Integer pid, int i) {
        this.addr = addr;
        this.share = share;
        this.pid = pid;
        this.status = i; // 默认状态为 1
    }
}

