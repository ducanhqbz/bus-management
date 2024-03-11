/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;
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
public class Route {
    private int id;
    private String startPoint;
    private String endPoint;
    private String startTime;
    private String endTime;
    private int frequency;
    private int busID;
    private String name;
    private int price;
    private List<RoutePassesStation> routePassesStation;

    public Route(int id, String startPoint, String endPoint, String startTime, String endTime, int frequency, String name, int price) {
        this.id = id;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startTime = startTime;
        this.endTime = endTime;
        this.frequency = frequency;
        this.name = name;
        this.price = price;
    }

    public Route(int id, String startPoint, String endPoint, String startTime, String endTime, int frequency) {
        this.id = id;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startTime = startTime;
        this.endTime = endTime;
        this.frequency = frequency;
    }

    public Route(String startPoint, String endPoint, String startTime, String endTime, int frequency) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startTime = startTime;
        this.endTime = endTime;
        this.frequency = frequency;
    }
    
    
}
