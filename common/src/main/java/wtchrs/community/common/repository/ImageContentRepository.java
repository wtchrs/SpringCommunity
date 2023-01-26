package wtchrs.community.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wtchrs.community.common.entity.ImageContent;

public interface ImageContentRepository extends JpaRepository<ImageContent, Long> {
}
