package com.lab8.galaxy.utils;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

@Slf4j
public class CurrencyConverter {

    private static final String API_URL = "https://mempool.space/api/v1/prices";

    public static double convertCongToUSD(long cong) throws IOException, InterruptedException {
        // 获取当前 BTC 到 USD 的汇率
        double btcToUsdRate = getCurrentBtcToUsdRate();

        // 转换成 BTC（使用正确的转换因子：100,000,000）
        double btc = cong / 100_000_000.0;

        // 转换成 USD
        return btc * btcToUsdRate;
    }
    public static double convertSatoshiToBitcoin(long satoshi) {
        return satoshi / 100000000.0;
    }
    private static double getCurrentBtcToUsdRate() throws IOException, InterruptedException {
        // 创建 HTTP 客户端
        HttpClient client = HttpClient.newHttpClient();

        // 创建请求
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .build();

        // 发送请求并获取响应
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 解析 JSON 响应
        JSONObject json = new JSONObject(response.body());

        // 返回 BTC 到 USD 的汇率
        return json.getDouble("USD");
    }

    public static double calculateFee(Integer byteCount) {
        final int maxAttempts = 3; // 最大重试次数
        int attempts = 0; // 当前重试次数
        double fee = -1; // 初始化手续费
        //https://mempool.space/api/v1/fees/recommended  主网 https://mempool.space/testnet/api/v1/fees/recommended test
        while (attempts < maxAttempts && fee == -1) {
            try {
                log.info("Attempting to fetch fee, attempt {}", attempts + 1);
                // 创建HTTP客户端
                HttpClient client = HttpClient.newHttpClient();
                // 创建请求
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://mempool.space/api/v1/fees/recommended"))
                        .build();
                // 发送请求并获取响应
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                // 解析响应内容为JSON
                JSONObject fees = new JSONObject(response.body());
                // 获取最优费率
                int fastestFee = fees.getInt("fastestFee");
                // 计算手续费
                fee = byteCount * fastestFee * 1.2; // 如果成功，设置手续费并结束循环
                log.info("Fee fetched successfully,{}",fastestFee);
            } catch (Exception e) {
                log.error("Failed to fetch fee", e);
                attempts++; // 增加重试次数
                fee = -1; // 确保循环继续（如果已经达到最大尝试次数，则停止）
                if (attempts < maxAttempts) {
                    try {
                        Thread.sleep(3000); // 等待3秒再次重试
                    } catch (InterruptedException ie) {
                        log.error("Retry interrupted", ie);
                        Thread.currentThread().interrupt(); // 重置中断状态
                        break; // 中断时退出循环
                    }
                }
            }
        }
        if (fee == -1) {
            log.warn("Failed to fetch fee after {} attempts", maxAttempts);
        }
        return fee; // 返回计算的费用或-1
    }
    public static void main(String[] args) {
        // 测试方法
        int byteCount = 250; // 假设的字节数
        double fee = calculateFee(byteCount);
        System.out.println("手续费: " + fee);
        System.out.println("手11112222---: " + 55 * 42 * 1.2 );
        // 如果成功，设置手续费并结束循环
    }
}
