package com.creditharmony.loan.test.excel.ex;
import java.util.Date;
public class Employees {
    private String name;
    private int age;
    private Double payment;
    private Double bonus;
    private Date birthDate;
    private Employees superior;
    public Employees(String name, int age, Double payment, Double bonus) {
        this.name = name;
        this.age = age;
        this.payment = payment;
        this.bonus = bonus;
    }
    public Employees(String name, int age, double payment, double bonus, Date birthDate) {
        this.name = name;
        this.age = age;
        this.payment = new Double(payment);
        this.bonus = new Double(bonus);
        this.birthDate = birthDate;
    }
    public Employees(String name, int age, double payment, double bonus) {
        this.name = name;
        this.age = age;
        this.payment = new Double(payment);
        this.bonus = new Double(bonus);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Double getPayment() {
        return payment;
    }
    public void setPayment(Double payment) {
        this.payment = payment;
    }
    public Double getBonus() {
        return bonus;
    }
    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public Employees getSuperior() {
        return superior;
    }
    public void setSuperior(Employees superior) {
        this.superior = superior;
    }
}