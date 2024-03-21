package com.lab8.galaxy.controller;

import java.math.BigDecimal;

public class ScoreCalculator {

    /**
     * 存款数
     * @param deposit
     * @return
     */
    public static BigDecimal calculateScore(BigDecimal deposit) {
        BigDecimal score = BigDecimal.ZERO;

        // 设置阶梯和相应的因子
        BigDecimal[] levels = {BigDecimal.valueOf(100), BigDecimal.valueOf(1000), BigDecimal.valueOf(10000), BigDecimal.valueOf(100000)};
        int[] factors = {0, 10, 20, 30, 40};  // 最后一项用于超过100000的情况

        BigDecimal previousLevel = BigDecimal.ZERO;
        for (int i = 0; i < levels.length && deposit.compareTo(previousLevel) > 0; i++) {
            BigDecimal currentLevel = levels[i];
            BigDecimal nextLevel = i + 1 < levels.length ? levels[i + 1] : deposit;

            // 计算当前阶梯的存款额
            BigDecimal levelDeposit = (deposit.compareTo(currentLevel) > 0 ? currentLevel : deposit).subtract(previousLevel);
            score = score.add(levelDeposit.multiply(BigDecimal.valueOf(factors[i])));

            previousLevel = currentLevel;
        }

        // 处理超过最高阶梯的情况
        if (deposit.compareTo(levels[levels.length - 1]) > 0) {
            BigDecimal excessDeposit = deposit.subtract(levels[levels.length - 1]);
            score = score.add(excessDeposit.multiply(BigDecimal.valueOf(factors[factors.length - 1])));
        }

        return score;
    }

/*    public static void main(String[] args) {
        BigDecimal deposit = new BigDecimal("2500");
        BigDecimal score = calculateScore(deposit);
        System.out.println("Total score for $" + deposit + " deposit is: " + score);
    }*/

    /**
     * 存款天数
     * @param days
     * @return
     */

    public static int calculateTimeScore(int days) {
    int score = 0;

    // 计算前10天的分数
    int firstPeriod = Math.min(days, 10);
    score += firstPeriod * 50;
    days -= firstPeriod;

    // 计算第31天到第60天的分数
    int secondPeriod = Math.min(days, 20);  // 如果超过30天，只计算30天
    score += secondPeriod * 100;
    days -= secondPeriod;

    // 计算第61天到第90天的分数
    int thirdPeriod = Math.min(days, 20);  // 同上
    score += thirdPeriod * 200;
    days -= thirdPeriod;

    // 如果还有更多天数，那么使用最高因子300
    score += days * 300;  // 剩余的天数全用最大因子计算

    return score;
}


    /**
     * 邀请人数
     * @param numberOfInvitations
     * @return
     */
    public static int calculateInvitationScore(int numberOfInvitations) {
        int score = 0;
        if (numberOfInvitations >= 1 && numberOfInvitations <= 5) {
            // 如果邀请的好友数量在1到5之间（含）
            score += numberOfInvitations * 50;
        } else if (numberOfInvitations > 5 && numberOfInvitations <= 10) {
            // 前5个好友每个50分
            score += 5 * 50;
            // 剩下的好友（第6到第10个），每个100分
            score += (numberOfInvitations - 5) * 100;
        } else if (numberOfInvitations > 10 && numberOfInvitations <= 100) {
            // 前10个好友，根据前面的阶梯计算
            score += 5 * 50 + 5 * 100;
            // 剩下的好友（第11到第100个），每个1000分
            score += (numberOfInvitations - 10) * 1000;
        } else if (numberOfInvitations > 100) {
            // 前100个好友，根据前面的阶梯计算
            score += 5 * 50 + 5 * 100 + 90 * 1000;
            // 剩下的好友（超过第100个），每个2000分
            score += (numberOfInvitations - 100) * 2000;
        }

        return score;
    }
    public static void main(String[] args) {
        int numberOfInvitations = 20;  // 示例邀请人数
        int score = calculateInvitationScore(numberOfInvitations);
        int score1 = calculateTimeScore(numberOfInvitations);

        System.out.println("Total score for " + numberOfInvitations + " invitations is: " + score);
        System.out.println("Total score1 for " + numberOfInvitations + " invitations is: " + score1);

    }

}
