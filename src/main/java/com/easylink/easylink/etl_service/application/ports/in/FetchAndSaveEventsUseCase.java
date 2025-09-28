package com.easylink.easylink.etl_service.application.ports.in;

public interface FetchAndSaveEventsUseCase {
    void fetchAndSave(String start, String end);
}
