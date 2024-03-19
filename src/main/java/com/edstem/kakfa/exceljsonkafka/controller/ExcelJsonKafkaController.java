package com.edstem.kakfa.exceljsonkafka.controller;

import com.edstem.kakfa.exceljsonkafka.service.ExcelJsonKafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/parse")
public class ExcelJsonKafkaController {
    private final ExcelJsonKafkaProducerService excelJsonKafkaProducerService;

    @Autowired
    public ExcelJsonKafkaController(ExcelJsonKafkaProducerService excelJsonKafkaProducerService) {
        this.excelJsonKafkaProducerService = excelJsonKafkaProducerService;
    }

    @PostMapping("/convert")
    public String uploadExcelAndConvertToJson(@RequestParam("file") MultipartFile file) throws IOException {
        return excelJsonKafkaProducerService.getJsonFromExcel(file);     }

}
