package com.easylink.easylink.etl.application.ports.out;

import java.io.File;

public interface AmplitudeExportPort {
    File fetch(String start, String end);
}
