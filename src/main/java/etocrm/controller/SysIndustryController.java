package etocrm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.util.ResponseVO;
import org.etocrm.model.industry.SysIndustryVO;
import org.etocrm.service.SysIndustryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author admin
 * @since 2021-01-19
 */
@Slf4j
@RestController
@RequestMapping("/sysIndustry")
@Api(value = "行业服务", tags = "行业服务")
public class SysIndustryController {

    @Resource
    private SysIndustryService sysIndustryService;

    @ApiOperation(value = "获取全部行业-不分页", notes = "获取全部行业-不分页")
    @GetMapping("/findAll")
    public ResponseVO<List<SysIndustryVO>> findAll(SysIndustryVO sysIndustry) {
        List<SysIndustryVO> sysIndustrys = sysIndustryService.findAll(sysIndustry);
        return ResponseVO.success(sysIndustrys);
    }

}