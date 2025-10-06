package com.easylink.easylink.etl_service.domain;

import java.time.Instant;

public record AmplitudeRawEvent (String userId,
                                 String eventType,
                                 String insertId,
                                 Instant eventTime,
                                 Instant  serverUploadTime,
                                 String userProperties,
                                 String eventProperties,
                                 String offerId
                                 ){}
