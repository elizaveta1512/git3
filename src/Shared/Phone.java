package Shared;

import java.io.Serializable;

public class Phone implements Serializable {
    private static final long serialVersionUID =
            5557573101675282136L;
    String label;
    String model;
    Float price;

    public Phone(String label, String model, Float price) {
        this.label = label;
        this.model = model;
        this.price = price;
    }

    public String getLabel() {
        return label;
    }

    public String getModel() {
        return model;
    }

    public Float getPrice() {
        return price;
    }
}
