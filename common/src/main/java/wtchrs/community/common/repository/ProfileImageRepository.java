package wtchrs.community.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wtchrs.community.common.entity.ProfileImage;
import wtchrs.community.common.entity.User;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, User> {
}
