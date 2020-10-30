package com.company.fyp.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TOUData
{
    private List<TimeData> timeSeriesData;
    private List<TimeData> offPeakData;
    private List<TimeData> dayPeakData;
    private List<TimeData> nightPeakData;
    private float offPeakEnergy;
    private float dayPeakEnergy;
    private float nightPeakEnergy;
    private float totalEnergy;


    public TOUData( List<TimeData> timeData )
    {
        this.timeSeriesData = new ArrayList<>( timeData );
        this.offPeakData = new ArrayList<>();
        this.dayPeakData = new ArrayList<>();
        this.nightPeakData = new ArrayList<>();

        timeData.forEach( dataPoint ->
        {
            if ( checkOffPeakTime( dataPoint ) ) {
                this.offPeakData.add( dataPoint );
            }
            if ( checkDayPeakTime( dataPoint ) ) {
                this.dayPeakData.add( dataPoint );
            }
            if ( checkNightPeakTime( dataPoint ) ) {
                this.nightPeakData.add( dataPoint );
            }
        } );

        this.offPeakEnergy = calculateEnergy( this.offPeakData );
        this.dayPeakEnergy = calculateEnergy( this.dayPeakData );
        this.nightPeakEnergy = calculateEnergy( this.nightPeakData );
        this.totalEnergy = calculateEnergy( this.timeSeriesData );
    }

    private float calculateEnergy( List<TimeData> timeSeriesData )
    {
        int numOfHours = timeSeriesData.size() / 60;

        float averagePower = ( float ) timeSeriesData.stream().mapToDouble( TimeData::getKiloWatts ).sum() / timeSeriesData.size();

        return averagePower * numOfHours;
    }

    private boolean checkNightPeakTime( TimeData dataPoint )
    {
        return dataPoint.getTime().isAfter( LocalTime.of( 18, 29, 59 ) ) &&
                dataPoint.getTime().isBefore( LocalTime.of( 22, 30, 0 ) );
    }

    private boolean checkDayPeakTime( TimeData dataPoint )
    {
        return dataPoint.getTime().isAfter( LocalTime.of( 5, 29, 59 ) ) &&
                dataPoint.getTime().isBefore( LocalTime.of( 18, 30, 0 ) );
    }

    private boolean checkOffPeakTime( TimeData dataPoint )
    {
        return ( dataPoint.getTime().isAfter( LocalTime.of( 22, 29, 59 ) ) &&
                dataPoint.getTime().isBefore( LocalTime.of( 23, 59, 59 ) ) ) ||
                dataPoint.getTime().equals( LocalTime.of( 0, 0, 0 ) ) ||
                ( dataPoint.getTime().isAfter( LocalTime.of( 0, 0, 0 ) ) &&
                        dataPoint.getTime().isBefore( LocalTime.of( 5, 30, 0 ) ) );
    }

    public List<TimeData> getTimeSeriesData()
    {
        return timeSeriesData;
    }

    public void setTimeSeriesData( List<TimeData> timeSeriesData )
    {
        this.timeSeriesData = timeSeriesData;
    }

    public List<TimeData> getOffPeakData()
    {
        return offPeakData;
    }

    public void setOffPeakData( List<TimeData> offPeakData )
    {
        this.offPeakData = offPeakData;
    }

    public List<TimeData> getDayPeakData()
    {
        return dayPeakData;
    }

    public void setDayPeakData( List<TimeData> dayPeakData )
    {
        this.dayPeakData = dayPeakData;
    }

    public List<TimeData> getNightPeakData()
    {
        return nightPeakData;
    }

    public void setNightPeakData( List<TimeData> nightPeakData )
    {
        this.nightPeakData = nightPeakData;
    }

    public float getOffPeakEnergy()
    {
        return offPeakEnergy;
    }

    public void setOffPeakEnergy( float offPeakEnergy )
    {
        this.offPeakEnergy = offPeakEnergy;
    }

    public float getDayPeakEnergy()
    {
        return dayPeakEnergy;
    }

    public void setDayPeakEnergy( float dayPeakEnergy )
    {
        this.dayPeakEnergy = dayPeakEnergy;
    }

    public float getNightPeakEnergy()
    {
        return nightPeakEnergy;
    }

    public void setNightPeakEnergy( float nightPeakEnergy )
    {
        this.nightPeakEnergy = nightPeakEnergy;
    }

    public float getTotalEnergy()
    {
        return totalEnergy;
    }

    public void setTotalEnergy( float totalEnergy )
    {
        this.totalEnergy = totalEnergy;
    }
}
