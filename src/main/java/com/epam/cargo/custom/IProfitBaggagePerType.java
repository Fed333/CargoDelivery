package com.epam.cargo.custom;

import com.epam.cargo.entity.BaggageType;

public interface IProfitBaggagePerType {

    BaggageType getType();

    Long getProfit();
}
