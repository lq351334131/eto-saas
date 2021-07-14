package etocrm.service.systemservice;

import org.etocrm.entity.UserRole;
import org.etocrm.model.AccountIdentityVo;

import java.util.List;
import java.util.Map;

/**
 * uam  各个系统对接service
 * @Author xingxing.xie
 * @Date 2021/6/18 10:44
 */
public interface SystemTypeService {

    /**
     *  根据不同系统   将对应参数 封装 进result 对象
     * @param result
     * @param userRoleList
     */
    void getResultBySystem(AccountIdentityVo result, List<UserRole> userRoleList, Map<Long, String> roleNameMap);
}
