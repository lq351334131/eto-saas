package etocrm.model.company;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.database.util.PageVO;

import java.io.Serializable;

@Data
public class SysCompanyPageListVO extends PageVO implements Serializable {

    @ApiModelProperty("是否是线上店铺")
    private Integer openShop;
    @ApiModelProperty("状态")
    private Integer status;
}
