package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.ImportCompanyFail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportCompanyFailRepository  extends BaseRepository<ImportCompanyFail, Long> {
    List<ImportCompanyFail> findByImportBatch(String importBatch);
}
