package com.example.vyrwu.agillicDemoMvn.Account;

import org.springframework.http.HttpStatus;

public interface HttpResponses {
    String OK = "";
    String BAD_REQUEST = "";
    String INTERNAL_SERVER_ERROR = "";
    static String getFor(HttpStatus code) {
        int codeValue = code.value();
        switch (codeValue) {
            case 200: return OK;
            case 400: return BAD_REQUEST;
            default: return INTERNAL_SERVER_ERROR;
        }

    }
}
