package com.duzheStudy.modules.common.content;

import com.alibaba.fastjson2.JSON;
import com.aliyun.imageaudit20191230.Client;
import com.aliyun.imageaudit20191230.models.*;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class ScanImage {
    private static final String accessKeyId = System.getenv().getOrDefault("ALIYUN_IMAGE_AUDIT_ACCESS_KEY_ID", "replace-with-your-access-key-id");
    private static final String accessKeySecret = System.getenv().getOrDefault("ALIYUN_IMAGE_AUDIT_ACCESS_KEY_SECRET", "replace-with-your-access-key-secret");

    @PostMapping("/scanImage")
    public String scanImage(@RequestBody HashMap<String,String> reqMap) throws Exception {
        // 获取待检测的图片
        String image = reqMap.get("image");
        System.out.println("image="+image);

        // 返回结果的变量
        Map<String,String> resMap = new HashMap<>();

        //实例化客户端
        Config config = new Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        config.endpoint = "imageaudit.cn-shanghai.aliyuncs.com";
        Client client = new Client(config);


        // 设置待检测内容
        ScanImageRequest.ScanImageRequestTask task0 = new ScanImageRequest.ScanImageRequestTask().setImageURL(image);

        // 封装检测请求
        /**
         * porn：图片智能鉴黄
         * terrorism：图片敏感内容识别、图片风险人物识别
         * ad：图片垃圾广告识别
         * live：图片不良场景识别
         * logo：图片Logo识别
         */
        ScanImageRequest scanImageRequest = new ScanImageRequest()
                .setTask(Arrays.asList(
                        task0
                ))
                .setScene(Arrays.asList(
                        "porn","terrorism","live"

                ));

        RuntimeOptions runtime = new RuntimeOptions();

        // 调用API获取检测结果
        ScanImageResponse response =  client.scanImageWithOptions(scanImageRequest, runtime);
        resMap.put("data", JSON.toJSONString(response.getBody().getData().getResults().get(0)));


        // 检测结果解析
        try {
            List<ScanImageResponseBody.ScanImageResponseBodyDataResultsSubResults> responseSubResults = response.getBody().getData().getResults().get(0).getSubResults();

            for(ScanImageResponseBody.ScanImageResponseBodyDataResultsSubResults responseSubResult : responseSubResults){
                if(responseSubResult.getSuggestion()!="pass"){
                    resMap.put("state",responseSubResult.getSuggestion());

                    String msg = "";
                    switch (responseSubResult.getLabel()){
                        case "porn":
                            msg = "图片智能鉴黄未通过";
                            break;
                        case "terrorism":
                            msg = "图片敏感内容识别、图片风险人物识别未通过";
                            break;
                        case "ad":
                            msg = "图片垃圾广告识别未通过";
                            break;
                        case "live":
                            msg = "图片不良场景识别未通过";
                            break;
                        case "logo":
                            msg = "图片Logo识别未通过";
                            break;
                    }

                    return JSON.toJSONString(resMap);
                }

            }

        } catch (Exception error) {
            resMap.put("state","review");
            resMap.put("msg","发生错误，详情:"+error);
            return  JSON.toJSONString(resMap);
        }
        resMap.put("state","pass");
        return  JSON.toJSONString(resMap);
    }
}
