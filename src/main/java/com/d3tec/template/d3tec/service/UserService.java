package com.d3tec.template.d3tec.service;

import com.d3tec.template.d3tec.entity.User;
import com.d3tec.template.d3tec.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
}
