package cn.herbal.visualization.model;

import java.io.Serializable;

public class WorkExperienceCount implements Serializable {

    private static final long serialVersionUID = 1L;

    private String workExperience;
    private int count;
    private String job;

    // Getters and Setters
    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
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
