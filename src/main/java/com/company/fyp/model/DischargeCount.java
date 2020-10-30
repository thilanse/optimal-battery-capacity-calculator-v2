package com.company.fyp.model;

public class DischargeCount
{
    private float dischargeRate;
    private int numOfMinutes;

    public DischargeCount( float dischargeRate, int numOfMinutes )
    {
        this.dischargeRate = dischargeRate;
        this.numOfMinutes = numOfMinutes;
    }

    public float getDischargeRate()
    {
        return dischargeRate;
    }

    public void setDischargeRate( float dischargeRate )
    {
        this.dischargeRate = dischargeRate;
    }

    public int getNumOfMinutes()
    {
        return numOfMinutes;
    }

    public void setNumOfMinutes( int numOfMinutes )
    {
        this.numOfMinutes = numOfMinutes;
    }
}
