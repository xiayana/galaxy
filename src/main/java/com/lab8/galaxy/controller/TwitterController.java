package com.lab8.galaxy.controller;

import com.lab8.galaxy.common.ResponseCode;
import com.lab8.galaxy.entity.Registration;
import com.lab8.galaxy.entity.ResultData;
import com.lab8.galaxy.service.RegistrationService;
import com.lab8.galaxy.utils.RandomCodeUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/twitter")
public class TwitterController {
    @Resource
    private RegistrationService registrationService;

    private static final String CONSUMER_KEY = "YOUR_CONSUMER_KEY";
    private static final String ACCESS_TOKEN = "USER_ACCESS_TOKEN";
    // Other necessary fields such as consumer secret, token secret, etc.

    /**
     * Follows a user on Twitter.
     *
     * @param userId The ID of the user to follow.
     * @param follow Whether to enable notifications for the target user.
     * @return The response from Twitter API.
     */
    public String followUser(String userId, boolean follow) {
        // Construct the request URL
        String url = "https://api.twitter.com/1.1/friendships/create.json?user_id=" + userId + "&follow=" + follow;

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "OAuth oauth_consumer_key=\"" + CONSUMER_KEY + "\", oauth_nonce=\"AUTO_GENERATED_NONCE\", oauth_signature=\"AUTO_GENERATED_SIGNATURE\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"AUTO_GENERATED_TIMESTAMP\", oauth_token=\"" + ACCESS_TOKEN + "\", oauth_version=\"1.0\"");
        // The authorization header should include a properly generated OAuth 1.0 signature

        // Create a new HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Execute the request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        // Return the response body
        return response.getBody();
    }
    @GetMapping("/twitterUserInfo")
    public ResultData getTwitterUserInfo(@RequestParam("oauth_token") String oauthToken,
                                   @RequestParam("oauth_verifier") String oauthVerifier) {
        String consumerKey = "yfgVf4ftA2067hbfpxZhwgB2b";
        String consumerSecret = "kVGtNbGLIjg0remVe9U8QJWLVfXjyLPbL1U56OzxINVwWN1fmO";
        ResultData resultData = new ResultData();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret);
                // 如果需要，添加代理设置
/*                .setHttpProxyHost("127.0.0.1")
                .setHttpProxyPort(1662);*/

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        try {
            // 交换访问令牌
            RequestToken requestToken = new RequestToken(oauthToken, consumerSecret);
            AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);

            // 使用获取的访问令牌获取用户信息
            twitter.setOAuthAccessToken(accessToken);
            resultData.setData(twitter.verifyCredentials());
            return resultData; // 返回用户信息
        } catch (TwitterException e) {
            e.printStackTrace();
            return null; // 实际应用中应该处理错误
        }
    }
    // 这是处理Twitter回调的方法
    @GetMapping("/twitterCallback")
    public String twitterCallback(String oauth_token, String oauth_verifier) {
        String consumerKey = "yfgVf4ftA2067hbfpxZhwgB2b";
        String consumerSecret = "kVGtNbGLIjg0remVe9U8QJWLVfXjyLPbL1U56OzxINVwWN1fmO";

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret);
 /*       // 如果需要，添加代理设置
                .setHttpProxyHost("127.0.0.1")
                .setHttpProxyPort(1662);*/
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        try {
            // 交换访问令牌
            AccessToken accessToken = twitter.getOAuthAccessToken(new RequestToken(oauth_token, ""), oauth_verifier);

            // 设置访问令牌以获取用户信息
            twitter.setOAuthAccessToken(accessToken);
            User user = twitter.verifyCredentials();
            log.info("User ID: " + user.getId() + ", Screen Name: " + user.getScreenName());
            // 现在使用accessToken去关注另一个用户
           // String followResponse = followUserWithAccessToken(accessToken.getToken());
            //log.info( "User ID: " + user.getId() + ", Screen Name: " + user.getScreenName() + ". Follow response: " + followResponse);;

            System.out.println("User ID: " + user.getId() + ", Screen Name: " + user.getScreenName());
            return "User ID: " + user.getId() + ", Screen Name: " + user.getScreenName();
            // 返回一些用户信息，实际应用中可以根据需要返回更多或更详细的信息
          //  return "User ID: " + user.getId() + ", Screen Name: " + user.getScreenName();
        } catch (TwitterException e) {
            e.printStackTrace();
           // return "Failed to verify credentials: " + e.getMessage();
        }
        return "User";
    }
