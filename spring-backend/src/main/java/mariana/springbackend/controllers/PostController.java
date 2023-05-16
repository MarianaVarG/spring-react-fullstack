package mariana.springbackend.controllers;

import mariana.springbackend.exceptions.PermissionsException;
import mariana.springbackend.models.requests.PostCreateRequestModel;
import mariana.springbackend.models.responses.OperationStatusModel;
import mariana.springbackend.models.responses.PostRest;
import mariana.springbackend.services.PostServiceInterface;
import mariana.springbackend.services.UserServiceInterface;
import mariana.springbackend.share.dto.PostCreationDto;
import mariana.springbackend.share.dto.PostDto;
import mariana.springbackend.share.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller in order to CRUD posts
 */
@RestController
@RequestMapping("/posts") /* localhost:8080/posts */
public class PostController {
    static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    @Autowired
    PostServiceInterface postService;

    @Autowired
    UserServiceInterface userService;

    @Autowired
    ModelMapper mapper;

    @PostMapping
    public PostRest createPost(@RequestBody PostCreateRequestModel createRequestModel) {
        LOGGER.info("PostController - createPost (PostMapping): /posts");
        // Logger user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        // Mapping
        PostCreationDto postCreationDto = mapper.map(createRequestModel, PostCreationDto.class);
        postCreationDto.setUserEmail(email);
        PostDto postDto = postService.createPost(postCreationDto);
        PostRest postToReturn = mapper.map(postDto, PostRest.class);

        validateExpiration(postToReturn);

        return postToReturn;
    }

    @GetMapping(path = "/last")
    public List<PostRest> lastPosts() {
        LOGGER.info("PostController - lastPosts (GetMapping): /posts/last");

        List<PostDto> postDtoList = postService.getLastPosts();
        // Mapper
        List<PostRest> postRestList = new ArrayList<>();
        for (PostDto post: postDtoList) {
            postRestList.add(mapper.map(post, PostRest.class));
        }

        return postRestList;
    }

    @GetMapping(path = "/{id}")
    public PostRest getPost(@PathVariable String id) {
        LOGGER.info("Post Controller - getPosts (GetMapping): /posts/{id}");

        PostDto postDto = postService.getPost(id);
        PostRest postRest = mapper.map(postDto, PostRest.class);

        validateExpiration(postRest);

        // Validate if post is private or/and expired - private = 1
        if (postRest.getExposure().getId() == 1 || postRest.isExpired()) {
            // Logged user
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(authentication.getPrincipal().toString());
            if (user.getId() != postDto.getUser().getId()) {
                throw new PermissionsException("Not allowed to perform this action");
            }
        }
        return postRest;
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deletePost(@PathVariable String id) {
        LOGGER.info("Post controller - deletePost (DeleteMapping): /posts/{id} ");
        // Logged user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(authentication.getPrincipal().toString());

        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setName("DELETE");

        postService.deletePost(id, user.getId());
        operationStatusModel.setResult("SUCCESS");

        return operationStatusModel;
    }

    @PutMapping(path = "/{id}")
    public PostRest updatePost(@RequestBody PostCreateRequestModel postUpdateRequestModel, @PathVariable String id) {
        LOGGER.info("Post controller - updatePost (PutMapping): /posts/{id}");
        // Logged user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(authentication.getPrincipal().toString());

        // Map and update post
        PostCreationDto postUpdateDto = mapper.map(postUpdateRequestModel, PostCreationDto.class);
        PostDto postDto = postService.updatePost(id, user.getId(), postUpdateDto);

        // Map and return post
        return mapper.map(postDto, PostRest.class);
    }

    private void validateExpiration(PostRest post) {
        if (post.getExpiresAt().compareTo(new Date(System.currentTimeMillis())) < 0){
            post.setExpired(true);
        }
    }
}
