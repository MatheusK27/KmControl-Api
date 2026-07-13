package com.matheus.servico;


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.matheus.entidades.entidades.Motoboy;
import com.matheus.entidades.entidades.RegistroKm;
import com.matheus.entidades.repositorio.RegistroKmRepositorio;
import com.matheus.infra.RegraDeNegocioException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RelatorioPdfService {

    private final RegistroKmRepositorio registroKmRepositorio;

    public RelatorioPdfService(RegistroKmRepositorio registroKmRepositorio) {
        this.registroKmRepositorio = registroKmRepositorio;
    }
        public byte[] gerarRelatorio(Long motoboyId, int ano, int mes) {

            LocalDate inicio = LocalDate.of(ano, mes, 1);
            LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());

            List<RegistroKm> registros =
                    registroKmRepositorio.buscarRelatorioMensal(motoboyId, inicio, fim);

            if (registros.isEmpty()) {
                throw new RegraDeNegocioException("Nenhum registro encontrado para este período.");
            }

            try {

                ByteArrayOutputStream out = new ByteArrayOutputStream();

                Document document = new Document(PageSize.A4);

                PdfWriter.getInstance(document, out);

                document.open();

                Font titulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);

                Paragraph p = new Paragraph("RELATÓRIO MENSAL DE KM", titulo);
                p.setAlignment(Element.ALIGN_CENTER);

                document.add(p);
                document.add(new Paragraph(" "));

                Motoboy motoboy = registros.get(0).getMotoboy();

                document.add(new Paragraph("Motoboy: " + motoboy.getNome()));
                document.add(new Paragraph("Período: " + mes + "/" + ano));
                document.add(new Paragraph(" "));

                PdfPTable tabela = new PdfPTable(4);
                tabela.setWidthPercentage(100);

                tabela.addCell("Data");
                tabela.addCell("KM Entrada");
                tabela.addCell("KM Final");
                tabela.addCell("KM Rodado");

                int totalKm = 0;

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                for (RegistroKm registro : registros) {

                    Integer kmRodado = registro.getTotalKm() == null
                            ? 0
                            : registro.getTotalKm();

                    totalKm += kmRodado;

                    tabela.addCell(registro.getData().format(formatter));
                    tabela.addCell(String.valueOf(registro.getKmEntrada()));
                    tabela.addCell(String.valueOf(registro.getKmFim()));
                    tabela.addCell(String.valueOf(kmRodado));
                }

                document.add(tabela);

                document.add(new Paragraph(" "));
                document.add(new Paragraph("Dias trabalhados: " + registros.size()));
                document.add(new Paragraph("KM Total: " + totalKm + " km"));

                document.close();

                return out.toByteArray();

            } catch (Exception e) {
                throw new RuntimeException("Erro ao gerar PDF.", e);
            }
        }

    }