/*    private String followUserWithAccessToken(String userAccessToken) {
        // 设置目标用户
        String targetUserId = "Joy16891167669"; // 或者是需要关注的用户ID
        String url = "https://api.twitter.com/1.1/friendships/create.json?screen_name=" + targetUserId + "&follow=true";

        // 设置代理
        SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 1662));
        clientHttpReq.setProxy(proxy);

        // 使用带有代理的RestTemplate
        RestTemplate restTemplate = new RestTemplate(clientHttpReq);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String rd = RandomCodeUtil.generateRandomCode();
        // 获取当前时间的时间戳（单位：秒）
        long timestamp = System.currentTimeMillis() / 1000L;
        headers.set("Authorization", "OAuth oauth_consumer_key=\"yfgVf4ftA2067hbfpxZhwgB2b\", oauth_nonce=\"kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg\", oauth_signature=\"AAAAAAAAAAAAAAAAAAAAADUHswEAAAAATzHoWGk1jw9TyueZy4fubLeauK8%3DVyKsiDMglnxeEDRKSZlfqpYwMFAh2zzDisT7FhYaNgj8FFYmiE\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\""+timestamp+"\", oauth_token=\"" + userAccessToken + "\", oauth_version=\"1.0\"");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        return response.getBody();
    }

    public static <SortedSet> String generateSignature(String method, String url, Map<String, String> oauthParams, String consumerSecret, String tokenSecret) throws Exception {
        java.util.SortedSet<String> sortedKeys = new TreeSet<String>(oauthParams.keySet());
        StringBuilder base = new StringBuilder();
        for (String key : sortedKeys) {
            base.append(key).append("=").append(URLEncoder.encode(oauthParams.get(key), "UTF-8")).append("&");
        }
        String baseString = method.toUpperCase() + "&" +
                URLEncoder.encode(url, "UTF-8") + "&" +
                URLEncoder.encode(base.toString().substring(0, base.length() - 1), "UTF-8");

        String signingKey = URLEncoder.encode(consumerSecret, "UTF-8") + "&" + URLEncoder.encode(tokenSecret, "UTF-8");

        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(signingKey.getBytes("UTF-8"), "HmacSHA1"));
        String signature = new String(Base64.getEncoder().encode(mac.doFinal(baseString.getBytes("UTF-8"))), "UTF-8");

        return signature;
    }*/
    // 返回Twitter的响应
/*    @GetMapping("/twitterCallback")
    public String twitterCallback(@RequestParam("oauth_token") String oauth_token,
                                  @RequestParam("oauth_verifier") String oauth_verifier) {
        String consumerKey = "YOUR_CONSUMER_KEY";
        String consumerSecret = "YOUR_CONSUMER_SECRET";

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                // 如果需要，添加代理设置
                .setHttpProxyHost("127.0.0.1")
                .setHttpProxyPort(1662);

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        try {
            // 交换访问令牌
            AccessToken accessToken = twitter.getOAuthAccessToken(new RequestToken(oauth_token, ""), oauth_verifier);

            // 设置访问令牌以获取用户信息
            twitter.setOAuthAccessToken(accessToken);
            User user = twitter.verifyCredentials();

            // 在这里添加用户关注指定账号的代码
            // 使用用户ID或screenName替换'YOUR_TARGET_USER_ID_OR_SCREEN_NAME'
            twitter.createFriendship("Joy16891167669");

            // 返回用户信息和关注成功的消息
            return "User ID: " + user.getId() + ", Screen Name: " + user.getScreenName() + " has successfully followed YOUR_TARGET_USER_ID_OR_SCREEN_NAME.";
        } catch (TwitterException e) {
            e.printStackTrace();
            return "Failed to verify credentials or follow the target account: " + e.getMessage();
        }
    }*/

    @GetMapping("/checkFriendship")
    public String checkFriendship(@RequestParam("sourceUserId") long sourceUserId,
                                  @RequestParam("targetScreenName") String targetScreenName) {
        String consumerKey = "YOUR_CONSUMER_KEY";
        String consumerSecret = "YOUR_CONSUMER_SECRET";
        String accessToken = "USER_ACCESS_TOKEN"; // 用户的access token
        String accessTokenSecret = "USER_ACCESS_TOKEN_SECRET"; // 用户的access token secret

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret)
                // 如果需要，添加代理设置
                .setHttpProxyHost("127.0.0.1")
                .setHttpProxyPort(1662);

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        try {
            // 获取两个用户之间的关系
            Relationship relationship = twitter.showFriendship(sourceUserId, Long.parseLong(targetScreenName));

            if (relationship.isSourceFollowingTarget()) {
                return "User with ID: " + sourceUserId + " is following " + targetScreenName + ".";
            } else {
                return "User with ID: " + sourceUserId + " is not following " + targetScreenName + ".";
            }
        } catch (TwitterException e) {
            e.printStackTrace();
            return "Failed to verify the relationship: " + e.getMessage();
        }
    }

