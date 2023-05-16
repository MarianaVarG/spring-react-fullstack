package mariana.springbackend.repositories;

import mariana.springbackend.entities.PostEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<PostEntity, Long> {
    /**
     * Get user posts. showing the newest ones first
     * @param userId user is
     * @return user posts
     */
    List<PostEntity> getByUserIdOrderByCreatedAtDesc(long userId);

    /**
     * In order to get las 20 public posts
     * @param exposureId if the ID changes in the DB
     * @param now date now
     * @return last 20 public posts
     */
    @Query(nativeQuery = true,
           value = "SELECT * FROM posts p WHERE p.exposure_id = :exposure AND p.expires_at > :now ORDER BY p.created_at DESC LIMIT 20")
    List<PostEntity> getLastPublicPosts(@Param("exposure") long exposureId, @Param("now") Date now);

    PostEntity findByPostId(String postId);
}
