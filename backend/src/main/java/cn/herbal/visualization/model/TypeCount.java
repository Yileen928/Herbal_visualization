package cn.herbal.visualization.model;

import java.io.Serializable;

public class TypeCount implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;
    private int count;

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
