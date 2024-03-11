/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */

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
public class Station {
    private int id;
    private String stationName;

    public Station(String stationName) {
        this.stationName = stationName;
    }
    
}
