package com.thanhdatpb.javamentorhub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a requested resource is not found in the database.
 * @ResponseStatus → Spring tự động trả về HTTP 404 khi exception này bị throw.
 *
 * Cách dùng trong Service:
 *   User user = userRepository.findById(id)
 *       .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * @param resourceName  Tên entity bị thiếu, vd: "User", "Question"
     * @param fieldName     Trường dùng để tìm, vd: "id", "username"
     * @param fieldValue    Giá trị đã tìm, vd: 5, "thanhdatpb"
     *
     * Kết quả message: "User not found with id: 5"
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue));
    }
}
