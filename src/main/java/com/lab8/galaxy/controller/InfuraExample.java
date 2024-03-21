package com.lab8.galaxy.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class InfuraExample {
/*
    public static void main(String[] args) {
        String infuraEndpoint = "https://sepolia.infura.io/v3/e29512aa48f946c3a4ea1edb08050320"; // 将这里的"你的项目ID"替换成你的Infura项目ID
        String txHash = "0xd60078a6f374728f37fdf72b9bc2a9dec8bb7587403c7aed92c20cb541972c99"; // 将这里的"你的交易哈希"替换成你要查询的交易哈希

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(infuraEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\"jsonrpc\":\"2.0\",\"method\":\"eth_getTransactionReceipt\",\"params\":[\"" + txHash + "\"],\"id\":1}"
                ))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response status code: " + response.statusCode());
            System.out.println("Response body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/


    public static void main(String[] args) {
        String infuraEndpoint = "https://sepolia.infura.io/v3/e29512aa48f946c3a4ea1edb08050320"; // 替换成你的Infura项目ID
        String txHash = "0xd60078a6f374728f37fdf72b9bc2a9dec8bb7587403c7aed92c20cb541972c99"; // 替换成你要查询的交易哈希

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(infuraEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\"jsonrpc\":\"2.0\",\"method\":\"eth_getTransactionByHash\",\"params\":[\"" + txHash + "\"],\"id\":1}"
                ))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response status code: " + response.statusCode());
            System.out.println("Response body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
