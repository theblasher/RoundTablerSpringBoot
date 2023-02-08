package com.RoundTabler.services;


import org.junit.jupiter.api.Test;

public class RoundTablerServiceTest {
    RoundTablerService roundTablerService = new RoundTablerService();

    @Test
    void generateJSONToBePassedTest(){
        Configuration configuration = new Configuration();
        PerformanceSummary performanceSummary = new PerformanceSummary();
        PerformanceResult performanceResult = new PerformanceResult();
        performanceResult.RowsPerSecond = 3.0;
        performanceSummary.addResult(performanceResult);
        ScanSummary scanSummary = new ScanSummary();
        ScanResult scanResult = new ScanResult();
        scanSummary.addResult(scanResult);
        roundTablerService.generateJSONToBePassed(configuration,scanSummary,performanceSummary);
    }
}
