package com.matheus.controller;


import com.matheus.servico.RelatorioPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorio")
@RequiredArgsConstructor
public class RelatorioPdfController {

    private final RelatorioPdfService relatorioPdfService;


    @GetMapping("/pdf")
    public ResponseEntity<byte[]> gerarRelatorioPdf(
            @RequestParam Long motoboyId,
            @RequestParam Integer ano,
            @RequestParam Integer mes) {

        byte[] pdf = relatorioPdfService.gerarRelatorio(motoboyId, ano, mes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=relatorio.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
