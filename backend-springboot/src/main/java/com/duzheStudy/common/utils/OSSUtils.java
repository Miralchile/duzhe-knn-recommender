package com.duzheStudy.common.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Calendar;

public class OSSUtils {

    //定义公开静态常量
    public static String END_POIND = System.getenv().getOrDefault("OSS_ENDPOINT", "oss-rg-china-mainland.aliyuncs.com");
    public static String ACCESS_KEY_ID = System.getenv().getOrDefault("OSS_ACCESS_KEY_ID", "replace-with-your-access-key-id");
    public static String ACCESS_KEY_SECRET = System.getenv().getOrDefault("OSS_ACCESS_KEY_SECRET", "replace-with-your-access-key-secret");
    public static String BUCKET_NAME = System.getenv().getOrDefault("OSS_BUCKET_NAME", "duzhework");
    public static String BUCKET_URL = System.getenv().getOrDefault("OSS_BUCKET_URL", "duzhework.oss-rg-china-mainland.aliyuncs.com");
    public static String VIDEO_DIR = "video";
    public static String IMAGE_DIR = "image";
    public static String COVER_IMAGE_DIR = "coverImage";

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
    public static String uploadImage(MultipartFile file) throws IOException {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 注意月份是从0开始计数的
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String folder = "/" + year + "-" + month + "-" + day;

        byte[] uploadBytes = file.getBytes();
        String md5Hex = DigestUtils.md5Hex(uploadBytes);
//        String filename = file.getOriginalFilename();
        String newFileName = IMAGE_DIR + folder + "/" + md5Hex + ".png";
        OSS ossClient = getOSSClient();
        String imagePath = null;
        try {
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(BUCKET_NAME, newFileName, inputStream);
            imagePath = "https://" + BUCKET_URL + "/"+  newFileName;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭OSS对象
            ossClient.shutdown();
        }
        return imagePath;
    }


    /**
     * 上传封面图文件
     *
     * @param file 文件
     * @return 返回图片上传路径
     */
    public static String uploadCoverImage(MultipartFile file) throws IOException {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 注意月份是从0开始计数的
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String folder = "/" + year + "-" + month + "-" + day;

        byte[] uploadBytes = file.getBytes();
        String md5Hex = DigestUtils.md5Hex(uploadBytes);
        String newFileName = IMAGE_DIR + folder + "/" + md5Hex  + ".png";
        OSS ossClient = getOSSClient();
        String imagePath = null;
        try {
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(BUCKET_NAME, newFileName, inputStream);
            imagePath = "https://" + BUCKET_URL + "/"+  newFileName;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭OSS对象
            ossClient.shutdown();
        }
        return imagePath;
    }

    /**
     * 上传视频文件
     *
     * @param file 文件
     * @return 返回图片上传路径
     */
    public static String uploadVideo(MultipartFile file) throws IOException {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 注意月份是从0开始计数的
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String folder = "/" + year + "-" + month + "-" + day;

        byte[] uploadBytes = file.getBytes();
        String md5Hex = DigestUtils.md5Hex(uploadBytes);
        String newFileName = VIDEO_DIR  + folder +  "/" + md5Hex + ".m3u8";
        OSS ossClient = getOSSClient();
        String imagePath = null;
        try {
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(BUCKET_NAME, newFileName, inputStream);
            imagePath = "https://" + BUCKET_URL + "/"+  newFileName;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭OSS对象
            ossClient.shutdown();
        }
        return imagePath;
    }

//    public static List<String> uploadArrayFile(List<MultipartFile> files) {
//        // 创建OSSClient实例。
//        List<String> list = new ArrayList<>();
//        OSS ossClient = getOSSClient();
//        try {
//            //设置文件名
//            for (MultipartFile file : files) {
//                String filename = file.getOriginalFilename();
//                String newFileName = PARENT_DIR + UUID.randomUUID() + filename;
//                InputStream inputStream = file.getInputStream();
//                // 创建PutObject请求。
//                ossClient.putObject(BUCKET_NAME, newFileName, inputStream);
//
//                String url = "https://" + BUCKET_URL + "/"+  newFileName;
////                System.out.println(url);
//                list.add(url);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//        return list;
//
//    }

    /**
     * 删除图片文件
     */
    public static void delete(String objectName) {
        OSS ossClient = new OSSClientBuilder().build(END_POIND, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        ossClient.deleteObject(BUCKET_NAME,objectName.replace(BUCKET_URL,""));
        ossClient.shutdown();
    }

}
