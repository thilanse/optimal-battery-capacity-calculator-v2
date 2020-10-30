package com.company.fyp.model;

public class Battery
{
    private int ampHours;
    private float depthOfDischarge;
    private float ratedVoltage;
    private float price;
    private float remainingCapacity;

    public Battery()
    {
    }

    public int getAmpHours()
    {
        return ampHours;
    }

    public void setAmpHours( int ampHours )
    {
        this.ampHours = ampHours;
    }

    public float getDepthOfDischarge()
    {
        return depthOfDischarge;
    }

    public void setDepthOfDischarge( float depthOfDischarge )
    {
        this.depthOfDischarge = depthOfDischarge;
    }

    public float getRatedVoltage()
    {
        return ratedVoltage;
    }

    public void setRatedVoltage( float ratedVoltage )
    {
        this.ratedVoltage = ratedVoltage;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice( float price )
    {
        this.price = price;
    }

    public float getRemainingCapacity()
    {
        return remainingCapacity;
    }

    public void setRemainingCapacity( float remainingCapacity )
    {
        this.remainingCapacity = remainingCapacity;
    }
}
