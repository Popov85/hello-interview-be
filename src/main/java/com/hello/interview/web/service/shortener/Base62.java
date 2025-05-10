package com.hello.interview.web.service.shortener;

public interface Base62 {
    String encode(String original);
    String decode(String shortened);
}
