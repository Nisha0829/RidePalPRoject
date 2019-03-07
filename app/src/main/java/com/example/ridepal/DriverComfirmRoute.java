package com.example.ridepal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class DriverComfirmRoute extends AppCompatActivity {

    private TextView seekMiles;
    private SeekBar searchMiles;
    private String currentMiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_comfirm_route);

        seekMiles = (TextView) findViewById(R.id.miles);
        searchMiles = (SeekBar) findViewById(R.id.milesbar);
        currentMiles = searchMiles.getProgress() + " Miles";
        seekMiles.setText(currentMiles);
        searchMiles.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentMiles = searchMiles.getProgress() + " Miles";
                seekMiles.setText(currentMiles);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
