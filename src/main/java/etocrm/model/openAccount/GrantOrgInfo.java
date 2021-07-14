package etocrm.model.openAccount;

import lombok.Data;

/**
 * @Author: dkx
 * @Date: 16:55 2021/6/23
 * @Desc:
 */
@Data
public class GrantOrgInfo {

    private Long uamOrgId;

    private Long crmOrgId;

    private Long industryId;

    private String industryName;

    private Long machineId;

    private String machineName;

    private String machineCode;

    private String name;

    private String orgLogoUrl;
}
