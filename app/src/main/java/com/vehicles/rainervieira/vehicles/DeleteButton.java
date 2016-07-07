package com.vehicles.rainervieira.vehicles;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Rainer on 07/07/2016.
 */
public class DeleteButton extends Button {

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    private String carId = "null";

    public DeleteButton(Context context) {
        super(context);
    }

    public DeleteButton(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public DeleteButton(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }
}
