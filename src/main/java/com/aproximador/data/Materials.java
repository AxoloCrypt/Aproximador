package com.aproximador.data;

import java.math.BigDecimal;

public class Materials extends Record<Materials>{

    public Materials() {}
    public Materials(String name, BigDecimal unitCost, String description) {
        super(name, unitCost, description);
    }


}
