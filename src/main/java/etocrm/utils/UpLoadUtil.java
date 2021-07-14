package etocrm.utils;

import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Service
public class UpLoadUtil {

    @Value("${cdn.file.url}")
    private String cdnUrl;

    @Value("${cdn.file.AccessKey}")
    private String accessKey;

    @Value("${cdn.file.SecretyKey}")
    private String secretyKey;

    @Value("${cdn.file.Idcinfo}")
    private String Idcinfo;

    @Value("${cdn.file.Os}")
    private String Os;

    @Value("${cdn.file.OrgId}")
    private String orgId;

    @Value("${cdn.file.brandsId}")
    private String brandsId;

    @Resource
    RestTemplate restTemplate;

    public JSONObject getCdnPath(MultipartFile file) {
        if (file != null && file.getOriginalFilename() != null && file.getSize() > 0) {
            JSONObject jsonObject = getUpLoadJson(file);
            if (jsonObject != null) return jsonObject;

        } else {
            log.info("上传文件为空！");
        }
        return null;
    }

    private JSONObject getUpLoadJson(MultipartFile file) {
        //todo 获取机构和品牌id
        HttpHeaders headers = getHttpHeaders();

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        ByteArrayResource byteArrayResource = null;
        try {
            byteArrayResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        param.add("fileName", file.getOriginalFilename());
        param.add("file", byteArrayResource);
        HttpEntity httpEntity = new HttpEntity<>(param, headers);
        JSONObject jsonObject = restTemplate.postForObject(cdnUrl, httpEntity, JSONObject.class);
        if (jsonObject != null) {
            return jsonObject;
        }
        return null;
    }


    public String getSign(String time, String org, String brands, String id) {

        String checkParam = accessKey + secretyKey + time + org + brands + id+Os;
        HMac mac = new HMac(HmacAlgorithm.HmacMD5, secretyKey.getBytes());
        log.info(checkParam);
        String macHex1 = mac.digestHex(checkParam);
        log.info(macHex1);
        return macHex1;
    }
    public JSONObject  getUpLoadJsonByByte(String originalFileName,byte[] bytes){
        HttpHeaders headers = getHttpHeaders();

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        ByteArrayResource  byteArrayResource = new ByteArrayResource(bytes) {
                @Override
                public String getFilename() {
                    return originalFileName;
                }
            };
        param.add("fileName", originalFileName);
        param.add("file", byteArrayResource);
        if(originalFileName.contains("csv")) {
            param.add("keepFileNameStatus",true);
        }

        HttpEntity httpEntity = new HttpEntity<>(param, headers);
        JSONObject jsonObject = restTemplate.postForObject(cdnUrl, httpEntity, JSONObject.class);
        if (jsonObject != null) {
            return jsonObject;
        }
        return null;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);
        headers.add("AccessKey", accessKey);
        headers.add("Org", orgId);
        headers.add("Brands", brandsId);
        headers.add("Idcinfo", Idcinfo);
        headers.add("Os",Os);
        String time = DateUtil.formatDateTimeByFormat(new Date(), DateUtil.default_datetimeformat);
        headers.add("Sign", getSign(time, orgId, brandsId, Idcinfo));
        headers.add("DateTime", time);
        return headers;
    }

}
