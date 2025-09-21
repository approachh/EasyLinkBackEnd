package com.easylink.easylink.etl.domain;

public record AmplitudeRawEvent (String userId,
                                 String eventType,
                                 String insertId,
                                 long serverUploadTime,
                                 String userProperties
                                 ){}
