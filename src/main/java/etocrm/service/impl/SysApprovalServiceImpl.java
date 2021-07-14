package etocrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.entity.SysOrgApproval;
import org.etocrm.model.org.approval.SysApprovalDetailVO;
import org.etocrm.repository.SysApprovalRepository;
import org.etocrm.service.SysApprovalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 审批表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Service
@Slf4j
public class SysApprovalServiceImpl implements SysApprovalService {

    @Resource
    private SysApprovalRepository sysApprovalRepository;

    @Override
    public SysApprovalDetailVO detailByPk(Long pk, Integer type) {
        List<SysOrgApproval> byId = sysApprovalRepository.findByOrgIdAndTypeOrderByCreatedTimeDesc(pk, type);
        SysApprovalDetailVO sysApprovalDetailVO =  new SysApprovalDetailVO();;
       if(CollectionUtil.isNotEmpty(byId)) {
           BeanUtil.copyProperties(byId.get(0), sysApprovalDetailVO);
           return sysApprovalDetailVO;
       }
       return null;

    }

}
