package mariana.springbackend.services;

import mariana.springbackend.entities.PostEntity;
import mariana.springbackend.entities.UserEntity;
import mariana.springbackend.exceptions.EmailExistsException;
import mariana.springbackend.repositories.PostRepository;
import mariana.springbackend.repositories.UserRepository;
import mariana.springbackend.share.dto.PostDto;
import mariana.springbackend.share.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ModelMapper mapper;

    /**
     * @param email - user email
     * @return users with @email
     */
    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null )
            throw new UsernameNotFoundException(email);

        UserDto userToReturn = new UserDto();
        BeanUtils.copyProperties(userEntity, userToReturn);

        return userToReturn;
    }

    /**
     * @param user - new user data
     * @return user created
     */
    @Override
    public UserDto createUser(UserDto user) {
        // Validation: not create a user with same email
        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new EmailExistsException("e-mail already exists");
        // Map UserDto to user entity
        UserEntity userEntity = mapper.map(user, UserEntity.class);
        // Encrypting password
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // Automatic and random id
        userEntity.setUserId(UUID.randomUUID().toString());
        // When we use "save" method, a user of type UserEntity is returned and that one is the user that is on DB
        UserEntity storedUserDetails = userRepository.save(userEntity);
        // Map and return
        return mapper.map(storedUserDetails, UserDto.class);
    }

    /**
     * @param email owner of posts
     * @return user posts
     */
    @Override
    public List<PostDto> getUserPosts(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        List<PostEntity> postEntityList = postRepository.getByUserIdOrderByCreatedAtDesc(userEntity.getId());
        // Mapping
        List<PostDto> postDtoList = new ArrayList<>();
        for (PostEntity post : postEntityList) {
            postDtoList.add(mapper.map(post, PostDto.class));
        }
        return postDtoList;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
