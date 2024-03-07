package com.lab8.galaxy.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultData implements Serializable {
    private Integer code = 0;
    private String msg = "成功";
    private Object data;

}
