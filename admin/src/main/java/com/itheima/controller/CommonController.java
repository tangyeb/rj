// package com.itheima.controller;
//
// /**
//  * @author tang
//  * @date 2022/5/10 0:00
//  */
//
// import cn.hutool.core.util.StrUtil;
// import com.itheima.common.R;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;
//
// import javax.servlet.ServletOutputStream;
// import javax.servlet.http.HttpServletResponse;
// import java.io.*;
// import java.text.SimpleDateFormat;
// import java.util.Date;
// import java.util.UUID;
//
// @RestController
// @RequestMapping("/common")
// public class CommonController {
//     //todo:待修改
//
//     @Value("${reggie.path}")
//     private String basePath;
//
//     @PostMapping("/upload")
//     public R<String> upload(MultipartFile file) throws IOException{
//
//         String contentType = file.getContentType();
//         if(StrUtil.isEmpty(contentType)){
//             return R.error("文件缺失");
//         }
//         InputStream inputStream = file.getInputStream();
//         String filename = new SimpleDateFormat("yyyy/MM/dd").format(new Date())
//                 + "/" + UUID.randomUUID().toString() + "." + contentType.split("/")[1];
//
//         String originalFilename = file.getOriginalFilename();
//         String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
//         String fileName = UUID.randomUUID().toString() + substring;
//         File dir = new File(basePath);
//
//         if (!dir.exists()) {
//             dir.mkdirs();
//         }
//
//         try {
//             file.transferTo(new File(basePath + fileName));
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//         return R.success(fileName);
//     }
//
//     @GetMapping("/download")
//     public void download(String name, HttpServletResponse response) {
//         try {
//             FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
//             ServletOutputStream outputStream = response.getOutputStream();
//
//             response.setContentType("image/jpeg");
//
//             int len = 0;
//             byte[] bytes = new byte[1024];
//             while ((len = fileInputStream.read(bytes)) != -1) {
//                 outputStream.write(bytes, 0, len);
//                 outputStream.flush();
//             }
//             outputStream.close();
//             fileInputStream.close();
//         } catch (FileNotFoundException e) {
//             e.printStackTrace();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }