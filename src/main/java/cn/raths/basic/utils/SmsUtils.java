package cn.raths.basic.utils;

import cn.raths.basic.constant.BaseConstants;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

public class SmsUtils {

    public static void SendMsg(String phone, String msg){
        try {
            HttpClient client = new HttpClient();
            PostMethod post = new PostMethod("https://utf8api.smschinese.cn/");
            post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");//在头文件中设置转码
            NameValuePair[] data ={ new NameValuePair("Uid", BaseConstants.SmsConstant.SMS_UID),
                    new NameValuePair("Key", BaseConstants.SmsConstant.SMS_KEY),
                    new NameValuePair("smsMob",phone),
                    new NameValuePair("smsText",msg)};
            post.setRequestBody(data);

            client.executeMethod(post);
            Header[] headers = post.getResponseHeaders();
            int statusCode = post.getStatusCode();
            System.out.println("statusCode:"+statusCode); //HTTP状态码
            for(Header h : headers){
                System.out.println(h.toString());
            }
            String result = new String(post.getResponseBodyAsString().getBytes("utf-8"));
            System.out.println(result);  //打印返回消息状态

            post.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
