package com.RoundTabler.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/*
 * Class/structure that contains and manages performance information
 * regarding scans
 */

public class PerformanceResult {

    public String TableName;
    public String TableColumn;
    public int rowsScanned;
    public int rowsMatched;
    public String MatchType;    //"PCIDSS or NACHA"
    public LocalDateTime ScanStarted;
    public LocalDateTime ScanFinished;
    public Double RowsPerSecond;

    public PerformanceResult() {
        super();
    }

    // Calculates performance as a measure of time to complete the task
    private void CalculatePerformance() {
        long elapsed = ChronoUnit.SECONDS.between(ScanStarted, ScanFinished);
        if (elapsed != 0) {
            Double dRows = Double.valueOf(rowsScanned);
            Double dElapsed = Double.valueOf(elapsed);
            RowsPerSecond = dRows / dElapsed;

        } else {
            RowsPerSecond = 0.0;
        }

        if ((RowsPerSecond == 0.0) && (rowsScanned > 0)) {
            RowsPerSecond = Double.valueOf(rowsScanned);
        }
    }


    public String toString() {
        this.CalculatePerformance();

        if (rowsMatched > 0) {
            // Yellow background with
            // emphasized text for a performance result with
            // at least one Row match
            return
                    "<TR><TD><STRONG><EM><SPAN STYLE=\"background-color: #FFFF00\">" + TableName + "</SPAN></EM></STRONG></TD>" +
                            "<TD><STRONG><EM><SPAN STYLE=\"background-color: #FFFF00\">" + TableColumn + "</SPAN></EM></STRONG></TD>" +
                            "<TD><STRONG><EM><SPAN STYLE=\"background-color: #FFFF00\">" + MatchType + "</SPAN></EM></STRONG></TD>" +
                            "<TD ALIGN=\"RIGHT\"><STRONG><EM><SPAN STYLE=\"background-color: #FFFF00\">" + String.format("%,d", rowsScanned) + "</SPAN></EM></STRONG></TD>" +
                            "<TD ALIGN=\"RIGHT\"><STRONG><EM><SPAN STYLE=\"background-color: #FFFF00\">" + String.format("%,d", rowsMatched) + "</SPAN></EM></STRONG></TD>" +
                            "<TD ALIGN=\"RIGHT\"><STRONG><EM><SPAN STYLE=\"background-color: #FFFF00\">" + String.format("%.0f", RowsPerSecond) + "</SPAN></EM></STRONG></TD>" +
                            "</TR>";
        } else {
            return
                    "<TR><TD>" + TableName + "</TD>" +
                            "<TD>" + TableColumn + "</TD>" +
                            "<TD>" + MatchType + "</TD>" +
                            "<TD ALIGN=\"RIGHT\">" + String.format("%,d", rowsScanned) + "</TD>" +
                            "<TD ALIGN=\"RIGHT\">" + String.format("%,d", rowsMatched) + "</TD>" +
                            "<TD ALIGN=\"RIGHT\">" + String.format("%.0f", RowsPerSecond) + "</TD>" +
                            "</TR>";
        }
    }
}