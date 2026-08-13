package com.personal.personalservice.service;

import com.personal.personalservice.model.request.PostPersonalInquiryRequest;
import com.personal.personalservice.model.response.PostPersonalInquiryResponse;
import com.personal.personalservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class PostPersonalInquiryService {

    private final UserRepository userRepository;
    private final StringRedisTemplate stringRedisTemplate;

    public PostPersonalInquiryResponse execute(PostPersonalInquiryRequest request) {
        log.info("Inquiry data");
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
