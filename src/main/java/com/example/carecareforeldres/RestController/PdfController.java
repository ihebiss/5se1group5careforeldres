package com.example.carecareforeldres.RestController;

import com.example.carecareforeldres.Service.PdfPatient;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class PdfController {
    private final PdfPatient pdfPatient;

   /*@GetMapping("/export-pdf/{id}")
    public void exportPdf(HttpServletResponse response, @PathVariable Integer id) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=fiche_patient.pdf");

        pdfPatient.export(response, id);
    }*/
    @GetMapping("/export-pdf/{id}")
    public void exportPdf(HttpServletResponse response, @PathVariable Integer id) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=fiche_patient.pdf");

        pdfPatient.export(response, id);
    }
}
