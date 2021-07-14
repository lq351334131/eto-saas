package etocrm.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.BasePage;
import org.etocrm.database.util.ResponseVO;
import org.etocrm.entity.SysOrgBrands;
import org.etocrm.model.brands.*;
import org.etocrm.service.SysOrgBrandsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author admin
 * @since 2021-01-23
 */
@Slf4j
@ApiSort(400)
@RestController
@RequestMapping("/sysOrgBrands")
@Api(value = "中台机构品牌服务", tags = "中台机构品牌服务")
public class SysOrgBrandsController {

    @Resource
    private SysOrgBrandsService sysOrgBrandsService;


    @ApiOperation(value = "机构品牌分页列表", notes = "机构品牌分页列表")
    @GetMapping("/list")
    public ResponseVO<BasePage<SysOrgBrandsListVO>> list(@Valid SysOrgBrandsSelectVO sysOrgBrands) {
        BasePage<SysOrgBrandsListVO> list = sysOrgBrandsService.list(sysOrgBrands.getOffset(), sysOrgBrands.getLimit(), sysOrgBrands);
        return ResponseVO.success(list);
    }

    @ApiOperation(value = "新增机构品牌", notes = "新增机构品牌")
    @ApiOperationSupport(order = 10)
    @PostMapping("/save")
    public ResponseVO save(@Valid @RequestBody SysBrandsVO sysOrgBrands) throws MyException {
        sysOrgBrandsService.saveSysOrgBrands(sysOrgBrands);
        return ResponseVO.success();
    }

    @ApiOperation(value = "修改机构品牌", notes = "修改机构品牌")
    @PostMapping("/update")
    public ResponseVO update(@Valid @RequestBody SysOrgBrands sysOrgBrands) throws MyException {
        sysOrgBrandsService.updateByPk(sysOrgBrands);
        return ResponseVO.success(ResponseEnum.SUCCESS);
    }

    @ApiOperation(value = "修改品牌状态", notes = "修改品牌状态")
    @PostMapping("/updateStatus")
    public ResponseVO updateStatus(@Valid @RequestBody UpdateBrandsStatusVO requestId) throws MyException {
        sysOrgBrandsService.updateStatus(requestId);
        return ResponseVO.success();
    }

    @ApiOperation(value = "全查机构品牌列表", notes = "全查机构品牌列表")
    @GetMapping("/findAll")
    public ResponseVO<List<SysOrgBrandsListVO>> findAll(SysOrgBrandsAllVO brandsSelectVO) {
        List<SysOrgBrandsListVO> sysOrgBrandss = sysOrgBrandsService.findAll(brandsSelectVO);
        return ResponseVO.success(sysOrgBrandss);
    }


}