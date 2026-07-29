package com.duzheStudy.common.utils;

import com.aliyun.oss.OSS;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取视频首帧图片用于界面展示
 *
 * @author LunarYouI
 * 基本信息描述：https://www.cnblogs.com/liangjingfu/p/12858018.html
 *
 * Input #0, mov,mp4,m4a,3gp,3g2,mj2, from 'F:\temporary\123456789.mp4':
 *   Metadata:
 *     major_brand     : isom
 *     minor_version   : 512
 *     compatible_brands: isomiso2avc1mp41
 *     encoder         : Lavf58.12.100
 *     description     : Tencent SID MTS
 *   Duration: 00:04:13.21, start: 0.000000, bitrate: 3167 kb/s
 *     Stream #0:0(und): Video: h264 (High) (avc1 / 0x31637661), yuv420p, 1280x720 [SAR 1:1 DAR 16:9], 3033 kb/s, 24 fps, 24 tbr, 90k tbn, 48 tbc (default)
 *     Metadata:
 *       handler_name    : VideoHandler
 *     Stream #0:1(und): Audio: aac (LC) (mp4a / 0x6134706D), 44100 Hz, stereo, fltp, 128 kb/s (default)
 *     Metadata:
 *       handler_name    : SoundHandler
 *
 *
 * @create 2022-06-13 9:41
 */
public class FFmpegUtil {

    public static Long getVideoTime(String filePath) throws Exception {
        FFmpegFrameGrabber grabberOne = FFmpegFrameGrabber.createDefault(filePath);
        grabberOne.start();
        // 计算时长
        long duration = grabberOne.getFormatContext().duration() / 1000;
        return duration;
    }
    /**
     *
     * @Title: getTempPath
     * @Description: 生成视频的首帧图片方法
     * @author: Zing
     * @param: @param tempPath 生成首帧图片的文件地址
     * @param: @param filePath 传进来的线上文件
     * @param: @return
     * @param: @throws Exception
     * @return: boolean
     * @throws
     */
    public static boolean getCoverImagePath(String coverImagePath, String filePath) throws Exception {
        File targetFile = new File(coverImagePath);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        File file2 = new File(filePath);
        System.out.println("确认文件是否是视频...");
        //判断文件是否为视频
        if(FFmpegUtil.isVideo(filePath)) {
            System.out.println("确认成功！");
            //判断文件是否存在
            if (file2.getParentFile().exists()) {
                FFmpegFrameGrabber ff = new FFmpegFrameGrabber(file2);
                ff.start();
                int ftp = ff.getLengthInFrames();
                int flag=0;
                Frame frame = null;
                while (flag <= ftp) {
                    //获取帧
                    frame = ff.grabImage();
                    //过滤前3帧，避免出现全黑图片
                    if ((flag>3)&&(frame != null)) {
                        break;
                    }
                    flag++;
                }
                if(ImageIO.write(FrameToBufferedImage(frame), "jpg", targetFile)) {
                    ff.close();
                    ff.stop();
                    System.out.println("输出图片成功！");
                    return true;
                }else {
                    ff.close();
                    ff.stop();
                    System.out.println("输出图片失败！");
                    return false;
                }
            }else{
                System.out.println("路径内容错误！"+file2.getParentFile());
                return false;
            }
        }else{
            System.out.println("确认失败！");
            return false;
        }
    }

    /***
     *
     * @Title: FrameToBufferedImage
     * @Description: 创建格式化BufferedImage对象
     * @author: Zing
     * @param: @param frame
     * @param: @return
     * @return: RenderedImage
     * @throws
     */
    private static RenderedImage FrameToBufferedImage(Frame frame) {
        //创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }

    /**
     *
     * @Title: isVideo
     * @Description:判断是不是视频
     * @author: Zing
     * @param: @param path 文件路径
     * @param: @return
     * @return: boolean       true是视频 false非视频
     * @throws
     */
    public static boolean isVideo(String path) {
        //设置视频后缀
        List<String> typeList = new ArrayList<String>();
        typeList.add("mp4");
        typeList.add("flv");
        typeList.add("avi");
        typeList.add("rmvb");
        typeList.add("rm");
        typeList.add("wmv");
        typeList.add("m3u8");
        //获取文件名和后缀
        String suffix = path.substring(path.lastIndexOf(".") + 1);
        for(String type : typeList) {
            //统一为大写作比较
            if(type.toUpperCase().equals(suffix.toUpperCase())) {
                return true;
            }
        }
        return false;
    }


}

