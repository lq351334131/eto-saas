package etocrm.model.delivery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.etocrm.database.annotation.QueryFileds;
import org.etocrm.database.enums.QueryType;
import org.etocrm.database.util.PageVO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class DeliveryPageVO extends PageVO implements Serializable {

    @ApiModelProperty(value = "ID")
    @NotNull(message = "ID不可为空")
    @QueryFileds(type = QueryType.EQUAL, field = "orderId")
    private Long id;
}
