package com.prettyshopbe.prettyshopbe.common;

public class OrderApiResponse {
    private final boolean success;
    private final String message;
    private final Integer id;

    public OrderApiResponse(boolean success, String message, Integer id) {
        this.success = success;
        this.message = message;
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Integer getId() {
        return id;
    }
}
