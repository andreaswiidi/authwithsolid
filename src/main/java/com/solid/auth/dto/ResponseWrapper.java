package com.solid.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.solid.auth.util.HelperUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class ResponseWrapper <T> {
    private String message;
    private T data;
    private Date timestamp = new Date();

    public ResponseWrapper<T> success() {
        this.message = "Success";

        return this;
    }

    public ResponseWrapper<T> success(T t) {
        this.message = "Success";

        if (HelperUtil.isArrayOrCollection(t)) {
            Map<String, Object> content = new HashMap<>();
            content.put("content", t);
            this.data = (T) content;
        } else {
            this.data = t;
        }

        return this;
    }

    public ResponseWrapper<T> fail(String message) {
        this.message = message;

        return this;
    }
//
//    public ResponseWrapper<T> fail(Map<String,String> message,T t) {
//        ArrayList<Map<String, String>> listMessage = new ArrayList<>();
//        listMessage.add(message);
//
//        this.data = (T) message;
//
//        if (HelperUtil.isArrayOrCollection(t)) {
//            Map<String, Object> content = new HashMap<>();
//            content.put("content", listMessage);
//            this.data = (T) content;
//        } else {
//            this.data = t;
//        }
//
//        return this;
//    }
}
