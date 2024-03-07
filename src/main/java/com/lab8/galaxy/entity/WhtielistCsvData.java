package com.lab8.galaxy.entity;

import lombok.Data;

@Data
public class WhtielistCsvData {
    private String column1;
    private Integer column2;
    // 构造函数
    public WhtielistCsvData(String column1, Integer column2) {
        this.column1 = column1;
        this.column2 = column2;
    }
}
