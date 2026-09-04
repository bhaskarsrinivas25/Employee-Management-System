package com.employee.dto;

public class ReportDTO {

    private long totalEmployees;
    private Double averageSalary;
    private Double highestSalary;
    private Double lowestSalary;

    // Constructors
    public ReportDTO() {
    }

    public ReportDTO(long totalEmployees, Double averageSalary, Double highestSalary, Double lowestSalary) {
        this.totalEmployees = totalEmployees;
        this.averageSalary = averageSalary;
        this.highestSalary = highestSalary;
        this.lowestSalary = lowestSalary;
    }

    // Getters and Setters
    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public Double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(Double averageSalary) {
        this.averageSalary = averageSalary;
    }

    public Double getHighestSalary() {
        return highestSalary;
    }

    public void setHighestSalary(Double highestSalary) {
        this.highestSalary = highestSalary;
    }

    public Double getLowestSalary() {
        return lowestSalary;
    }

    public void setLowestSalary(Double lowestSalary) {
        this.lowestSalary = lowestSalary;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "totalEmployees=" + totalEmployees +
                ", averageSalary=" + averageSalary +
                ", highestSalary=" + highestSalary +
                ", lowestSalary=" + lowestSalary +
                '}';
    }
}
