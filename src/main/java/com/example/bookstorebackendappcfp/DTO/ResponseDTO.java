package com.example.bookstorebackendappcfp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "Build")
@NoArgsConstructor
public class ResponseDTO {
    private String message;
    private Object data;
}
