package com.personal.personalservice.service;

import com.personal.personalservice.model.request.PostPersonalInquiryRequest;
import com.personal.personalservice.model.response.PostPersonalInquiryResponse;
import com.personal.personalservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostPersonalInquiryService {

    private final UserRepository userRepository;

    public PostPersonalInquiryResponse execute(PostPersonalInquiryRequest request) {
        return userRepository.findByUsername(request.getName())
                .map(u -> PostPersonalInquiryResponse.builder()
                        .name(u.getFullName())
                        .email(u.getEmail())
                        .status(u.getStatus())
                        .build())
                .orElse(new PostPersonalInquiryResponse());
    }
}
