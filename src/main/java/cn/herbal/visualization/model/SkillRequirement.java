package cn.herbal.visualization.model;

import java.io.Serializable;

public class SkillRequirement implements Serializable {

    private static final long serialVersionUID = 1L;

    private String skillRequirement;
    private int count;
    private String job;

    // Getters and Setters
    public String getSkillRequirement() {
        return skillRequirement;
    }

    public void setSkillRequirement(String skillRequirement) {
        this.skillRequirement = skillRequirement;
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