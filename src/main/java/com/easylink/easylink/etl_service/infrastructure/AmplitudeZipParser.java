package com.easylink.easylink.etl_service.infrastructure;

import com.easylink.easylink.etl_service.application.ports.out.AmplitudeParserPort;
import com.easylink.easylink.etl_service.domain.AmplitudeRawEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


@Component
@RequiredArgsConstructor
public class AmplitudeZipParser implements AmplitudeParserPort {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<AmplitudeRawEvent> parse(File zipFile) {
        List<AmplitudeRawEvent> events = new ArrayList<>();

        try(ZipFile zf = new ZipFile(zipFile)){

            for(ZipEntry entry: Collections.list(zf.entries())){

                try(InputStream is = zf.getInputStream(entry);
                    GZIPInputStream gis = new GZIPInputStream(is);
                    BufferedReader br = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8))){

                    String line;
                    while((line = br.readLine())!=null){

                        JsonNode node = mapper.readTree(line);

                        String offerId = node.path("event_properties").path("offerId").asText(null);
//
//                        System.out.println("offerID "+"  "+node.path("event_type").asText(null)+" "+offerId);

                        System.out.println("RAW EVENT: " + node.toPrettyString());


                        AmplitudeRawEvent are = new AmplitudeRawEvent(
                                node.path("user_id").asText(null),
                                node.path("event_type").asText(null),
                                node.path("insert_id").asText(null),
                                node.path("server_upload_time").asLong(0),
                                node.path("user_properties").toString(),
                                node.path("event_properties").toString(),
                                offerId
                        );

                        events.add(are);
                    }
                }
            }
            }catch (IOException e){
            throw new RuntimeException("Error parsing zip file",e);
        }
        return events;
    }
}
