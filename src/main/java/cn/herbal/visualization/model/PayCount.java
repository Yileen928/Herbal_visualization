package cn.herbal.visualization.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayCount implements Serializable {

    private static final long serialVersionUID = 1L;

    private String salaryType;
    private BigDecimal salary;
    private int count;
    private String job;

    // Getters and Setters
    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
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