package com.awda.services;

import com.awda.kafka.UserProducer;
import com.awda.mapper.UserMapper;
import com.awda.model.MessageTemplate;
import com.awda.model.User;
import com.awda.model.UserData;
import com.awda.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserProducer userProducer;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userMapper.findByEmail(email);
        return user.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("user with email %s not found", email)
                ));
    }

    public List<User> getAllUser() {
        List<User> userList = userMapper.findAll();
        if (userList.isEmpty()) {
            return new ArrayList<>();
        }
        return userList;
    }

    public User getUser(Long id) {
        Optional<User> optionalUser = userMapper.findById(id);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
        return optionalUser.get();
    }

    public User storeUser(User user) {
        Optional<User> isUserExist = userMapper.findByEmail(user.getEmailAddress());
        if (isUserExist.isPresent()) {
            throw new IllegalArgumentException("Email " + user.getEmailAddress() + " is already registered");
        }
        String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        userMapper.store(user);
        UserData userData = new UserData(user.getId(), user.getUserName(), user.getAccountNumber(), user.getEmailAddress(), user.getIdentityNumber(), encodePassword);
        MessageTemplate messageTemplate = new MessageTemplate("POST", user);
        this.userProducer.sendMessage(messageTemplate);
        rabbitTemplate.convertAndSend("", "q.user-registration", messageTemplate);
        return user;
    }

    public void updateUser(User user, Long id) {
        try {
            String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
            userMapper.update(user, id);
            UserData userData = new UserData(id, user.getUserName(), user.getAccountNumber(), user.getEmailAddress(), user.getIdentityNumber(), encodePassword);
            MessageTemplate messageTemplate = new MessageTemplate("PUT", user);
            this.userProducer.sendMessage(messageTemplate);
            rabbitTemplate.convertAndSend("", "q.user-registration", messageTemplate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Boolean deleteUser(Long id) {
        try {
            Optional<User> existingUser = userMapper.findById(id);
            if (existingUser.isEmpty()) {
                return false;
            }
            userMapper.deleteById(id);
            UserData userData = new UserData(id, null, null, null, null, null);
            MessageTemplate messageTemplate = new MessageTemplate("DELETE", existingUser.get());
            this.userProducer.sendMessage(messageTemplate);
            rabbitTemplate.convertAndSend("", "q.user-registration", messageTemplate);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
