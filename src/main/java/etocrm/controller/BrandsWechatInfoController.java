package etocrm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.BasePage;
import org.etocrm.database.util.ResponseVO;
import org.etocrm.model.brands.SysBrandsWechatInfoVO;
import org.etocrm.model.minapp.*;
import org.etocrm.service.BrandsWechatInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author admin
 * @since 2021-01-22
 */
@Slf4j
@RestController
@RequestMapping("/brandsWechatService")
@Api(value = "品牌公众号小程序", tags = "品牌公众号小程序")
public class BrandsWechatInfoController {

    @Resource
    private BrandsWechatInfoService wechatInfoService;


    @ApiOperation(value = "品牌公众号小程序分页列表", notes = "品牌公众号小程序分页列表")
    @GetMapping("/list")
    public ResponseVO<BasePage<BrandsWechatListVO>> list(BrandsWechatSelectVO brandsWechatSelectVO) throws MyException {
        BasePage<BrandsWechatListVO> list = wechatInfoService.list(brandsWechatSelectVO.getOffset(), brandsWechatSelectVO.getLimit(), brandsWechatSelectVO);
        return ResponseVO.success(list);
    }

    @ApiOperation(value = "新增品牌公众号小程序", notes = "新增品牌公众号小程序")
    @PostMapping("/save")
    public ResponseVO save(@Valid @RequestBody BrandsWechatInfoSaveVO brandsWechatService) {
        Long a = wechatInfoService.saveBrandsWechatService(brandsWechatService);
        return ResponseVO.success(a);
    }

    @ApiOperation(value = "修改品牌公众号小程序", notes = "修改品牌公众号小程序")
    @PostMapping("/update")
    public ResponseVO update(@Valid @RequestBody BrandsWechatInfoUpdateVO brandsWechatService) throws MyException {
        Long a = wechatInfoService.updateByPk(brandsWechatService);
        return ResponseVO.success(a);
    }

    @ApiOperation(value = "更新状态品牌公众号小程序", notes = "更新状态品牌公众号小程序")
    @PostMapping("/updateStatus")
    public ResponseVO updateStatus(@Valid @RequestBody UpdateMinAppStatusVO requestId) throws MyException {
        wechatInfoService.updateStatus(requestId);
        return ResponseVO.success();
    }

    @ApiOperation(value = "品牌公众号小程序详情", notes = "品牌公众号小程序详情")
    @PostMapping(value = "/detail")
    public ResponseVO<List<BrandsWechatInfoUpdateVO>> detail(@Valid @RequestBody BindParam bandParam ) {
        List<BrandsWechatInfoUpdateVO> brandsWechatService = wechatInfoService.detailByBrandsId(bandParam.getBrandsId(),bandParam.getType());
        return ResponseVO.success(brandsWechatService);
    }


    @ApiOperation(value = "品牌公众号小程序/公众号下拉列表", notes = "品牌公众号小程序/公众号下拉列表")
    @PostMapping(value = "/findAll")
    public ResponseVO< List<BrandWechatInfoVO>> getDetailByType(@RequestBody  SysBrandsWechatInfoVO vo) {
        List<BrandWechatInfoVO> res = wechatInfoService.getDetailByType(vo);
        return ResponseVO.success(res);
    }

    @ApiOperation(value = "品牌绑定小程序/公众号", notes = "绑定小程序/公众号")
    @PostMapping(value = "/bind/mini/app")
    public ResponseVO<String> bindBrandsAndMiniApp(@RequestBody BindParam bindParam) {
        Long  res = wechatInfoService.bindBrandsAndMiniApp(bindParam);
        return res>0?ResponseVO.success(ResponseEnum.SUCCESS.getCode(),"绑定成功"):ResponseVO.success(ResponseEnum.FAILD.getCode(),"绑定失败");
    }

    @ApiOperation(value = "已绑定微信公众号", notes = "已绑定微信公众号")
    @PostMapping(value = "/find/bind/wechatInfo")
    public ResponseVO< List<BrandWechatInfoVO>> getBindedWechatInfo(Long brandId,String type) {
        List<BrandWechatInfoVO> res = wechatInfoService.getBindedWechatInfo(brandId,type);
        return ResponseVO.success(res);
    }


    @ApiOperation(value = "解除绑定", notes = "解除绑定")
    @PostMapping(value = "/dismiss/wechatInfo")
    public ResponseVO<Long> dismissBrandsWechatInfo(@RequestBody DissmissBindParam bindParam) {
        Long  res = wechatInfoService.dismissBrandsWechatInfo(bindParam);
        return ResponseVO.success(res);
    }

}