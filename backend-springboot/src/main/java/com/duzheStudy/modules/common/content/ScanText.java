package com.duzheStudy.modules.common.content;

import com.alibaba.fastjson2.JSON;
import com.aliyun.imageaudit20191230.Client;
import com.aliyun.imageaudit20191230.models.ScanTextRequest;
import com.aliyun.imageaudit20191230.models.ScanTextResponse;
import com.aliyun.imageaudit20191230.models.ScanTextResponseBody;
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
public class ScanText {
    private static final String accessKeyId = System.getenv().getOrDefault("ALIYUN_IMAGE_AUDIT_ACCESS_KEY_ID", "replace-with-your-access-key-id");
    private static final String accessKeySecret = System.getenv().getOrDefault("ALIYUN_IMAGE_AUDIT_ACCESS_KEY_SECRET", "replace-with-your-access-key-secret");

    @PostMapping("/scanText")
    public String scanText(@RequestBody HashMap<String,String> reqMap) throws Exception {
        // 获取待检测的文字
        String text = reqMap.get("text");
        System.out.println("text="+text);

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

        /**
         * spam：文字垃圾内容识别
         * politics：文字敏感内容识别
         * abuse：文字辱骂内容识别
         * terrorism：文字暴恐内容识别
         * porn：文字鉴黄内容识别
         * flood：文字灌水内容识别
         * contraband：文字违禁内容识别
         * ad：文字广告内容识别
         */
        // 设置待检测类型
        ScanTextRequest.ScanTextRequestLabels labels0 = new ScanTextRequest.ScanTextRequestLabels()
                .setLabel("politics");
        ScanTextRequest.ScanTextRequestLabels labels1 = new ScanTextRequest.ScanTextRequestLabels()
                .setLabel("contraband");
        ScanTextRequest.ScanTextRequestLabels labels2 = new ScanTextRequest.ScanTextRequestLabels()
                .setLabel("terrorism");
        ScanTextRequest.ScanTextRequestLabels labels3 = new ScanTextRequest.ScanTextRequestLabels()
                .setLabel("abuse");
        ScanTextRequest.ScanTextRequestLabels labels4 = new ScanTextRequest.ScanTextRequestLabels()
                .setLabel("spam");
        ScanTextRequest.ScanTextRequestLabels labels5 = new ScanTextRequest.ScanTextRequestLabels()
                .setLabel("ad");

        // 设置待检测内容
        ScanTextRequest.ScanTextRequestTasks tasks0 = new ScanTextRequest.ScanTextRequestTasks()
                .setContent(text);

        ScanTextRequest scanTextRequest = new ScanTextRequest()
                .setTasks(Arrays.asList(
                        tasks0
                ))
                .setLabels(Arrays.asList(
                        labels0,
                        labels1,
                        labels2,
                        labels3,
                        labels4,
                        labels5
                ));


        RuntimeOptions runtime = new RuntimeOptions();
        ScanTextResponse response = null;
        try {
            // 复制代码运行请自行打印 API 的返回值
            response = client.scanTextWithOptions(scanTextRequest, runtime);
            resMap.put("data", JSON.toJSONString(response.getBody().getData().getElements().get(0).getResults()));

            //调用后获取到他的返回对象，  然后判断我们的文字 是什么内容
            List<ScanTextResponseBody.ScanTextResponseBodyDataElementsResultsDetails> responseDetails = response.getBody().getData().getElements().get(0).getResults().get(0).getDetails();
            if (responseDetails.size()>0){
                resMap.put("state","block");

                StringBuilder error = new StringBuilder("检测到：");
                for (ScanTextResponseBody.ScanTextResponseBodyDataElementsResultsDetails detail : responseDetails) {
                    if ("abuse".equals(detail.getLabel()))  error.append("辱骂内容、");
                    if ("spam".equals(detail.getLabel()))  error.append("垃圾内容、");
                    if ("politics".equals(detail.getLabel()))  error.append("敏感内容、");
                    if ("terrorism".equals(detail.getLabel()))  error.append("暴恐内容、");
                    if ("porn".equals(detail.getLabel()))  error.append("黄色内容、");
                    if ("flood".equals(detail.getLabel()))  error.append("灌水内容、");
                    if ("contraband".equals(detail.getLabel())) error.append("违禁内容、");
                    if ("ad".equals(detail.getLabel()))  error.append("广告内容、");
                }
                resMap.put("msg",error.toString());
                return  JSON.toJSONString(resMap);

            }else {
                resMap.put("state","pass");
                resMap.put("msg","未检测出违规!");
                return  JSON.toJSONString(resMap);
            }
        }  catch (Exception _error) {
            resMap.put("state","review");
            resMap.put("msg","阿里云无法进行判断，需要人工进行审核,错误详情:"+_error);
            return  JSON.toJSONString(resMap);
        }
    }

}
