package wtchrs.community.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wtchrs.community.common.entity.NameHistory;

public interface NameHistoryRepository extends JpaRepository<NameHistory, Long> {
}
