package com.easylink.easylink.etl_service.application.ports.out;

import java.io.File;

public interface AmplitudeExportPort {
    File fetch(String start, String end);
}
