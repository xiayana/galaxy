package com.lab8.galaxy.common;


/**
 * 响应编码
 * @author xy
 * @since 2021/6/23 17:16
 */
public interface ResponseCode {
    /**
     * 正常
     */
    int SUCCESS_CODE = 0;
    /**
     * 失败
     */
    int FAIL_CODE_ONE = 1;
    /**
     * 系统异常
     */
    int FAIL_CODE = 500;

    /**
     * sql启动失败
     */
    int SQL_IS_FAILURE = 1001;

    /**
     * 参数为空
     */
    int PARAMETER_IS_NULL = 1002;

    /**
     * 文件不存在
     */
    int FILE_NOTFOUND = 1003;
    /**
     * 参数重复
     */
    int PARAMETER_REPEAT = 1004;
    /**
     * 获取结果为空
     */
    int RESULT_NULL = 1005;
    /**
     * 尝试添加重复的txHash值
     */
    int DUPLICATE_TXHASH_VALUES = 1006;
    /**
     * 数据库相关异常
     */
    int DATABASE_EXCEPTIONS = 1007;
    /**
     * NFT下每个订单只能购买份额1
     */
    int NFT_SINGLE_ORDER_LIMIT = 1008;
    /**
     * pid不可为空
     */
    int PID_IS_NULL = 1009;
    /**
     * 非白名单用户
     */
    int NOT_WHITELISTED_USER = 1010;
    /**
     * 购买总份额超出单地址最大购买份额
     */
    int EXCEED_MAX_PURCHASE_PER_ADDRESS = 1011;
    /**
     * 单次购买不得少于单次地址最小购买份额
     */
    int BELOW_MIN_PURCHASE_PER_TRANSACTION = 1012;
    /**
     * 购买已超出总份额
     */
    int EXCEED_TOTAL_PURCHASE_QUOTA = 1013;
    /**
     * 购买数不等于规定购买数
     */
    int INCORRECT_PURCHASE_SHARE = 1014;
    /**
     * 白名单用户重复购买
     */
    int WHITELIST_USERS_REPEAT_PURCHASES = 1015;
    /**
     * 小于指定手续费
     */
    int LESS_THAN_THE_SPECIFIED_HANDLING_FEE = 1016;
}

