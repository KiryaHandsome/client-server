package ru.clevertec.entity;

import java.util.Objects;

public abstract class AbstractEntity {
    private Integer value;

    public AbstractEntity(Integer value)  {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "AbstractEntity{" +
                "value=" + value +
                '}';
    }
}
