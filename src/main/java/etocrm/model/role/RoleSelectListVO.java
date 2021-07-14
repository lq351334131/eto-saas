package etocrm.model.role;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Date;

@Data
public class RoleSelectListVO implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "关联员工数量")
    private Integer empCount;

    @ApiModelProperty(value = "状态1代表启用，0代表禁用")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "管理系统的类型")
    private String systemCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "最后操作时间")
    private Date updatedTime;
    @ApiModelProperty("最后操作人")
    private String updateUser;

}
