package com.company.fyp.service;

import com.company.fyp.model.Battery;
import com.company.fyp.model.DischargeCount;
import com.company.fyp.model.TOUData;
import com.company.fyp.model.TimeData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BatterySimulationService
{
    private static final float RATED_MAINS_VOLTAGE = 230f;
    private static final float PEUKERTS_EXPONENT = 1.17f;
    private static final float RATED_DISCHARGE_TIME = 20f;

    public static float simulateBatteryDischarge( TOUData timeOfUseData, Battery battery )
    {
        List<TimeData> peakData = timeOfUseData.getNightPeakData();

        List<DischargeCount> dischargeRateCounts = getDischargeRateMinutes( peakData );

        return calculateTotalDischargeTime( dischargeRateCounts, battery );
    }

    private static float calculateTotalDischargeTime( List<DischargeCount> dischargeRateCounts, Battery battery )
    {
        float totalDischargeTime = 0.0f;
        battery.setRemainingCapacity( battery.getAmpHours() );

        for ( DischargeCount dischargeCount : dischargeRateCounts )
        {
            if ( dischargeCount.getDischargeRate() <= 0.0f )
            {
                continue;
            }

            float currentDrawnFromBattery = dischargeCount.getDischargeRate() * ( RATED_MAINS_VOLTAGE / battery.getRatedVoltage() );
            float dischargeTime = calculateDischargeTimeFromPeukerts( currentDrawnFromBattery, battery.getRemainingCapacity() );

            if ( dischargeTime > dischargeCount.getNumOfMinutes() )
            {
                totalDischargeTime = totalDischargeTime + dischargeCount.getNumOfMinutes();
            }
            else
            {
                totalDischargeTime = totalDischargeTime + dischargeTime;
                break;
            }

            float reducedBatteryCapacity = calculateReductionInBatteryCapacity( currentDrawnFromBattery, dischargeCount.getNumOfMinutes() );
            battery.setRemainingCapacity( battery.getRemainingCapacity() - reducedBatteryCapacity );
        }

        return totalDischargeTime;
    }

    private static float calculateReductionInBatteryCapacity( float dischargeRate, float numOfMinutes )
    {
        float timeConsumed = numOfMinutes / 60;

        return ( float ) ( dischargeRate * RATED_DISCHARGE_TIME * Math.pow( timeConsumed / RATED_DISCHARGE_TIME, 1 / PEUKERTS_EXPONENT ) );
    }

    private static float calculateDischargeTimeFromPeukerts( float dischargeRate, float batteryCapacity )
    {
        return ( float ) ( RATED_DISCHARGE_TIME * Math.pow( batteryCapacity / ( dischargeRate * RATED_DISCHARGE_TIME ), PEUKERTS_EXPONENT ) * 60 );
    }

    private static List<DischargeCount> getDischargeRateMinutes( List<TimeData> peakData )
    {
        List<Float> kiloWatts = peakData.stream()
                                        .map( dataPoint -> round( dataPoint.getKiloWatts(), 1 ) )
                                        .collect( Collectors.toList() );

        int count = 1;
        float previousValue = kiloWatts.remove( 0 );
        List<DischargeCount> dischargeMinutes = new ArrayList<>();

        for ( float currentValue : kiloWatts )
        {
            if ( currentValue == previousValue )
            {
                count++;
            }
            else
            {
                dischargeMinutes.add( new DischargeCount( previousValue, count ) );
                count = 1;
            }

            previousValue = currentValue;

            if ( kiloWatts.indexOf( currentValue ) == kiloWatts.size() - 1 )
            {
                dischargeMinutes.add( new DischargeCount( currentValue, count ) );
            }
        }

        return dischargeMinutes;
    }

    private static float round( double value, int places )
    {
        if ( places < 0 ) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal( Double.toString( value ) );
        bd = bd.setScale( places, RoundingMode.HALF_UP );
        return bd.floatValue();
    }
}
