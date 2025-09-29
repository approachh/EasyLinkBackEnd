package com.easylink.easylink.etl_service.domain;

public record AmplitudeRawEvent (String userId,
                                 String eventType,
                                 String insertId,
                                 long serverUploadTime,
                                 String userProperties,
                                 String eventProperties,
                                 String offerId
                                 ){}
