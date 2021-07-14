package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.UserWechatInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 微信用户表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Repository
public interface UserWechatInfoRepository extends BaseRepository<UserWechatInfo, Long>{

    List<UserWechatInfo> findByUserId(Long userId);

    UserWechatInfo findByUserIdAndOpenid(Long userId, String openId);

    List<UserWechatInfo> findByOpenid(String openId);
}