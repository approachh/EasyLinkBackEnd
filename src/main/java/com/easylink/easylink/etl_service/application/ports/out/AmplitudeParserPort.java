package com.easylink.easylink.etl_service.application.ports.out;

import com.easylink.easylink.etl_service.domain.AmplitudeRawEvent;

import java.io.File;
import java.util.List;


public interface AmplitudeParserPort {
    List<AmplitudeRawEvent> parse(File zipFile);
}
