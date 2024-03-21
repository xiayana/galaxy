package com.lab8.galaxy.controller;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterLogin {
    private static final String CONSUMER_KEY = "MI7pBPYEW3w9OuHvxN31qoMIt";
    private static final String CONSUMER_SECRET = "N5mMtEgiLkFj5wcoXXvmhoaW8qyAqIfEi3NgXObm9D9siqQiKf";

    public static void main(String[] args) {
        try {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("gvcE3PzmGaX1YnOZI4FHhK6LZ")
                    .setOAuthConsumerSecret("Ntw0LBte7NUVy3Y71W53cO6OowQx1fndnDVztAQ5zFK0086oQi")
                    // 如果需要，设置代理
                    .setHttpProxyHost("127.0.0.1")
                    .setHttpProxyPort(1662);
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

            RequestToken requestToken = twitter.getOAuthRequestToken();
            System.out.println("请打开以下URL并授权:");
            System.out.println(requestToken.getAuthorizationURL());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
 /*   public static void main1(String[] args) {
        String accessToken = "CdSfuQAAAAABqtI_AAABjhvzmFM";
        String accessTokenSecret = "YOUR_ACCESS_TOKEN_SECRET";

        try {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("MI7pBPYEW3w9OuHvxN31qoMIt")
                    .setOAuthConsumerSecret("N5mMtEgiLkFj5wcoXXvmhoaW8qyAqIfEi3NgXObm9D9siqQiKf")
                    .setOAuthAccessToken(accessToken)
                    .setOAuthAccessTokenSecret(accessTokenSecret)
                    // 设置代理
                    .setHttpProxyHost("127.0.0.1")
                    .setHttpProxyPort(1662);

            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

            User user = twitter.verifyCredentials(); // 使用access token验证用户凭证并获取用户信息

            // 打印用户信息
            System.out.println("User ID: " + user.getId());
            System.out.println("User Name: " + user.getName());
            System.out.println("Screen Name: " + user.getScreenName());
            // 可以根据需要添加更多用户信息的打印
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }*/
/*
public static void main(String[] args) {
    String consumerKey = "MI7pBPYEW3w9OuHvxN31qoMIt";
    String consumerSecret = "N5mMtEgiLkFj5wcoXXvmhoaW8qyAqIfEi3NgXObm9D9siqQiKf";

    // 从回调URL中获得的oauth_token和oauth_verifier
    String oauthToken = "CdSfuQAAAAABqtI_AAABjhvzmFM"; // 示例值
    String oauthVerifier = "vsjbEZFqhm9nz6jXzoSEpBDythPfiYjb"; // 示例值

    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
            .setOAuthConsumerKey(consumerKey)
            .setOAuthConsumerSecret(consumerSecret)
            // 设置代理
            .setHttpProxyHost("127.0.0.1")
            .setHttpProxyPort(1662);

    TwitterFactory tf = new TwitterFactory(cb.build());
    Twitter twitter = tf.getInstance();

    try {
        // 使用oauth_token创建一个RequestToken对象
        RequestToken requestToken = new RequestToken(oauthToken, consumerSecret);

        // 使用oauth_verifier交换访问令牌
        AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);

        // 打印访问令牌和密钥，实际应用中应安全存储这些信息
        System.out.println("Access Token: " + accessToken.getToken());
        System.out.println("Access Token Secret: " + accessToken.getTokenSecret());

        // 以下代码演示如何使用获得的访问令牌进行API调用
        // twitter.setOAuthAccessToken(accessToken);
        // User user = twitter.verifyCredentials();
        // System.out.println("User Name: " + user.getName());

    } catch (TwitterException e) {
        e.printStackTrace();
    }
}
*/

/*    public static void main(String[] args) {
        String accessToken = "1725432100650983424-Zi0qdpBGz0cpOccHpA3uXb3uIi2O2t";
        String accessTokenSecret = "XLJqBxff1WO7s59Jq7AyvzYVbdqxob7ESDOTR2UcnZ2Oy";

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("MI7pBPYEW3w9OuHvxN31qoMIt") // 使用您的consumer key
                .setOAuthConsumerSecret("N5mMtEgiLkFj5wcoXXvmhoaW8qyAqIfEi3NgXObm9D9siqQiKf") // 使用您的consumer secret
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret)
                // 如果需要，设置代理
                .setHttpProxyHost("127.0.0.1")
                .setHttpProxyPort(1662);

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        try {
            User user = twitter.verifyCredentials(); // 使用access token验证用户凭证并获取用户信息

            // 打印用户信息
            System.out.println("User ID: " + user.getId());
            System.out.println("User Name: " + user.getName());
            System.out.println("Screen Name: " + user.getScreenName());
            System.out.println("Location: " + user.getLocation());
            System.out.println("User Description: " + user.getDescription());
            // 可以根据需要添加更多用户信息的打印
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }*/
}
