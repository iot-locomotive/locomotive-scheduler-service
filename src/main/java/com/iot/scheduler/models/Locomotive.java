package com.iot.scheduler.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Locomotive {
    private UUID locomotiveCode;
    private String locomotiveName;
    private String locomotiveDimension;
    private int status;
    private String time;
}
