package com.RoundTabler.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RoundTablerRequest {
    private String scanType;
    private String dbType;
    private String dbServerAddress;
    private String dbUsername;
    private String dbPassword;
    private String dbName;
    private String tableName;
}
