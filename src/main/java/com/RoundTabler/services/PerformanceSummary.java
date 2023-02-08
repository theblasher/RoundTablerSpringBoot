package com.RoundTabler.services;

import java.util.ArrayList;

/*
 * Class/structure that contains all PerformanceResults
 */

public class PerformanceSummary {

    private final ArrayList<PerformanceResult> performanceResults = new ArrayList<>();

    public PerformanceSummary() {
        super();
    }

    public void addResult(PerformanceResult NewResult) {
        performanceResults.add(NewResult);
    }

    public String toString() {
        StringBuilder temp;
        int index;
        temp = new StringBuilder();

        for (index = 0; index < performanceResults.size(); index++) {
            temp.append(performanceResults.get(index).toString()).append("\n");
        }

        return temp.toString();
    }

    public String getSummaryRow() {
        StringBuilder temp;
        long rowsScanned = 0;
        long rowsMatched = 0;
        int index;
        temp = new StringBuilder();

        temp.append("<TR><TH COLSPAN=\"3\">Totals</TH>");

        for (index = 0; index < performanceResults.size(); index++) {
            rowsScanned += performanceResults.get(index).rowsScanned;
            rowsMatched += performanceResults.get(index).rowsMatched;
        }

        temp.append("<TH ALIGN=\"RIGHT\">" + String.format("%,d", rowsScanned) + "</TH>");
        temp.append("<TH ALIGN=\"RIGHT\">" + String.format("%,d", rowsMatched) + "</TH>");
        temp.append("<TH></TH>");

        temp.append("</TR>");
        temp.append("\n");

        return temp.toString();
    }

    public Boolean isEmpty() {
        return performanceResults.isEmpty();
    }
}

