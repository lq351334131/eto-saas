package etocrm.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.etocrm.model.permission.PermissionVO;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaasTreeUtil {
    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     具有树形结构特点的集合
     * @param parentId 父节点ID
     *
     * @return 树形结构集合 child menu vos
     */
    public static List<PermissionVO> getChildMenuVos(List<PermissionVO> list, Long parentId) {
        List<PermissionVO> returnList = Lists.newArrayList();

        for (PermissionVO t : list) {
            if (t.getParentId() == null) {
                continue;
            }

            if (Objects.equals(t.getParentId() , parentId)) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private static void recursionFn(List<PermissionVO> list, PermissionVO t) {
        // 得到子节点列表
        List<PermissionVO> childList = getChildList(list, t);
        t.setSubMenu(childList);
        if (CollectionUtil.isNotEmpty(childList)) {
            t.setHasMenu(true);
        }
        for (PermissionVO tChild : childList) {
            // 判断是否有子节点
            if (hasChild(list, tChild)) {
                for (PermissionVO n : childList) {
                    recursionFn(list, n);
                }
                tChild.setHasMenu(true);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private static List<PermissionVO> getChildList(List<PermissionVO> list, PermissionVO t) {
        List<PermissionVO> tList = Lists.newArrayList();

        for (PermissionVO menuVo : list) {
            if (menuVo.getParentId()==null) {
                continue;
            }
            if (Objects.equals(menuVo.getParentId(), t.getId())) {
                tList.add(menuVo);
            }
        }
        return tList;
    }

    /**
     * 判断是否有子节点
     */
    private static boolean hasChild(List<PermissionVO>  list, PermissionVO t) {
        return !getChildList(list, t).isEmpty();
    }

}