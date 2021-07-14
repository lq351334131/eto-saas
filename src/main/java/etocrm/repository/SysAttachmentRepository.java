package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.SysAttachment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 文件表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-01-19
 */
@Repository
public interface SysAttachmentRepository extends BaseRepository<SysAttachment, Long>{

    List<SysAttachment> findByTypeCodeAndExternalId(String code, Long id);
}