package ru.clevertec.entity;

import java.math.BigInteger;

public abstract class AbstractEntity {
    private BigInteger value;

    public AbstractEntity(BigInteger value)  {
        this.value = value;
    }

    public BigInteger getValue() {
        return value;
    }
}
