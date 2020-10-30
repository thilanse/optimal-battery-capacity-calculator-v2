package com.company.fyp;

import com.company.fyp.datasource.CSVLoader;
import com.company.fyp.model.Battery;
import com.company.fyp.model.TOUData;
import com.company.fyp.model.TimeData;
import com.company.fyp.service.BatterySimulationService;

import java.util.List;
import java.util.Objects;

public class MainBatteryApplication
{
    public static void main( String[] args )
    {
        String filePath = Objects.requireNonNull( MainBatteryApplication.class.getClassLoader().getResource( "data/july/1.csv" ) ).getPath();
        List<TimeData> timeSeries = CSVLoader.loadFromCSV( filePath );

        TOUData timeOfUseData = new TOUData( timeSeries );

        Battery battery = new Battery();
        battery.setAmpHours( 200 );
        battery.setRatedVoltage( 24 );

        float dischargeTime = BatterySimulationService.simulateBatteryDischarge( timeOfUseData, battery );

        System.out.println(dischargeTime);

    }
}
