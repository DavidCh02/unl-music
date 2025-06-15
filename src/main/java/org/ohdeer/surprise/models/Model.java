package org.ohdeer.surprise.models;


public class Model {
    private int value;

    public Model(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Model model = (Model) obj;
        return value == model.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}