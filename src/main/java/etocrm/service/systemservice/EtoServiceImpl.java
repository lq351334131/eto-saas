package etocrm.service.systemservice;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.entity.BrandsWechatInfo;
import org.etocrm.entity.UserRole;
import org.etocrm.enums.WeChatAppTypeEnum;
import org.etocrm.model.AccountIdentityVo;
import org.etocrm.model.IdentityDetail;
import org.etocrm.model.MiniAppRoleListVO;
import org.etocrm.repository.BrandsWechatInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ETOMINIAPP 小程序
 * @Author xingxing.xie
 * @Date 2021/6/18 10:58
 */
@Component(value = "ETOSERVICE")
@Slf4j
public class EtoServiceImpl implements SystemTypeService {
    @Autowired
    private BrandsWechatInfoRepository repository;

    @Override
    public void getResultBySystem(AccountIdentityVo result, List<UserRole> userRoleList, Map<Long, String> roleNameMap) {

        List<IdentityDetail> identityDetails = new ArrayList<>();
        for (UserRole userRole : userRoleList) {
            //一个账号角色下  多条 小程序
            List<MiniAppRoleListVO> miniAppList=new ArrayList<>();
            IdentityDetail identityDetail = new IdentityDetail()
                    .setRoleId(userRole.getRoleId())
                    .setRoleName(roleNameMap.get(userRole.getRoleId()));

                String weChatInfoIdStr = userRole.getWechatInfoId();
            if (StrUtil.isNotEmpty(weChatInfoIdStr)) {
                String[] split = weChatInfoIdStr.split(",");
                List<Long> weChatInfoIds = Arrays.asList(split).stream().map(t -> Long.valueOf(t)).collect(Collectors.toList());
                List<BrandsWechatInfo> weChatInfos = repository.findByTypeAndIdIn(WeChatAppTypeEnum.SERVICE.getCode(), weChatInfoIds);
                if (CollUtil.isEmpty(weChatInfos)) {
                    continue;
                }
                for (BrandsWechatInfo weChatInfo : weChatInfos) {
                    MiniAppRoleListVO miniAppRoleListVO = new MiniAppRoleListVO()
                            .setId(weChatInfo.getId())
                            //设置appId
                            .setAppId(weChatInfo.getAppid())
                            .setName(weChatInfo.getWechatName())
                            .setStatus(weChatInfo.getStatus())
                            .setRoleName(roleNameMap.get(userRole.getRoleId()))
                            .setRoleId(userRole.getRoleId());
                    miniAppList.add(miniAppRoleListVO);
                }
            }
            //设置  公众号
            identityDetail.setSubscriptionList(miniAppList);
            identityDetails.add(identityDetail);
        }
        result.setIdentityDetails(identityDetails);

    }
}
