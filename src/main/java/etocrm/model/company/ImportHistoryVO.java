package etocrm.model.company;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ImportHistoryVO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "导入批次")
    private String importBatch;

    @ApiModelProperty(value = "成功数量")
    private Integer successNumber;

    @ApiModelProperty(value = "失败数量")
    private Integer failNumber;

    @ApiModelProperty(value = "错误文件url")
    private String errorFileUrl;
}
