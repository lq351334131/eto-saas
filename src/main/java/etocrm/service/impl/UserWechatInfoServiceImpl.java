package etocrm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.util.BasePage;
import org.etocrm.database.util.PageBounds;
import org.etocrm.database.util.QueryConditionUtil;
import org.etocrm.entity.UserWechatInfo;
import org.etocrm.repository.UserWechatInfoRepository;
import org.etocrm.service.UserWechatInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 用户详情信息 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-22
 */
@Service
@Slf4j
public class UserWechatInfoServiceImpl implements UserWechatInfoService {

    @Resource
    private UserWechatInfoRepository userDetailsRepository;


    @Override
    public Long saveUserDetails(UserWechatInfo userDetails) {
        // todo 做你想做的事
        userDetailsRepository.save(userDetails);
        return userDetails.getId();
    }

    @Override
    public Long updateByPk(UserWechatInfo userDetails) {
        // todo 做你想做的事
        userDetailsRepository.save(userDetails);
        return userDetails.getId();
    }

    @Override
    public void deleteByPk(Long pk) {
        // todo 做你想做的事
        userDetailsRepository.logicDelete(pk);
    }

    @Override
    public UserWechatInfo detailByPk(Long pk) {
        // todo 做你想做的事
        Optional<UserWechatInfo> byId = userDetailsRepository.findById(pk);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    @Override
    public BasePage<UserWechatInfo> list(Integer curPage, Integer size, UserWechatInfo userDetails) {
        // todo 做你想做的事
        PageBounds pageBounds = new PageBounds(curPage, size);
        Page<UserWechatInfo> userDetailss = userDetailsRepository.findAll((r, q, c) -> {
            return (new QueryConditionUtil()).where(userDetails, r, c);
        }, PageRequest.of(pageBounds.getOffset(), pageBounds.getLimit()));
        return new BasePage(userDetailss);
    }

    @Override
    public List<UserWechatInfo> findAll(UserWechatInfo userDetails) {
        // todo 做你想做的事
        return userDetailsRepository.findAll((r, q, c) -> {
            return (new QueryConditionUtil()).where(userDetails, r, c);
        });
    }
}
