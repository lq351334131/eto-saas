package etocrm.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.util.ResponseVO;
import org.etocrm.entity.SysRegions;
import org.etocrm.repository.SysRegionsRepository;
import org.etocrm.utils.UpLoadUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sysCommon")
@Api(value = "公共服务", tags = "公共服务")
public class SysCommonController {
    @Resource
    SysRegionsRepository regionRepository;

    @Resource
    UpLoadUtil upLoadUtil;

    @ApiOperation(value = "上传文件", notes = "上传文件")
    @PostMapping("/upLoad")
    public JSONObject uploadFile(@RequestParam MultipartFile file) {
       return upLoadUtil.getCdnPath(file);
    }

    @ApiOperation("获取全部省市区")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "省pid", dataType = "Integer")
    })
    @GetMapping("region")
    public ResponseVO region(@RequestParam(required = false) Integer pid) {
        if (pid == null) {
            //默认最顶级
            return ResponseVO.success(regionRepository.findByPid(9998));
        } else {
            List<SysRegions> list = regionRepository.findByPid(pid);
            return ResponseVO.success(list);
        }
    }

}