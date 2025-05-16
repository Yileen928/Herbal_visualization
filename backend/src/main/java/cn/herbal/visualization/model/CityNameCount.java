package cn.herbal.visualization.model;

import java.io.Serializable;

public class CityNameCount implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cityPrefix;
    private int count;
    private String job;

    // Getters and Setters
    public String getCityPrefix() {
        return cityPrefix;
    }

    public void setCityPrefix(String cityPrefix) {
        this.cityPrefix = cityPrefix;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
