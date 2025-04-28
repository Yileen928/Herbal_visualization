package cn.herbal.visualization.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
public class PredictController {

    private static final Logger logger = LoggerFactory.getLogger(PredictController.class);
    private static final String PYTHON_SERVICE_URL = "http://121.4.116.71:5000/predict";

    @PostMapping("/predict")
    public Object predict(@RequestParam("file") MultipartFile file) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            // 发送POST请求到远程Flask服务
            String response = restTemplate.postForObject(PYTHON_SERVICE_URL, requestEntity, String.class);
            logger.info("响应: {}", response);
            return response;
        } catch (Exception e) {
            logger.error("预测过程中发生错误", e);
            throw new RuntimeException(e);
        }
    }
}