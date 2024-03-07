package com.lab8.galaxy.utils;

import com.lab8.galaxy.entity.Orderlist;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExportUtility {

    public static void exportListToCSV(List<Orderlist> orderLists, String fileName) {
        // 使用FileWriter来写文件
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            // 写入CSV文件的头部
            fileWriter.append("ID,Order ID,Status,Project Name,Type,Token Name,Stage,From Address,Fund Address,Received Address,Transmit Address,Amount,Project Quota,Platform Quota,Created Time,Updated Time,Amount Float,Buy Amount,Project ID,Transaction Hash,Has Incribed,Page Num,Page Size,Price,Failure Description,Size,Handling Fee\n");
            // 遍历List，写入数据
            for (Orderlist order : orderLists) {
                fileWriter.append(String.valueOf(order.getId())).append(",");
                fileWriter.append(order.getOrderid()).append(",");
                fileWriter.append(order.getStatus()).append(",");
                fileWriter.append(order.getProjectname()).append(",");
                fileWriter.append(order.getType()).append(",");
                fileWriter.append(order.getTokenname()).append(",");
                fileWriter.append(order.getStage()).append(",");
                fileWriter.append(order.getFromaddr()).append(",");
                fileWriter.append(order.getFundaddr()).append(",");
                fileWriter.append(order.getReceivedAddr()).append(",");
                fileWriter.append(order.getTransmitAddr()).append(",");
                fileWriter.append(String.valueOf(order.getAmount())).append(",");
                fileWriter.append(String.valueOf(order.getProjectquota())).append(",");
                fileWriter.append(String.valueOf(order.getPlatformquota())).append(",");
                fileWriter.append(String.valueOf(order.getCreatetime())).append(",");
                fileWriter.append(String.valueOf(order.getUpdatetime())).append(",");
                fileWriter.append(String.valueOf(order.getAmountFloat())).append(",");
                // 注意这里可能需要处理Transaction List转换为字符串
                fileWriter.append(String.valueOf(order.getBuyAmount())).append(",");
                fileWriter.append(String.valueOf(order.getPid())).append(",");
                fileWriter.append(order.getTxHash()).append(",");
                fileWriter.append(order.getHasIncribled()).append(",");
                fileWriter.append(String.valueOf(order.getPageNum())).append(",");
                fileWriter.append(String.valueOf(order.getPageSize())).append(",");
                fileWriter.append(String.valueOf(order.getPrice())).append(",");
                fileWriter.append(order.getFailuredescription()).append(",");
                fileWriter.append(String.valueOf(order.getSize())).append(",");
                fileWriter.append(String.valueOf(order.getHandlingfee())).append("\n");
            }

            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 /*   private static String convertTransactionListToString(List<Transaction> transactions) {
        // 根据您的Transaction类实现逻辑进行调整
        StringBuilder sb = new StringBuilder();
        for (Transaction transaction : transactions) {
            // 假设Transaction类有一个toString方法或者您可以自定义输出格式
            sb.append(transaction.toString()).append("; ");
        }
        return sb.toString();
    }*/
}
