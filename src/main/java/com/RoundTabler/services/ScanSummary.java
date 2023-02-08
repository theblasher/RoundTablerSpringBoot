package com.RoundTabler.services;

import java.util.ArrayList;

/*
 * Class/structure that contains all ScanResults
 */

public class ScanSummary {

    private final ArrayList<ScanResult> ScannerResults = new ArrayList<ScanResult>();

    public ScanSummary() {
        super();
    }

    public void addResult(ScanResult NewResult) {
        ScannerResults.add(NewResult);
    }

    public String toString() {
        StringBuilder temp;
        int index;
        temp = new StringBuilder();

        for (index = 0; index < ScannerResults.size(); index++) {
            temp.append(ScannerResults.get(index).toString() + "\n");
        }

        return temp.toString();
    }
}