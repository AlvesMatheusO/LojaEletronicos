package com.loja;

public class Employee {
    
    private Long id;
    private String name;
    private String role;
    
    public Employee(int id2, String name2, String role2) {
    }
    public Long getId() {
        return id;
    }
    public void setId(int i) {
        this.id = (long) i;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setCargo(String string) {
    }
    public String getCargo() {
        return null;
    }
   
    
}
