package com.example.bookstorebackendappcfp.Exception;


import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.bookstorebackendappcfp.DTO.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    //---------------------------Method arguments invalid exception--------------------------//

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> methodArgumentException(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        List<FieldError> errorList = exception.getBindingResult().getFieldErrors();
        errorList.forEach(objErr ->
                errorMap.put(objErr.getField(), objErr.getDefaultMessage()));
        ResponseDTO responseDTO = ResponseDTO.Build("Method parameters invalid while processing Http Method Request", errorMap);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }



    //---------------------------Username password invalid exception----------------------//

    @ExceptionHandler(UsernamePasswordInvalidException.class)
    public ResponseEntity<ResponseDTO> handleBusinessException(UsernamePasswordInvalidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error Message", exception.getMessage());
        ResponseDTO responseDTO = ResponseDTO.Build("Exception while processing Http Method Request", exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    //---------------------------User not Found invalid exception----------------------//
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ResponseDTO> handleBusinessException(UserException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error Message", exception.getMessage());
        ResponseDTO responseDTO = ResponseDTO.Build("Exception while processing Http Method Request", exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    //---------------------------Book not Found invalid exception----------------------//
    @ExceptionHandler(BookException.class)
    public ResponseEntity<ResponseDTO> handleBusinessException(BookException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error Message", exception.getMessage());
        ResponseDTO responseDTO = ResponseDTO.Build("Exception while processing Http Method Request", exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }
    //---------------------------Cart not Found invalid exception----------------------//
    @ExceptionHandler(CartException.class)
    public ResponseEntity<ResponseDTO> handleBusinessException(CartException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error Message", exception.getMessage());
        ResponseDTO responseDTO = ResponseDTO.Build("Exception while processing Http Method Request", exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    //---------------------------Cart not Found invalid exception----------------------//
    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ResponseDTO> handleBusinessException(OrderException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error Message", exception.getMessage());
        ResponseDTO responseDTO = ResponseDTO.Build("Exception while processing Http Method Request", exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }
    //---------------------------Jwt invalid exception----------------------//
    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<ResponseDTO> methodArgumentException(JWTDecodeException exception) {
        Map<String, String> errorMap = new HashMap<>();

        ResponseDTO responseDTO = ResponseDTO.Build("Method parameters invalid while processing Http Method Request", exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
    //---------------------------invalid date exception----------------------//
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("startDate","Should have a date in format of dd MMM yyyy");
        ResponseDTO responseDTO =  ResponseDTO.Build("Exception while processing Http Method Request",errorMap);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}