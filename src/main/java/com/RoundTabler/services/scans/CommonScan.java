package com.RoundTabler.services.scans;

import com.RoundTabler.services.PerformanceResult;
import com.RoundTabler.services.PerformanceSummary;
import com.RoundTabler.services.ScanResult;
import com.RoundTabler.services.ScanSummary;
import com.RoundTabler.utility.database.DBReader;
import com.RoundTabler.utility.database.SchemaItems;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/*
 * Class responsible for managing scans
 * Depending on configuration, will run NACHA and PCI scans
 */

public class CommonScan {

    private final StringBuilder nsbResults;
    private PerformanceSummary nPerformanceScan;
    private ScanSummary pScanSummary;
    private DBReader nDBReader;

    public CommonScan() {
        super();
        this.nsbResults = new StringBuilder();
        nsbResults.append("\n");
    }

    public CommonScan(PerformanceSummary performanceSummary,
                      ScanSummary scans, DBReader databaseReader) {
        this();
        nPerformanceScan = performanceSummary;
        pScanSummary = scans;
        nDBReader = databaseReader;
    }

    // Scans the database column-by-column
    public void scanDB(String typeOfScan) throws SQLException {
        int currentConfidenceLevel = 0;
        String currentTable;
        String currentColumn;

        String currentRow;

        SchemaItems tablesAndColumns;

        // If we successfully are able to read the database schema, then begin reading column contents
        if (nDBReader.readSchema()) {
            // Create a list of scanners to use
            ArrayList<GenericScan> scanners = new ArrayList<>();

            // Populate the scanners list
            scanners.add(new NACHAScan());
            scanners.add(new PCIScan());

            if (Objects.equals(typeOfScan, "NACHA"))
                scanners.remove(1);
            else if (Objects.equals(typeOfScan, "PCI"))
                scanners.remove(0);


            // Get a list of columns of relevant data types
            tablesAndColumns = nDBReader.getSchemaItems();

            for (GenericScan scanner : scanners) {
                int index;
                String scanType = "";

                // For each column we have access to...
                for (index = 0; index < tablesAndColumns.size(); index++) {
                    // Progress bar printing

                    currentTable = tablesAndColumns.get(index).getTableName();
                    currentColumn = tablesAndColumns.get(index).getColumnName();

                    // Retrieve the array of rows in the column as strings
                    ArrayList<String> rowsData;
                    rowsData = nDBReader.readColumn(tablesAndColumns.get(index));

                    int rowindex;

                    // Begin measuring performance of scan
                    PerformanceResult currentResult;
                    currentResult = new PerformanceResult();
                    currentResult.TableName = currentTable;
                    currentResult.TableColumn = currentColumn;
                    currentResult.MatchType = scanType = scanner.scanType;
                    currentResult.rowsMatched = 0;
                    currentResult.rowsScanned = rowsData.size();
                    currentResult.ScanStarted = LocalDateTime.now();

                    // For each row in the current column we are in...
                    int rowCount = rowsData.size(); // If smartIterable, this is an inefficient operation
                    for (rowindex = 0; rowindex < rowCount; rowindex++) {
                        // Scan row for a confidence level of a match
                        currentRow = rowsData.get(rowindex);
                        currentConfidenceLevel = scanner.getConfidenceLevelMatch(currentRow);

                        if (currentConfidenceLevel > 0) {
                            AppendMatch(scanner, scanType, currentTable, currentColumn, currentRow, currentConfidenceLevel);
                            currentResult.rowsMatched++;
                        }
                    }

                    // Finalize performance metrics for this column
                    currentResult.ScanFinished = LocalDateTime.now();
                    nPerformanceScan.addResult(currentResult);
                }
            }
        }
    }

    private void AppendMatch(GenericScan scanner, String scanType, String currentTable, String currentColumn,
                             String currentRow, int currentConfidenceLevel) {
        ScanResult tResult;
        tResult = new ScanResult();
        tResult.ConfidenceLevel = currentConfidenceLevel;
        tResult.TableName = currentTable;
        tResult.MatchType = scanType;

        tResult.HTMLEmphasizedResult = insertStrongEmphasisInto(currentRow,
                scanner.getLastMatchStart(), scanner.getLastMatchEnd());

        tResult.MatchResultRule = scanner.getLastMatchDescription();

        tResult.TableColumn = currentColumn;
        pScanSummary.addResult(tResult);
    }

    /*
     * Returns a string with strong and emphasis tags surrounding the StartLocation
     * and EndLocation
     */
    private String insertStrongEmphasisInto(String MatchedRow, int StartLocation, int EndLocation) {
        StringBuilder tsb = new StringBuilder(MatchedRow);
        tsb.insert(EndLocation, "</SPAN></EM></STRONG>");
        tsb.insert(StartLocation, "<STRONG><EM><SPAN STYLE=\"background-color: #FFFF00\">");
        return tsb.toString();
    }
}
