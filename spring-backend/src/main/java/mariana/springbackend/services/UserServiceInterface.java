package mariana.springbackend.services;

import mariana.springbackend.share.dto.PostDto;
import mariana.springbackend.share.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserServiceInterface extends UserDetailsService {
    UserDto getUser(String email);

    UserDto createUser(UserDto user);

    List<PostDto> getUserPosts(String email);

}
