package com.example.bookstorebackendappcfp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "Build")
@NoArgsConstructor
public class ResponseDTO {
    private String message;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Object data;
}
