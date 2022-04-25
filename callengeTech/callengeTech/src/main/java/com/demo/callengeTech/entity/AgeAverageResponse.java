package com.demo.callengeTech.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class AgeAverageResponse {

    @Getter
    @Setter
    public double ageAverage;

    @Getter
    @Setter
    private double standardDeviation;

}
