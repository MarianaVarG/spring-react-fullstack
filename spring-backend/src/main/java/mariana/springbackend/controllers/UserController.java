package mariana.springbackend.controllers;

import mariana.springbackend.models.requests.UserDetailsRequestModel;
import mariana.springbackend.models.responses.PostRest;
import mariana.springbackend.models.responses.UserRest;
import mariana.springbackend.services.UserServiceInterface;
import mariana.springbackend.share.dto.PostDto;
import mariana.springbackend.share.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller in order to CRUD users
 */
@RestController
@RequestMapping("/users") /* localhost:8080/users */
public class UserController {
    static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserServiceInterface userService;

    @Autowired
    ModelMapper mapper;

    /**
     * Login
     * @return user details
     */
    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser() {
        LOGGER.info("UserController - getUser (GetMapping): /users");
        // When authentication is successful, the security context will set
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Get user logged email
        String email = authentication.getPrincipal().toString();
        // Get userDto from email
        UserDto userDto = userService.getUser(email);
        // Convert userDto to UserRest
        return mapper.map(userDto, UserRest.class);
    }

    /**
     * Method to create a user
     * @param userDetails new user details
     * @return user created
     */
    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
        LOGGER.info("UserController - createUser (PostMapping): /users");
        // 1. We receive the object in json, it is transformed into java object
        UserRest userToReturn = new UserRest();
        // 2. To send it to the project logic
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        // 3. To create the user
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, userToReturn);

        return userToReturn;
    }

    /**
     * End point to get users posts, only posts that have not expired are shown.
     * @return Posts lists
     */
    @GetMapping(path = "/posts")
    public List<PostRest> getPosts() {
        LOGGER.info("UserController - getPosts (GetMapping): /users/posts");
        // Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();

        List<PostDto> postDtoList = userService.getUserPosts(email);
        //Convert PostDto list to PostRest list
        List<PostRest> postRestList = new ArrayList<>();
        for (PostDto post : postDtoList) {
            PostRest postRest = mapper.map(post, PostRest.class);
            if (postRest.getExpiresAt().compareTo(new Date(System.currentTimeMillis())) < 0){
                postRest.setExpired(true);
            }
            postRestList.add(postRest);
        }

        return postRestList;
    }
}
