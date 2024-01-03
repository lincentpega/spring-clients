package com.lincentpega.restclients;

public class Response {
    private final Integer length;
    private final String name;

    public Response(Integer length, String name) {
        this.length = length;
        this.name = name;
    }

    public Integer getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Penis{" +
                "length=" + length +
                ", name='" + name + '\'' +
                '}';
    }
}
