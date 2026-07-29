package com.duzheStudy.modules.common.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class OSSUtils implements InitializingBean {

    @Value("${oss.endpoint}")
    private  String endpoint;

    @Value("${oss.AccessKeyId}")
    private  String accessKeyId;

    @Value("${oss.AccessKeySecret}")
    private  String accessKeySecret;

    @Value("${oss.bucketName}")
    private  String bucketName;

    @Value("${oss.sufferUrl}")
    private  String sufferUrl;

    @Value("${oss.parentDir}")
    private  String parentDir;

    //定义公开静态常量
    public static String END_POIND;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;
    public static String SUFFER_URL;
    public static String PARENT_DIR;
    public static String VIDEO_PARENT_DIR="videos/";

    @Override
    public void afterPropertiesSet() {
        END_POIND = endpoint;
        ACCESS_KEY_ID = accessKeyId;
        ACCESS_KEY_SECRET = accessKeySecret;
        BUCKET_NAME = bucketName;
        SUFFER_URL = sufferUrl;
        PARENT_DIR = parentDir;
    }
    /**
     * 获取OSS对象
     *
     * @return 返回OSS对象
     */
    public static OSS getOSSClient() {
        OSS ossClient = new OSSClientBuilder().build(END_POIND, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        // 验证仓库是否存在
        if (!ossClient.doesBucketExist(BUCKET_NAME)) {
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(BUCKET_NAME);
            // 设置成可读写的
            createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
            // 创建
            ossClient.createBucket(createBucketRequest);
        }
        return ossClient;
    }

    /**
     * 上传图片文件
     *
     * @param file 文件
     * @return 返回图片上传路径
     */
    public static String uploadImage(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String newFileName = PARENT_DIR + UUID.randomUUID() + filename;
        OSS ossClient = getOSSClient();
        String imagePath = "";
        try {
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(BUCKET_NAME, newFileName, inputStream);
            imagePath = "https://" + SUFFER_URL + "/"+  newFileName;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭OSS对象
            ossClient.shutdown();
        }
        return imagePath;
    }

    /**
     * 上传图片文件
     *
     * @param file 文件
     * @return 返回图片上传路径
     */
    public static String uploadVideo(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String newFileName = VIDEO_PARENT_DIR + UUID.randomUUID() + filename;
        OSS ossClient = getOSSClient();
        String videoPath = "";
        try {
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(BUCKET_NAME, newFileName, inputStream);
            videoPath = "https://" + SUFFER_URL + "/"+  newFileName;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭OSS对象
            ossClient.shutdown();
        }
        return videoPath;
    }

    public static List<String> uploadArrayFile(List<MultipartFile> files) {
        // 创建OSSClient实例。
        List<String> list = new ArrayList<>();
        OSS ossClient = getOSSClient();
        try {
            //设置文件名
            for (MultipartFile file : files) {
                String filename = file.getOriginalFilename();
                String newFileName = PARENT_DIR + UUID.randomUUID() + filename;
                InputStream inputStream = file.getInputStream();
                // 创建PutObject请求。
                ossClient.putObject(BUCKET_NAME, newFileName, inputStream);

                String url = "https://" + SUFFER_URL + "/"+  newFileName;
//                System.out.println(url);
                list.add(url);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return list;

    }

    /**
     * 删除图片文件
     */
    public static void delete(String objectName) {
        OSS ossClient = new OSSClientBuilder().build(END_POIND, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        ossClient.deleteObject(BUCKET_NAME,objectName.replace("https://reader-station.oss-cn-hangzhou.aliyuncs.com/images/",""));
        ossClient.shutdown();
    }

}
