package com.itheima.controller;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.itheima.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author 汤
 */
@RestController
@RequestMapping("/common")
public class OssTemplate {

    @Value("${rj1-1.oss.accessKey}")
    private String accessKeyId;

    @Value("${rj1-1.oss.secret}")
    private String accessKeySecret;

    @Value("${rj1-1.oss.bucketName}")
    private String bucketName;

    @Value("${rj1-1.oss.url}")
    private String url;

    @Value("${rj1-1.oss.endpoint}")
    private String endpoint;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {

        String contentType = file.getContentType();
        if (StrUtil.isEmpty(contentType)) {
            return R.error("文件缺失");
        }

        // 文件存储路径
        // 组成部分1：年月日/ 命名图片存储的文件夹
        // 组成部分2：UUID/ 不重复，有顺序 编号生成规则
        // 组成部分3：文件后缀名 
        InputStream inputStream = file.getInputStream();
        String filename = new SimpleDateFormat("yyyy/MM/dd").format(new Date())
                + "/" + UUID.randomUUID().toString() + "." + contentType.split("/")[1];

        // OSS核心代码
        // build方法（参数1，参数2，参数3）
        // 参数1：endpoint：oss-cn-beijing.aliyuncs.com
        // 参数2：根据上述教程获取（账号）
        // 参数3：根据上述教程获取（密码）
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // putObject(参数1，参数2，参数3）
        // 参数1：bucketName（取自bucket域名的第一段，类似：tom-tanhua）
        // 参数2：filename，上传到oss上后的存储路径
        // 参数3：文件内容（字节输入流）
        ossClient.putObject(bucketName, filename, inputStream);

        // 关闭（结束）
        ossClient.shutdown();
        // OSS核心代码

        // 图片在aliyun上的存储规则是：域名 + 文件路径
        String ossUrl = url + "/" + filename;
        return R.success(ossUrl);
    }

}