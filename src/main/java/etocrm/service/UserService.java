package etocrm.service;

import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.BasePage;
import org.etocrm.entity.User;
import org.etocrm.model.UserVO;
import org.etocrm.model.user.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 人员信息 服务类
 * </p>
 * @author admin
 * @since 2021-01-22
 */
public interface UserService {


    /**
     * 添加
     */
    Long saveUser(UserSaveVO user);

    /**
     * 修改
     */
    Long updateByPk(UserUpdateVO user)throws MyException;

    /**
     * 删除
     */
    void deleteByPk(Long pk);

    /**
     * 详情
     */
    UserDetail detailByPk(Long pk);

    /**
     * 全查列表
     * @return
     */
    List<User> findAll(User user);

    /**
     * 分页查询
     */
    BasePage<UserSelectListVO> list(Integer curPage, Integer pageSize, UserSelectVO userSelectVO);

    LoginUserVO detailByUserName(LoginUser user);

    List<UserVO> getUserInfoByIds(Set<Long> userIds);

    List<UserVO> getUserInfoByOrgId(Long orgId);

    UserRoleVO getUserRole(Long userId, Long roleId);

    LoginCurrentVO getCurrentUserInfo(Long userId);

    Long updateStatus(Long id, Integer status)throws MyException;

    Long userRelationRole(UserRoleVO vo)throws MyException ;

    List<UserSystemVO> manageSystem();

    Integer dismissionWechatInfo(Long userId);

    Integer deleteUserRole(UserRoleRelationVO userRoleVO);

    List<UserSystemVO> roleManageSystem();

    Long updateUserInfo(UserVO userVO)throws MyException;

    UserDetail getCurrentUserDetail();

    List<UserLikeVO> userInfoLike(UserLikeVO userLikeVO);

    List<UserSystemVO> getCurrentLoginSystem();

    LoginUserVO  getCurrentLoginSystemAccessToken(String code);

    CurrentUserInfo getCurrentLoginUserInfo();

    void logout(HttpServletRequest request1);

    String sendVerifyCode(EmailParam emailParam)throws MyException ;

    Long verifyEmail(EmailParam emailParam)throws MyException ;

    Long resetPasswordByEmail(EmailPasswordParam emailPasswordParam)throws MyException ;

    Long updatePassword(UpdatePasswordParam updatePasswordParam)throws MyException;

    Long updateUserRole(UserRoleVO vo) throws MyException;
}