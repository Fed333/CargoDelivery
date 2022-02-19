package com.epam.cargo.util;

import com.epam.cargo.entity.WeightFare;

import java.util.List;

public class CsvFileWeightFareReader {

    public static List<WeightFare> readWeightFaresCsv(String filename){
        return CsvFileReader.readCsv(filename, (String[] p) -> WeightFare.builder()
                .id(Long.parseLong(p[0]))
                .price(Double.parseDouble(p[1]))
                .weightFrom(Integer.parseInt(p[2]))
                .weightTo(Integer.parseInt(p[3])).build()
        );
    }
}
