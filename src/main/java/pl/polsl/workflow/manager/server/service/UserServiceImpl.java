package pl.polsl.workflow.manager.server.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.workflow.manager.server.exception.BadRequestException;
import pl.polsl.workflow.manager.server.exception.UsernameAlreadyUsedException;
import pl.polsl.workflow.manager.server.helper.authentication.AuthenticationHelper;
import pl.polsl.workflow.manager.server.mapper.UserMapper;
import pl.polsl.workflow.manager.server.model.Role;
import pl.polsl.workflow.manager.server.model.User;
import pl.polsl.workflow.manager.server.repository.UserRepository;
import pl.polsl.workflow.manager.server.view.UserPatch;
import pl.polsl.workflow.manager.server.view.UserPost;
import pl.polsl.workflow.manager.server.view.UserView;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationHelper authenticationHelper;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, UserMapper userMapper, AuthenticationHelper authenticationHelper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    public UserView registerUser(UserPost userPost) {
        Role role = Role.valueOf(userPost.getRole());
        if(role.equals(Role.COORDINATOR))
            throw new BadRequestException("Coordinators can not create accounts of other coordinators");
        if(userRepository.findByUsername(userPost.getUsername()).isPresent())
            throw new UsernameAlreadyUsedException(userPost.getUsername());
        User user = userMapper.map(userPost);
        user.setPassword(bCryptPasswordEncoder.encode(userPost.getPassword()));
        user.setRole(role);
        return userMapper.map(userRepository.save(user));
    }

    @Override
    public UserView updateUser(Long userId, UserPatch userPatch) {
        User user = userRepository.getById(userId);
        if(user.getRole().equals(Role.COORDINATOR))
            throw new BadRequestException("Coordinators can not update accounts of other coordinators");
        userMapper.map(userPatch, user);
        return userMapper.map(userRepository.save(user));
    }

    @Override
    public List<UserView> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::map).collect(Collectors.toList());
    }

    @Override
    public UserView getSelf(String userToken) {
        User user = authenticationHelper.getUserFromToken(userToken);
        return userMapper.map(user);
    }
}
