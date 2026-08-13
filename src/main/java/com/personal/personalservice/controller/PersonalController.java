package com.personal.personalservice.controller;

import com.personal.personalservice.model.request.PostPersonalInquiryRequest;
import com.personal.personalservice.model.response.PostPersonalInquiryResponse;
import com.personal.personalservice.service.PostPersonalInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PersonalController {

    private final PostPersonalInquiryService postPersonalInquiryService;

    @PostMapping("/personal/v1/inquiry")
    public PostPersonalInquiryResponse personalInquiry(@RequestBody PostPersonalInquiryRequest request) {
        return postPersonalInquiryService.execute(request);
    }
}
