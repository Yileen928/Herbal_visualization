package cn.herbal.visualization.model;

import java.io.Serializable;

public class ProvinceCount implements Serializable {

    private static final long serialVersionUID = 1L;

    private String province;
    private int count;

    // Getters and Setters
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}