package com.edstem.kakfa.exceljsonkafka.service;

import org.apache.poi.ss.usermodel.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


@Service
public class ExcelJsonKafkaProducerService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private final String kafkaTopic = "excel_json_converter";
    public String getJsonFromExcel(MultipartFile file) throws IOException {
        Workbook workbook;
        try (InputStream inputStream = file.getInputStream()) {
            workbook = WorkbookFactory.create(inputStream);
        }
        JSONArray jsonArray = new JSONArray();
        Sheet sheet = workbook.getSheetAt(0);
        Row headerRow = sheet.getRow(0);


        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row currentRow = sheet.getRow(rowIndex);
            if (currentRow != null) {
                Map<String, String> rowData = new HashMap<>();
                for (int cellIndex = 0; cellIndex < headerRow.getLastCellNum(); cellIndex++) {
                    Cell currentCell = currentRow.getCell(cellIndex);
                    if (currentCell != null) {
                        String header = headerRow.getCell(cellIndex).toString();
                        String value = currentCell.toString();
                        rowData.put(header, value);
                    }
                }
                jsonArray.put(rowData);
            }
        }
        String json =  jsonArray.toString();

        Message<String> message = MessageBuilder
                .withPayload(json)
                .setHeader(KafkaHeaders.TOPIC, kafkaTopic)
                .build();
        kafkaTemplate.send(message);
        return json.toString();
    }

}
