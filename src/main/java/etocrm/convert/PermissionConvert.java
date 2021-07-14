package etocrm.convert;

import org.etocrm.entity.Permission;
import org.etocrm.model.permission.PermissionResponseVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @description: 菜单映射器
 * @author xingxing.xie
 * @date 2021/6/8 18:05
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface PermissionConvert {

    /**
     * 转换对应的vo类
     * @param permission
     * @return
     */
    PermissionResponseVO doToVo(Permission permission);


    /**
     * 转换对应的 vo list类
     * @param permissions vo list类
     * @return
     */
    List<PermissionResponseVO> doListToVoList(List<Permission> permissions);




}
