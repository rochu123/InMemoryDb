package org.example;

public class Pair<M, N> {
    private final M key;
    private final N value;

    public Pair(M key, N value) {
        this.key = key;
        this.value = value;
    }

    public M getKey() {
        return this.key;
    }

    public N getValue() {
        return this.value;
    }
}
