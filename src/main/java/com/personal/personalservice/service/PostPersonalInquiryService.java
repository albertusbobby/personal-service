package com.personal.personalservice.service;

import com.personal.personalservice.model.request.PostPersonalInquiryRequest;
import com.personal.personalservice.model.response.PostPersonalInquiryResponse;
import com.personal.personalservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostPersonalInquiryService {

    private final UserRepository userRepository;
    private final StringRedisTemplate stringRedisTemplate;

    public PostPersonalInquiryResponse execute(PostPersonalInquiryRequest request) {
        return userRepository.findByUsername(request.getName())
                .map(u -> PostPersonalInquiryResponse.builder()
                        .name(u.getFullName())
                        .email(u.getEmail())
                        .status(u.getStatus())
                        .hasSession(stringRedisTemplate.opsForValue()
                                .getOperations().hasKey("user-token-".concat(u.getEmail())))
                        .build())
                .orElse(new PostPersonalInquiryResponse());
    }
}
