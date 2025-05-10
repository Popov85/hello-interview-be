package com.hello.interview.web.service.shortener;

import org.springframework.stereotype.Service;

@Service
public class Base62Impl implements Base62 {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final int BASE = BASE62.length();

    @Override
    public String encode(String original) {
        StringBuilder sb = new StringBuilder();
        int num = Integer.parseInt(original);
        do {
            sb.append(BASE62.charAt((int)(num % BASE)));
            num /= BASE;
        } while (num > 0);
        return sb.reverse().toString();
    }

    @Override
    public String decode(String shortened) {
        long num = 0;
        for (char c : shortened.toCharArray()) {
            num = num * BASE + BASE62.indexOf(c);
        }
        return String.valueOf(num);
    }
}
