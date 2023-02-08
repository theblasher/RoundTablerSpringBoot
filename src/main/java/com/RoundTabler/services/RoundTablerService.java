package com.RoundTabler.services;

import com.RoundTabler.models.RoundTablerRequest;
import com.RoundTabler.services.scans.CommonScan;
import com.RoundTabler.utility.database.DBReader;
import com.RoundTabler.utility.database.ReaderMaker;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

import static com.RoundTabler.services.RoundTable.WriteResultsToHTMLFile;

@Service
public class RoundTablerService {
    public void doSomethingWithTheData(RoundTablerRequest roundTablerRequest) {
        System.out.println(roundTablerRequest);
    }

    public void setUpRoundTablerRequest(RoundTablerRequest roundTablerRequest) throws SQLException, ClassNotFoundException {
        Configuration config = new Configuration();

        DBReader reader;

        PerformanceSummary SummaryOfPerformance = new PerformanceSummary();
        ScanSummary SummaryOfScans = new ScanSummary();

        config.setType(roundTablerRequest.getScanType());
        config.setDbType(roundTablerRequest.getDbType());
        config.setServer(roundTablerRequest.getDbServerAddress());
        config.setUser(roundTablerRequest.getDbUsername());
        config.setPassword(roundTablerRequest.getDbPassword());
        config.setDatabase(roundTablerRequest.getDbName());
        config.setTable(roundTablerRequest.getTableName());

        // Initialize our database reader based on the configuration passed
        reader = ReaderMaker.getReader(config);

        // Standardize our scan type casing
        String scanType = config.getType().toUpperCase().trim();

        // Attempt to execute scans (PCI/NACHA based on config passed)
        CommonScan scan = new CommonScan(SummaryOfPerformance, SummaryOfScans, reader);
        try {
            scan.scanDB(scanType);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Should not write empty results
        // Checking performance because it records results even when no matches occur
        if (!SummaryOfPerformance.isEmpty()) {
            WriteResultsToHTMLFile(SummaryOfScans, SummaryOfPerformance, config);
        } else {
            new HTMLErrorOut("No content able to be scanned was found in the specified database/table.");
        }
    }
}
