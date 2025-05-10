package com.hello.interview.web.service.shortener;

import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class CounterImpl implements Counter {

    private final AtomicReference<BigInteger> counter = new AtomicReference<>(BigInteger.ONE);

    @Override
    public String getNextId() {
        BigInteger nextInteger =
                counter.getAndUpdate(num -> num.add(BigInteger.ONE));
        return String.valueOf(nextInteger);
    }
}
