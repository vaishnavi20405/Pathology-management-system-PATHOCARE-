/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author vaish
 */

public class Test {
    private int test_id;
    private String test_name;
    private String description;
    private double price;

    public Test(int test_id, String test_name, String description, double price) {
        this.test_id = test_id;
        this.test_name = test_name;
        this.description = description;
        this.price = price;
    }
    
    public Test(){
        
    }

    // Getters and Setters
    public int getTestId() {
        return test_id;
    }

    public void setTestId(int test_id) {
        this.test_id = test_id;
    }

    public String getTestName() {
        return test_name;
    }

    public void setTestName(String test_name) {
        this.test_name = test_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}