package com.RoundTabler.services;

import com.RoundTabler.models.RoundTablerRequest;
import com.RoundTabler.services.scans.CommonScan;
import com.RoundTabler.utility.database.DBReader;
import com.RoundTabler.utility.database.ReaderMaker;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.RoundTabler.services.RoundTable.WriteResultsToHTMLFile;

@Service
public class RoundTablerService {
    public void doSomethingWithTheData(RoundTablerRequest roundTablerRequest) {
        System.out.println(roundTablerRequest);
    }

    public void setUpRoundTablerRequest(RoundTablerRequest roundTablerRequest) throws SQLException, ClassNotFoundException {
        Configuration config = new Configuration();

        DBReader reader;

        PerformanceSummary performanceSummary = new PerformanceSummary();
        ScanSummary summaryofscans = new ScanSummary();

        config.setScanType(roundTablerRequest.getScanType());
        config.setDbType(roundTablerRequest.getDbType());
        config.setServer(roundTablerRequest.getDbServerAddress());
        config.setUser(roundTablerRequest.getDbUsername());
        config.setPassword(roundTablerRequest.getDbPassword());
        config.setDatabase(roundTablerRequest.getDbName());
        config.setTableName(roundTablerRequest.getTableName());

        // Initialize our database reader based on the configuration passed
        reader = ReaderMaker.getReader(config);

        // Standardize our scan type casing
        String scanType = config.getScanType().toUpperCase().trim();

        // Attempt to execute scans (PCI/NACHA based on config passed)
        CommonScan scan = new CommonScan(performanceSummary, summaryofscans, reader);
        try {
            scan.scanDB(scanType);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Should not write empty results
        // Checking performance because it records results even when no matches occur
        if (!performanceSummary.isEmpty()) {
            WriteResultsToHTMLFile(summaryofscans, performanceSummary, config);
        } else {
            new HTMLErrorOut("No content able to be scanned was found in the specified database/table.");
        }
    }

    public void generateJSONToBePassed(Configuration configuration, ScanSummary scanSummary,
                                       PerformanceSummary performanceSummary){
        JSONObject jsonObject = new JSONObject();
        JSONObject configurationDetails = new JSONObject();
        configurationDetails.put("databaseName",configuration.getDatabase());
        configurationDetails.put("serverName",configuration.getServer());
        configurationDetails.put("scanType",configuration.getScanType());
        configurationDetails.put("tableName",configuration.getTableName());

        jsonObject.put("configuration", configurationDetails);

        JSONArray matchedScanSummary = new JSONArray();
        ArrayList<ScanResult> scanResultsList = scanSummary.getScanResults();

        for (int i = 0; i < scanResultsList.size(); i++){
            JSONObject rowMatch = new JSONObject();
            rowMatch.put("tableName", scanResultsList.get(i).TableName);
            rowMatch.put("tableColumn", scanResultsList.get(i).TableColumn);
            rowMatch.put("HTMLEmphasizedResult", scanResultsList.get(i).HTMLEmphasizedResult);
            rowMatch.put("matchType", scanResultsList.get(i).MatchType);
            rowMatch.put("confidenceLevel", String.valueOf(scanResultsList.get(i).ConfidenceLevel));
            rowMatch.put("matchResultRule", scanResultsList.get(i).MatchResultRule);
            matchedScanSummary.put(rowMatch);
        }

        jsonObject.put("matchedScanSummary", matchedScanSummary);

        JSONArray performanceScanSummary = new JSONArray();
        ArrayList<PerformanceResult> performanceResultsList = performanceSummary.getPerformanceResults();

        for (int i = 0; i < performanceResultsList.size(); i++){
            JSONObject rowMatch = new JSONObject();
            rowMatch.put("tableName", performanceResultsList.get(i).TableName);
            rowMatch.put("columnName", performanceResultsList.get(i).TableColumn);
            rowMatch.put("scanType", performanceResultsList.get(i).MatchType);
            rowMatch.put("rowsScanned", String.valueOf(performanceResultsList.get(i).rowsScanned));
            rowMatch.put("rowsMatched", String.valueOf(performanceResultsList.get(i).rowsMatched));
            rowMatch.put("rowsPerSecond", performanceResultsList.get(i).RowsPerSecond.toString());
            performanceScanSummary.put(rowMatch);
        }

        jsonObject.put("performanceScanSummary", performanceScanSummary);

        System.out.println(jsonObject);
    }
}
