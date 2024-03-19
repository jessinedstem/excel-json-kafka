package com.edstem.kakfa.exceljsonkafka.service;

import org.apache.poi.ss.usermodel.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


@Service
public class ExcelJsonKafkaProducerService {
    public String getJsonFromExcel(MultipartFile file) throws IOException {
        Workbook workbook;
        try (InputStream inputStream = file.getInputStream()) {
            workbook = WorkbookFactory.create(inputStream);
        }

        Sheet sheet = workbook.getSheetAt(0);
        Row headerRow = sheet.getRow(0);

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[\n");

        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row currentRow = sheet.getRow(rowIndex);
            if (currentRow != null) {
                Map<String, String> rowData = new HashMap<>();
                Cell idCell = currentRow.getCell(0);
                Cell nameCell = currentRow.getCell(1);
                Cell salaryCell = currentRow.getCell(2);

                String id = idCell != null ? idCell.toString() : "";
                String name = nameCell != null ? nameCell.toString() : "";
                String salary = salaryCell != null ? salaryCell.toString() : "";

                rowData.put("ID", id);
                rowData.put("Employee Name", name);
                rowData.put("Salary", salary);

                jsonBuilder.append("  ").append(new JSONObject(rowData).toString());
                if (rowIndex < sheet.getLastRowNum()) {
                    jsonBuilder.append(",\n");
                }
            }
        }

        jsonBuilder.append("\n]");
        return jsonBuilder.toString();
    }
}
