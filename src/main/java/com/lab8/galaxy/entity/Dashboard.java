package com.lab8.galaxy.entity;

import lombok.Data;

@Data
public class Dashboard {
    private Long projectCount;//项目总数
    private double raisedCount;//订单金额总数美元
    private Long usersCount;//用户总数
}
