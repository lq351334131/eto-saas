package etocrm.service;

import org.etocrm.model.org.approval.SysApprovalDetailVO;

/**
 * <p>
 * 审批表 服务类
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
public interface SysApprovalService {

    SysApprovalDetailVO detailByPk(Long pk, Integer type);

}