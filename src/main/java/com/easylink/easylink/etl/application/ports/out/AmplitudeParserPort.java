package com.easylink.easylink.etl.application.ports.out;

import com.easylink.easylink.etl.domain.AmplitudeRawEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;


public interface AmplitudeParserPort {
    List<AmplitudeRawEvent> parse(File zipFile);
}
