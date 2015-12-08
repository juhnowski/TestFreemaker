/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.phreebie.testfreemarker;

/**
 *
 * @author Ilya
 */
public class Product {
    
    private String url;
    private String name;

    public String getUrl() {
        return url;
    }
    
    public String getName() {
        return name;
    }
    
    public Product(String url, String name){
        this.url = url;
        this.name = name;
    }
}
