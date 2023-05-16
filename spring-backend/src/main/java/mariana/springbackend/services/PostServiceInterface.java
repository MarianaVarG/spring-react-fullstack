package mariana.springbackend.services;

import mariana.springbackend.share.dto.PostCreationDto;
import mariana.springbackend.share.dto.PostDto;

import java.util.List;

public interface PostServiceInterface {
    PostDto createPost(PostCreationDto postCreationDto);

    List<PostDto> getLastPosts();

    PostDto getPost(String id);

    void deletePost(String postId, long userId);

    PostDto updatePost(String postId, long userId, PostCreationDto postUpdateDto);
}
