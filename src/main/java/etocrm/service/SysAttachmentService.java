package etocrm.service;


import org.etocrm.entity.SysAttachment;
import org.etocrm.entity.SysAttachmentSaveVO;

import java.util.List;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
public interface SysAttachmentService {

    Long saveSysAttachment(SysAttachmentSaveVO sysAttachment);

    void saveListAttachment(List<String> list, String code, Long id);

    SysAttachment detailByPk(Long pk);


    List<String> findAll(String code, Long id);

    void deleteByTypeAndExternalId(String code, Long id);


}