/*
    @GetMapping("/twitterCallback")
    public String twitterCallback(@RequestParam("oauth_token") String oauth_token,
                                  @RequestParam("oauth_verifier") String oauth_verifier) {
        String consumerKey = "yfgVf4ftA2067hbfpxZhwgB2b";
        String consumerSecret = "kVGtNbGLIjg0remVe9U8QJWLVfXjyLPbL1U56OzxINVwWN1fmO";
        // 需要用户关注的Twitter账户ID或Screen Name
        String targetUserId = "Joy16891167669";

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                // 如果需要，添加代理设置
                .setHttpProxyHost("127.0.0.1")
                .setHttpProxyPort(1662);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        try {
            // 交换访问令牌
            AccessToken accessToken = twitter.getOAuthAccessToken(new RequestToken(oauth_token, ""), oauth_verifier);

            // 设置访问令牌以获取用户信息
            twitter.setOAuthAccessToken(accessToken);
            User user = twitter.verifyCredentials();

            // 让用户关注指定的账户
            twitter.createFriendship(targetUserId);

            // 返回用户信息和关注成功的消息
            return "User ID: " + user.getId() + ", Screen Name: " + user.getScreenName() +
                    " has successfully followed " + targetUserId + ".";
        } catch (TwitterException e) {
            e.printStackTrace();
            return "Failed to process Twitter callback: " + e.getMessage();
        }
    }
*/
    @GetMapping("/twitterUrl")
    public ResultData requestTwitterAuthorization(String callbackUrl) {
        ResultData resultData = new ResultData();
        try {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("yfgVf4ftA2067hbfpxZhwgB2b")
                    .setOAuthConsumerSecret("kVGtNbGLIjg0remVe9U8QJWLVfXjyLPbL1U56OzxINVwWN1fmO");
/*                    // 如果需要，设置代理
                    .setHttpProxyHost("127.0.0.1")
                    .setHttpProxyPort(1662);*/

            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();
            // 判断是否提供了回调地址，并据此获取请求令牌
            RequestToken requestToken;
            if (callbackUrl != null && !callbackUrl.isEmpty()) {
                requestToken = twitter.getOAuthRequestToken(callbackUrl);
            } else {
                requestToken = twitter.getOAuthRequestToken();
            }
            // 将授权URL发送给前端
            //resultData.setData("请打开以下URL并授权: " + requestToken.getAuthorizationURL());
            Map<String, String> data = new HashMap<>();
            // 直接从RequestToken获取oauth_token
            String oauthToken = requestToken.getToken();
            data.put("authorizationUrl", requestToken.getAuthorizationURL());
            data.put("token", oauthToken); // 单独的token
            resultData.setData(data);
            return resultData;
        } catch (TwitterException e) {
            e.printStackTrace();
            resultData.setCode(1);
            resultData.setData("获取Twitter授权失败: " + e.getMessage());
            return resultData;
        }
    }
    @GetMapping("selectTt")
    public ResultData selectTt(String userId) {
        log.info("Request arrives  -selectCode-");

        ResultData resultData = new ResultData();
        if(userId != null && !userId.equals("")){
            Registration registration = new Registration();
            registration.setTwitterAddress(userId);
            List<Registration> registrationList = registrationService.queryByCode(registration);
            if(registrationList.size() > 0){
                resultData.setCode(ResponseCode.FAIL_CODE_ONE);
                resultData.setMsg("该用户已经绑定地址："+registrationList.get(0).getUserAddress());
                return resultData;
            }
            resultData.setData(registrationList);
        }else{
            resultData.setCode(ResponseCode.PARAMETER_IS_NULL);
            resultData.setMsg("参数为空");
        }
        return resultData;
    }
}
