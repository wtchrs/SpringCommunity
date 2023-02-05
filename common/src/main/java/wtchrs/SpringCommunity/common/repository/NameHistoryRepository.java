package wtchrs.SpringCommunity.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wtchrs.SpringCommunity.common.entity.NameHistory;

public interface NameHistoryRepository extends JpaRepository<NameHistory, Long> {
}
