package etocrm.model.openAccount;

import lombok.Data;

/**
 * @Author: dkx
 * @Date: 16:55 2021/6/23
 * @Desc:
 */
@Data
public class GrantAdminUserInfo {

    private Long userId;

    private String loginUid;

    private String name;

    private int isAdmin;

    private int status;

    private Long uamOrgId;

    private Long crmOrgId;

    private String contactsPhone;

    private String password;

    private String avatar;
}
