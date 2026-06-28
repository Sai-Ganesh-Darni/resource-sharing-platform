package com.example.resourcesharingsystem.service;

import com.example.resourcesharingsystem.dto.UserInfo;
import com.example.resourcesharingsystem.entity.User;
import com.example.resourcesharingsystem.mapper.UserMapper;
import com.example.resourcesharingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public List<UserInfo> getAllUsers() {
        List<User> users = userRepository.findAllByDeletedFalse();
        List<UserInfo> userInfos = new ArrayList<>();
        for (User user : users) {
            userInfos.add(UserMapper.toResponse(user));
        }
        return userInfos;
    }
}
