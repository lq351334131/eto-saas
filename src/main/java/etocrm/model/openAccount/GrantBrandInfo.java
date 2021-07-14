package etocrm.model.openAccount;

import lombok.Data;

/**
 * @Author: dkx
 * @Date: 16:55 2021/6/23
 * @Desc:
 */
@Data
public class GrantBrandInfo {

    private Long uamBrandId;

    private Long crmBrandId;

    private String name;

    private int status;

    private Long uamOrgId;

    private Long crmOrgId;
}
