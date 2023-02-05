package wtchrs.SpringCommunity.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wtchrs.SpringCommunity.common.entity.ProfileImage;
import wtchrs.SpringCommunity.common.entity.ProfileImageId;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, ProfileImageId> {
}
