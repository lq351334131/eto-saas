package etocrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.etocrm.entity.SysAttachment;
import org.etocrm.entity.SysAttachmentSaveVO;
import org.etocrm.repository.SysAttachmentRepository;
import org.etocrm.service.SysAttachmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Service
@Slf4j
public class SysAttachmentServiceImpl implements SysAttachmentService {

    @Resource
    private SysAttachmentRepository sysAttachmentRepository;


    @Override
    public Long saveSysAttachment(SysAttachmentSaveVO sysAttachment) {
        SysAttachment sysAttachment1 = new SysAttachment();
        BeanUtil.copyProperties(sysAttachment, sysAttachment1);
        sysAttachmentRepository.save(sysAttachment1);
        return sysAttachment1.getId();
    }

    @Override
    public void saveListAttachment(List<String> list, String code, Long id) {
        SysAttachmentSaveVO sysAttachmentSaveVO;
        if(CollectionUtil.isNotEmpty(list)) {
            for (String otherUrl : list) {
                if (StringUtils.isNotBlank(otherUrl)) {
                    sysAttachmentSaveVO = new SysAttachmentSaveVO();
                    sysAttachmentSaveVO.setUrl(otherUrl);
                    if (otherUrl.contains("_")) {
                        String substring = otherUrl.substring(otherUrl.lastIndexOf("_"));
                        sysAttachmentSaveVO.setName(substring);
                    }
                    sysAttachmentSaveVO.setTypeCode(code);
                    sysAttachmentSaveVO.setExternalId(id);
                    saveSysAttachment(sysAttachmentSaveVO);
                }
            }
        }
    }


    @Override
    public SysAttachment detailByPk(Long pk) {
        Optional<SysAttachment> byId = sysAttachmentRepository.findById(pk);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    @Override
    public List<String> findAll(String code, Long id) {
        List<SysAttachment> sysAttachments = sysAttachmentRepository.findByTypeCodeAndExternalId(code, id);
        if (sysAttachments.isEmpty()) {
            return new ArrayList<>();
        }
        return sysAttachments.stream().map(SysAttachment::getUrl).collect(Collectors.toList());
    }

    @Override
    public void deleteByTypeAndExternalId(String code, Long id) {
        List<SysAttachment> byTypeCodeAndExternalId = sysAttachmentRepository.findByTypeCodeAndExternalId(code, id);
        sysAttachmentRepository.logicDelete(byTypeCodeAndExternalId);
    }
}
