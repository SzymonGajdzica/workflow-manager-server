package pl.polsl.workflow.manager.server.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.exception.BadRequestException;
import pl.polsl.workflow.manager.server.exception.UsernameAlreadyUsedException;
import pl.polsl.workflow.manager.server.mapper.UserMapper;
import pl.polsl.workflow.manager.server.model.*;
import pl.polsl.workflow.manager.server.repository.UserRepository;
import pl.polsl.workflow.manager.server.service.initialization.DataFiller;
import pl.polsl.workflow.manager.server.view.UserPatch;
import pl.polsl.workflow.manager.server.view.UserPost;
import pl.polsl.workflow.manager.server.view.UserView;

import java.util.Arrays;

@Component
public class UserServiceImpl implements UserService, DataFiller {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, UserMapper userMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
        userMapper.map(userPatch, user);
        return userMapper.map(userRepository.save(user));
    }

    @Override
    public void fillDatabase() {
        if(userRepository.count() != 0L)
            return;
        Coordinator coordinator = new Coordinator();
        coordinator.setUsername("coordinator1");
        coordinator.setPassword(bCryptPasswordEncoder.encode("coordinator1"));
        coordinator.setRole(Role.COORDINATOR);

        Manager manager = new Manager();
        manager.setUsername("manager1");
        manager.setPassword(bCryptPasswordEncoder.encode("manager1"));
        manager.setRole(Role.MANAGER);

        Worker worker = new Worker();
        worker.setUsername("worker1");
        worker.setPassword(bCryptPasswordEncoder.encode("worker1"));
        worker.setRole(Role.WORKER);

        userRepository.saveAll(Arrays.asList(coordinator, manager, worker));
    }
}
