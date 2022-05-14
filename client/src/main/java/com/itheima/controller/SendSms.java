package com.itheima.controller;

import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.*;
import org.springframework.stereotype.Component;


/**
 * @author tang
 * @date 2022/5/13 15:33
 */
@Component
public class SendSms {

    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId     1
     * @param accessKeySecret 1
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId("LTAI5tGSxUs7U11LDjKM8BBT")
                // 您的AccessKey Secret
                .setAccessKeySecret("jVdowa57XnQVj8gUMUw48icmVjmfjU");
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }


    public void msg(String phone, String code) throws Exception {
        com.aliyun.dysmsapi20170525.Client client = SendSms.createClient("accessKeyId", "accessKeySecret");
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("阿里云短信测试")
                .setTemplateCode("SMS_154950909")
                .setPhoneNumbers(phone)
                .setTemplateParam("{\"code\":\"" + code + "\"}");
        // 复制代码运行请自行打印 API 的返回值
        client.sendSms(sendSmsRequest);
    }


}
