package com.lucas.matches.api.v1.exception;

import java.util.Objects;

public class APIError {
    public APIError() {
    }

    public APIError(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private String errorMsg;
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        APIError apiError = (APIError) o;
        return Objects.equals(errorMsg, apiError.errorMsg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorMsg);
    }

    @Override
    public String toString() {
        return "APIError{" +
                "errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
