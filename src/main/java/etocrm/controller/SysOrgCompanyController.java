package etocrm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.BasePage;
import org.etocrm.database.util.ResponseVO;
import org.etocrm.entity.ImportTemplate;
import org.etocrm.model.CompanyDistributeDetailVO;
import org.etocrm.model.company.*;
import org.etocrm.service.CompanyDistributeDetailService;
import org.etocrm.service.SysOrgCompanyService;
import org.etocrm.utils.RequestID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author admin
 * @since 2021-01-23
 */
@Slf4j
@RestController
@RequestMapping("/sysOrgCompany")
@Api(value = "机构公司", tags = "机构公司")
public class SysOrgCompanyController {

    @Resource
    private SysOrgCompanyService sysOrgCompanyService;

    @Autowired
    private CompanyDistributeDetailService companyDistributeDetailService;


    @ApiOperation(value = "获取全部公司树", notes = "获取全部公司树")
    @GetMapping("/findTree")
    public ResponseVO<List<SysOrgCompanyTreeVO>> findAll(Integer status,Long id,Integer openShop) throws MyException {
        List<SysOrgCompanyTreeVO> sysOrgCompanys = sysOrgCompanyService.findAll(status,id,openShop);
        return ResponseVO.success(sysOrgCompanys);
    }


    @ApiOperation(value = "新增机构公司", notes = "新增机构公司")
    @PostMapping("/save")
    public ResponseVO save(@Valid @RequestBody SysOrgCompanySaveVO sysOrgCompany) throws MyException {
        //0总公司1分公司  (默认 分公司)
        sysOrgCompany.setType(1);
        Long aLong = sysOrgCompanyService.saveSysOrgCompany(sysOrgCompany);
        return ResponseVO.success(aLong);
    }

    @ApiOperation(value = "修改机构公司", notes = "修改机构公司")
    @PostMapping("/update")
    public ResponseVO update(@Valid @RequestBody SysOrgCompanyUpdateVO sysOrgCompany) throws MyException {
        //不更新 公司类型
        sysOrgCompany.setType(null);
        Long aLong = sysOrgCompanyService.updateByPk(sysOrgCompany);
        return ResponseVO.success(aLong);
    }


    @ApiOperation(value = "更新状态机构公司", notes = "更新状态机构公司")
    @PostMapping("/updateStatus")
    public ResponseVO updateStatus(@Valid @RequestBody UpdateCompanyStatusVO requestId) throws MyException {
        Long a = sysOrgCompanyService.updateStatus(requestId);
        return ResponseVO.success(a);
    }

    @ApiOperation(value = "机构公司信息详情", notes = "机构公司信息详情")
    @PostMapping(value = "/detail")
    public ResponseVO<SysOrgCompanyUpdateVO> detail(@Valid @RequestBody RequestID requestId) {
        SysOrgCompanyUpdateVO sysOrgCompany = sysOrgCompanyService.detailByPk(requestId.getId());
        return ResponseVO.success(sysOrgCompany);
    }

    @ApiOperation(value = "根据机构获取公司", notes = "根据机构获取公司")
    @GetMapping(value = "/org/company/list")
    public ResponseVO<BasePage<SysOrgCompanyVO>> getCompanyList(SysCompanyPageListVO v) {
        BasePage<SysOrgCompanyVO> vo = sysOrgCompanyService.getCompanyList(v.getOffset(), v.getLimit(), v);
        return ResponseVO.success(vo);
    }

    @ApiOperation(value = "导入机构公司", notes = "导入机构公司")
    @PostMapping("/import")
    public ResponseVO importCompany(MultipartFile file) {
        Long res = sysOrgCompanyService.importCompany(file);
        return res > 0 ? ResponseVO.success(res) : ResponseVO.error(ResponseEnum.FAILD.getCode(), "保存失败");
    }

    @ApiOperation(value = "导入公司的错误信息", notes = "导入公司的错误信息")
    @GetMapping("/error/history")
    public ResponseVO<ImportHistoryVO> importErrorCompany(Long id) {
        ImportHistoryVO res = sysOrgCompanyService.importErrorCompany(id);
        return ResponseVO.success(res);
    }

    @ApiOperation(value = "导入公司模板文件", notes = "导入公司模板文件")
    @GetMapping("/template")
    public ResponseVO<String> importTemplate() {
        ImportTemplate res = sysOrgCompanyService.importTemplate();
        return ResponseVO.success(res.getTemplateUrl());
    }

    @ApiOperation(value = "分公司新增、修改分发回调", notes = "分公司新增、修改分发回调")
    @PostMapping(value = "/callbackInfo")
    public ResponseVO callbackInfo(@RequestBody List<CompanyDistributeDetailVO> voList) throws MyException {
        companyDistributeDetailService.save(voList);
        return ResponseVO.success();
    }

}