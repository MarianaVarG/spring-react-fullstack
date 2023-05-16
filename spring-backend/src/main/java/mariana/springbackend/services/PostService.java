package mariana.springbackend.services;

import mariana.springbackend.entities.ExposureEntity;
import mariana.springbackend.entities.PostEntity;
import mariana.springbackend.entities.UserEntity;
import mariana.springbackend.exceptions.PermissionsException;
import mariana.springbackend.repositories.ExposureRepository;
import mariana.springbackend.repositories.PostRepository;
import mariana.springbackend.repositories.UserRepository;
import mariana.springbackend.share.dto.PostCreationDto;
import mariana.springbackend.share.dto.PostDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PostService implements PostServiceInterface {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExposureRepository exposureRepository;

    @Autowired
    ModelMapper mapper;

    /**
     * @param postCreationDto
     * @return
     */
    @Override
    public PostDto createPost(PostCreationDto postCreationDto) {
        UserEntity userEntity = userRepository.findByEmail(postCreationDto.getUserEmail());
        ExposureEntity exposureEntity = exposureRepository.findById(postCreationDto.getExposureId());

        PostEntity postEntity = new PostEntity();
        postEntity.setUser(userEntity);
        postEntity.setExposure(exposureEntity);
        postEntity.setTitle(postCreationDto.getTitle());
        postEntity.setContent(postCreationDto.getContent());
        postEntity.setPostId(UUID.randomUUID().toString());
        postEntity.setExpiresAt(new Date(System.currentTimeMillis() + (postCreationDto.getExpirationTime() * 60000L)));

        PostEntity createdPost = postRepository.save(postEntity);
        return mapper.map(createdPost, PostDto.class);
    }

    /**
     * @return Last 20 posts
     */
    @Override
    public List<PostDto> getLastPosts() {
        long publicExposureId = 2;
        List<PostEntity> postEntities = postRepository.getLastPublicPosts(publicExposureId, new Date(System.currentTimeMillis()));

        List<PostDto> postDtoList = new ArrayList<>();

        for (PostEntity post : postEntities) {
            postDtoList.add(mapper.map(post, PostDto.class));
        }
        return postDtoList;
    }

    /**
     * @param id Post id
     * @return post details
     */
    @Override
    public PostDto getPost(String id) {
        PostEntity postEntity = postRepository.findByPostId(id);
        return mapper.map(postEntity, PostDto.class);
    }

    /**
     * The posts should be created for user with him id = userId
     * @param postId post id
     * @param userId user id
     */
    @Override
    public void deletePost(String postId, long userId) {
        PostEntity postEntity = postRepository.findByPostId(postId);
        if (postEntity.getUser().getId() != userId)
            throw new PermissionsException("Not allowed to perform this action");

        postRepository.delete(postEntity);
    }

    /**
     * @param postId
     * @param userId
     * @param postUpdateDto
     * @return post after update
     */
    @Override
    public PostDto updatePost(String postId, long userId, PostCreationDto postUpdateDto) {
        PostEntity postEntity = postRepository.findByPostId(postId);
        if (postEntity.getUser().getId() != userId)
            throw new PermissionsException("Not allowed to perform this action");

        ExposureEntity exposureEntity = exposureRepository.findById(postUpdateDto.getExposureId());

        postEntity.setExposure(exposureEntity);
        postEntity.setTitle(postUpdateDto.getTitle());
        postEntity.setContent(postUpdateDto.getContent());
        postEntity.setExpiresAt(new Date(System.currentTimeMillis() + (postUpdateDto.getExpirationTime() * 60000L)));

        // Save post
        PostEntity updatedPost = postRepository.save(postEntity);

        // Mapping and return
        return mapper.map(updatedPost, PostDto.class);
    }
}
