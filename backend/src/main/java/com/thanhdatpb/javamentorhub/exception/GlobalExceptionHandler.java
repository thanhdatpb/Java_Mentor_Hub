package com.thanhdatpb.javamentorhub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler — bắt tất cả exception trong app, trả về JSON thống nhất.
 *
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 * → Áp dụng cho tất cả @RestController trong project.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ───────────────────────────────────────────────────────────
    // Helper: Tạo response body thống nhất
    // ───────────────────────────────────────────────────────────

    /**
     * Tạo Map response chuẩn:
     * {
     *   "status": 404,
     *   "message": "User not found with id: 5",
     *   "timestamp": "2024-01-01T10:00:00"
     * }
     */
    private Map<String, Object> buildErrorBody(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("message", message);
        body.put("timestamp", LocalDateTime.now().toString());
        return body;
    }

    // ───────────────────────────────────────────────────────────
    // 1. Resource không tìm thấy → 404
    // ───────────────────────────────────────────────────────────

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildErrorBody(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    // ───────────────────────────────────────────────────────────
    // 2. Validation lỗi (vd: @NotBlank, @Email) → 400
    // Xảy ra khi @Valid kiểm tra DTO request thất bại
    // ───────────────────────────────────────────────────────────

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Thu thập tất cả field lỗi
        // vd: { "email": "must not be blank", "username": "size must be between 3 and 50" }
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> body = buildErrorBody(HttpStatus.BAD_REQUEST, "Validation failed");
        body.put("errors", fieldErrors); // Thêm chi tiết field lỗi
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // ───────────────────────────────────────────────────────────
    // 3. Sai username/password khi login → 401
    // ───────────────────────────────────────────────────────────

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(buildErrorBody(HttpStatus.UNAUTHORIZED, "Invalid username or password"));
    }

    // ───────────────────────────────────────────────────────────
    // 4. Không có quyền truy cập (vd: user gọi admin API) → 403
    // ───────────────────────────────────────────────────────────

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(buildErrorBody(HttpStatus.FORBIDDEN, "Access denied. You don't have permission."));
    }

    // ───────────────────────────────────────────────────────────
    // 5. IllegalArgumentException (vd: username đã tồn tại) → 400
    // ───────────────────────────────────────────────────────────

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildErrorBody(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    // ───────────────────────────────────────────────────────────
    // 6. Catch-all: Mọi exception khác không được xử lý → 500
    // ───────────────────────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorBody(HttpStatus.INTERNAL_SERVER_ERROR,
                        "An unexpected error occurred. Please try again later."));
    }
}
