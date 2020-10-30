package com.company.fyp.datasource;

import com.company.fyp.model.TimeData;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVLoader
{
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "HH:mm:ss" );

    public static List< TimeData > loadFromCSV( String file )
    {
        try {
            // Create an object of file reader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader( file );

            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder( filereader ).build();
            List< String[] > allData = csvReader.readAll();

            List< TimeData > timeSeries = new ArrayList<>();
            allData.forEach( dataEntry ->
            {
                LocalTime time = LocalTime.parse( dataEntry[ 0 ], formatter );
                float current = Float.parseFloat( dataEntry[ 1 ] );

                TimeData timeData = new TimeData( time, current );
                timeSeries.add( timeData );
            } );

            return timeSeries;

        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }
}
