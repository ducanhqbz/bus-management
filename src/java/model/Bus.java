/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author ADMIN
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Bus {
    private int id;
    private String name;
    private int price;
    private int routeID;

    public Bus(String name, int price, int routeID) {
        this.name = name;
        this.price = price;
        this.routeID = routeID;
    }
    
}
