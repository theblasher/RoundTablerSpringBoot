package com.RoundTabler.services;

import lombok.Getter;

import java.util.ArrayList;

/*
 * Class/structure that contains all ScanResults
 */
@Getter
public class ScanSummary {

    private final ArrayList<ScanResult> scanResults = new ArrayList<>();

    public ScanSummary() {
        super();
    }

    public void addResult(ScanResult newResult) {
        scanResults.add(newResult);
    }

    public String toString() {
        StringBuilder temp = new StringBuilder();

        for (ScanResult scanResult : scanResults) {
            temp.append(scanResult.toString()).append("\n");
        }

        return temp.toString();
    }
}