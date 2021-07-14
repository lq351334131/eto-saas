package etocrm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.ResponseVO;
import org.etocrm.model.org.*;
import org.etocrm.service.SysOrgService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author admin
 * @since 2021-01-19
 */
@Slf4j
@RestController
@RequestMapping("/sysOrg")
@Api(value = "中台机构服务", tags = "中台机构服务-凯旋")
public class SysOrgController {

    @Resource
    private SysOrgService sysOrgService;

    @ApiOperation(value = "修改机构", notes = "修改机构")
    @PostMapping("/update")
    public ResponseVO update(@Valid @RequestBody SysOrgDetailVO sysOrg) throws MyException {
        Long aLong = sysOrgService.updateByPk(sysOrg);
        return ResponseVO.success(aLong);
    }


    @ApiOperation(value = "机构详情", notes = "机构详情")
    @PostMapping(value = "/detail")
    public ResponseVO<SysOrgDetailVO> detail() throws MyException {
        SysOrgDetailVO sysOrg = sysOrgService.detailByPk();
        return ResponseVO.success(sysOrg);
    }

    @ApiOperation(value = "机构详情信息", notes = "机构详情信息")
    @PostMapping(value = "/detailInfo")
    public ResponseVO<SysOrgDetailInfo> detailInfo()  {
        SysOrgDetailInfo sysOrg = sysOrgService.detailInfo();
        return ResponseVO.success(sysOrg);
    }

}