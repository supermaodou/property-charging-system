package com.maodou.property.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 10. 统一响应结果
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "成功", data);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }
}