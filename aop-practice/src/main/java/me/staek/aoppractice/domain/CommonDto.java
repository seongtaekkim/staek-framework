package me.staek.aoppractice.domain;

import java.util.Objects;

public class CommonDto<T> {
    private int statusCode;
    private T data;

    public int getStatusCode() {
        return statusCode;
    }

    public T getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonDto<?> commonDto = (CommonDto<?>) o;
        return statusCode == commonDto.statusCode && Objects.equals(data, commonDto.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, data);
    }

    public CommonDto(int statusCode) {
        this.statusCode = statusCode;
    }

    public CommonDto(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }
}
