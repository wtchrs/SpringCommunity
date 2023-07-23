package wtchrs.SpringCommunity.entity.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageContentRepository extends JpaRepository<ImageContent, Long> {

    Optional<ImageContent> findByName(String name);

}
