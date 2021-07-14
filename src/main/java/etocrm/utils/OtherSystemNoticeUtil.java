package etocrm.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.exception.MyException;
import org.etocrm.entity.*;
import org.etocrm.enums.OtherSystemUrlTypeEnum;
import org.etocrm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author xingxing.xie
 * @Date 2021/6/23 14:34
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class OtherSystemNoticeUtil {


    @Autowired
    private OrgSystemRelationRepository orgSystemRelationRepository;

    @Autowired
    private SystemMachineRelationRepository systemMachineRelationRepository;

    @Autowired
    private OtherSystemInfoRepository otherSystemInfoRepository;

    @Autowired
    private SystemInfoRepository systemInfoRepository;
    @Autowired
    private SysOrgRepository sysOrgRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static String[] filterSystemCodes = {"ETOCRM", "ETOSHOP", "ETODG"};


    public void restTemplate(Long orgId, Long machineId, String dataJsonStr, OtherSystemUrlTypeEnum otherSystemUrlTypeEnum) throws MyException {
        if(null==machineId){
            Optional<SysOrg> byId = sysOrgRepository.findById(orgId);
            if(!byId.isPresent()){

                throw new MyException(ResponseEnum.INCORRECT_PARAMS.getCode(), "该机构无任何机房信息！");
            }
            machineId = byId.get().getMachineId();

        }

        //根据orgId   查询org_system_relation
        List<OrgSystemRelation> allByOrgId = orgSystemRelationRepository.findAllByOrgId(orgId);
        if (CollectionUtil.isEmpty(allByOrgId)) {
            log.error("该机构暂未购买任何系统！org：{},machineId：{}", orgId, machineId);
            log.error("该机构暂未购买任何系统！下发通知暂停。");
            throw new MyException(ResponseEnum.INCORRECT_PARAMS.getCode(), "该机构暂未购买任何系统！");
        }
        //todo  暂时过滤
        List<Long> collect = systemInfoRepository.findByCodeIn(Arrays.asList(filterSystemCodes))
                .stream().map(SystemInfo::getId).collect(Collectors.toList());

        List<Long> systemIds = allByOrgId.stream().map(OrgSystemRelation::getSystemId)
                .filter(t -> collect.contains(t))
                .collect(Collectors.toList());

        //根据systemId   查询 system_machine_relation
        List<SystemMachineRelation> byMachineIdAndSystemIdIn = systemMachineRelationRepository.findByMachineIdAndSystemIdIn(machineId, systemIds);
        if (CollectionUtil.isEmpty(byMachineIdAndSystemIdIn)) {
            log.error("查询无数据，machineId：{}，systemIds：{}", machineId, systemIds.toString());
            throw new MyException(ResponseEnum.INCORRECT_PARAMS.getCode(), "该机房下无对应系统");
        } else {
            List<Long> otherSysIds = byMachineIdAndSystemIdIn.stream().map(SystemMachineRelation::getOtherSystemId).collect(Collectors.toList());
            List<OtherSystemInfo> byUrlTypeAndIdIn = otherSystemInfoRepository.findByUrlTypeAndIdIn(otherSystemUrlTypeEnum.getCode(), otherSysIds);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/json");
            HttpEntity<String> httpEntity = new HttpEntity<>(dataJsonStr, httpHeaders);
            for (OtherSystemInfo otherSystemInfo : byUrlTypeAndIdIn) {
                String url = otherSystemInfo.getUrlDetail();
                if (!StringUtils.isEmpty(url)) {
                    log.error("系统调用地址：" + otherSystemInfo.getUrlDetail() + " 开户参数：" + dataJsonStr);

                    if(!url.startsWith("http")){
                        continue;
                    }
                    ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, JSONObject.class);
                    log.error("通知返回实体类" + responseEntity);
                    if (responseEntity.getStatusCodeValue() == 200) {
                        JSONObject body = responseEntity.getBody();
                        Integer code =  null==body.get("code")?1:(Integer) body.get("code");
                        if (code != 0) {
                            log.error("下发参数失败={}，路径={}，参数={}", responseEntity, otherSystemInfo.getUrlDetail(), dataJsonStr);
                            throw new MyException(ResponseEnum.FAILD.getCode(), "下发失败！" + body.get("message"));
                        }
                    } else {
                        log.error("下发参数失败={}，路径={}，参数={}", responseEntity, otherSystemInfo.getUrlDetail(), dataJsonStr);
                        throw new MyException(ResponseEnum.FAILD.getCode(), "下发失败！");
                    }
                }
            }
        }
    }

}
