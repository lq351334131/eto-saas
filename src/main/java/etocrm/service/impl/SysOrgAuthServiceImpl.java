package etocrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.exception.MyException;
import org.etocrm.entity.SysOrgIndustry;
import org.etocrm.enums.AttachTypeEnum;
import org.etocrm.enums.OrgAuthEnum;
import org.etocrm.model.org.orgAuth.SysOrgAuthSaveVO;
import org.etocrm.model.org.orgAuth.SysOrgAuthUpdateVO;
import org.etocrm.model.org.orgAuth.SysOrgAuthVO;
import org.etocrm.repository.SysOrgAuthRepository;
import org.etocrm.service.SysAttachmentService;
import org.etocrm.service.SysDictService;
import org.etocrm.service.SysOrgAuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 认证表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Service
@Slf4j
public class SysOrgAuthServiceImpl implements SysOrgAuthService {

    @Resource
    private SysOrgAuthRepository sysOrgAuthRepository;

    @Resource
    private SysAttachmentService sysAttachmentService;

    @Resource
    SysDictService sysDictService;


    @Override
    public Long saveSysOrgAuth(SysOrgAuthSaveVO sysOrgAuth, Long id) throws MyException {
        SysOrgIndustry orgIndustry;
//        try {
            orgIndustry = new SysOrgIndustry();
            BeanUtil.copyProperties(sysOrgAuth, orgIndustry);
            orgIndustry.setOrgId(id);
            //待认证.
            orgIndustry.setAuthStatus(sysDictService.findByCode(OrgAuthEnum.NOT_AUTH.getCode()));
            sysOrgAuthRepository.save(orgIndustry);
            //保存其他文件落地
            sysAttachmentService.saveListAttachment(sysOrgAuth.getOtherUrls(), AttachTypeEnum.OEG.getCode(), orgIndustry.getId());
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            throw new MyException(ResponseEnum.FAILD.getCode(), "保存认证错误");
//        }
        return orgIndustry.getId();
    }

    @Override
    public Long updateByPk(SysOrgAuthUpdateVO sysOrgAuth) throws MyException {
        SysOrgIndustry sysOrgAuth1;
        sysOrgAuth1 = new SysOrgIndustry();
        BeanUtil.copyProperties(sysOrgAuth, sysOrgAuth1);
        sysOrgAuthRepository.update(sysOrgAuth1);
        //先删除文件
        sysAttachmentService.deleteByTypeAndExternalId(AttachTypeEnum.OEG.getCode(),sysOrgAuth1.getId());
        //保存其他文件落地
        sysAttachmentService.saveListAttachment(sysOrgAuth.getOtherUrls(),AttachTypeEnum.OEG.getCode(),sysOrgAuth1.getId());
        return sysOrgAuth1.getId();
    }

    @Override
    public SysOrgAuthVO detailByOrgId(Long pk)  {

        SysOrgIndustry byId = sysOrgAuthRepository.findByOrgId(pk);

        if(byId!=null) {
            SysOrgAuthVO vo = new SysOrgAuthVO();
            BeanUtil.copyProperties(byId, vo);
            vo.setOtherUrls(sysAttachmentService.findAll(AttachTypeEnum.OEG.getCode(), byId.getId()));
            vo.setAuthStatusName(sysDictService.findDictNameById(vo.getAuthStatus()));
            return vo;
        }
           return null;

    }

    @Override
    public List<SysOrgIndustry> findByAuthStatus(Long authStatus) {
        return sysOrgAuthRepository.findByAuthStatus(authStatus);
    }
}
