package etocrm.repository;

import org.etocrm.database.repository.BaseRepository;
import org.etocrm.entity.ImportHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportHistoryRepository   extends BaseRepository<ImportHistory, Long> {
}
