package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.Entity.Patient;
import com.example.carecareforeldres.Entity.Rdv;
import com.example.carecareforeldres.Repository.PatientRepository;
import com.example.carecareforeldres.Repository.RdvRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class PdfPatient {
    PatientRepository patientRepository;
    RdvRepository rdvRepository;

    public void export(HttpServletResponse response, Integer id) throws IOException, DocumentException {
        Patient patient = patientRepository.findById(id).get();
        List<Rdv> rdvs = rdvRepository.findRdvByPatient(patient.getIdpatient());

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font fontContent = FontFactory.getFont(FontFactory.HELVETICA, 12);

        // Title Section
        Paragraph title = new Paragraph("FICHE PATIENT", fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Table Section
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(80);
        table.setSpacingBefore(20);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);

        // Add table headers
        addHeaderCellToTable(table, "Nom", fontHeader);
        addHeaderCellToTable(table, "Status", fontHeader);
        addHeaderCellToTable(table, "Dates rendez-vous", fontHeader);

        // Add patient information
        addCellToTable(table, patient.getNom()+" "+patient.getPrenom(), patient.getTypatient().toString(), rdvs, fontContent);

        document.add(table);
        document.close();
    }

    private void addHeaderCellToTable(PdfPTable table, String label, Font font) {
        PdfPCell cellLabel = new PdfPCell(new Phrase(label, font));
        cellLabel.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cellLabel);
    }

    private void addCellToTable(PdfPTable table, String nom, String status, List<Rdv> rdvs, Font font) {
        PdfPCell cellNom = new PdfPCell(new Phrase(nom, font));
        PdfPCell cellStatus = new PdfPCell(new Phrase(status, font));
        PdfPCell cellDates = new PdfPCell();

        cellNom.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellStatus.setHorizontalAlignment(Element.ALIGN_CENTER);

        // Ajouter les dates de rendez-vous dans la même ligne sous la colonne "Dates rendez-vous"
        for (Rdv rdv : rdvs) {
            cellDates.addElement(new Phrase(rdv.getDateRDV().toString(), font));
        }

        table.addCell(cellNom);
        table.addCell(cellStatus);
        table.addCell(cellDates);
    }
}