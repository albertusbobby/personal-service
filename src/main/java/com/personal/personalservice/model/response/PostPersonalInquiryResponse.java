package com.personal.personalservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostPersonalInquiryResponse implements Serializable {

    private String name;
    private String email;
    private String status;
    private Boolean hasSession;


}
