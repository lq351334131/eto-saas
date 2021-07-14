package etocrm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.exception.MyException;
import org.etocrm.database.util.BasePage;
import org.etocrm.database.util.ResponseVO;
import org.etocrm.model.role.*;
import org.etocrm.service.RoleService;
import org.etocrm.utils.ParamId;
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
@RequestMapping("/role")
@Api(value = "角色信息", tags = "角色信息")
public class RoleController {

    @Resource
    private RoleService roleService;


    @ApiOperation(value = "角色分页列表", notes = "角色分页列表")
    @GetMapping("/list")
    public ResponseVO<BasePage<RoleSelectListVO>> list(RoleSelectVO vo) {
        BasePage<RoleSelectListVO> list = roleService.list(vo.getOffset(), vo.getLimit(), vo);
        return ResponseVO.success(list);
    }

    @ApiOperation(value = "新增角色", notes = "新增角色")
    @PostMapping("/save")
    public ResponseVO save(@Valid @RequestBody RoleSaveVO role) {
        Long res = roleService.saveRole(role);
        return res > 0 ? ResponseVO.success(ResponseEnum.SUCCESS.getCode(), "保存成功") : ResponseVO.error(ResponseEnum.FAILD.getCode(), "保存失败");
    }


    @ApiOperation(value = "修改角色", notes = "修改角色")
    @PostMapping("/update")
    public ResponseVO update(@Valid @RequestBody RoleUpdateVO role) throws MyException {
        Long res = roleService.updateByPk(role);
        return res > 0 ? ResponseVO.success(ResponseEnum.SUCCESS.getCode(), "修改成功") : ResponseVO.error(ResponseEnum.FAILD.getCode(), "修改失败");
    }

    @ApiOperation(value = "角色详情", notes = "角色详情")
    @PostMapping(value = "/detail")
    public ResponseVO<RoleDetailVO> detail(@Valid @RequestBody ParamId paramId) {

        RoleDetailVO role = roleService.detailByPk(paramId.getId());
        return ResponseVO.success(role);
    }

    @ApiOperation(value = "角色下拉列表", notes = "角色下拉列表")
    @GetMapping(value = "/pullDownRoleList")
    public ResponseVO<List<RoleSelectListVO>> pullDownRoleList(Long systemId) {

        List<RoleSelectListVO> vos = roleService.pullDownRoleList(systemId);
        return ResponseVO.success(vos);
    }


    @ApiOperation(value = "修改角色状态  状态1代表启用，0代表禁用", notes = "修改角色状态   状态1代表启用，0代表禁用")
    @PostMapping(value = "/updateStatus")
    public ResponseVO<Long> updateStatus(@Valid @RequestBody RoleStatusUpdateVO vo) throws MyException {
        Long updateStatus = roleService.updateStatus(vo.getId(), vo.getStatus());
        return ResponseVO.success(updateStatus);
    }

}