package com.easylink.easylink.etl.application.ports.in;

public interface FetchAndSaveEventsUseCase {
    void fetchAndSave(String start, String end);
}
