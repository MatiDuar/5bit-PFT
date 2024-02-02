package com.beans;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.persistencia.dto.EscolaridadDTO;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Named("escolaridadPDF")
@RequestScoped
public class EscolaridadPDF {
	
	@Inject
	GestionPersona ges;

	 public static void main(String[] args) {
		 EscolaridadPDF generadorPDF = new EscolaridadPDF();
	        generadorPDF.generarPDF();
	    }

	 public void generarPDF() {
	        try {
	            // Crear un flujo de bytes para el PDF
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();

	            // Configurar el documento y el escritor de PDF
	            Document document = new Document();
	            PdfWriter.getInstance(document, baos);
	            document.open();

	            // Crear una tabla con 3 columnas
	            PdfPTable table = new PdfPTable(8);

	            
	          List<EscolaridadDTO> escolaridades = ges.getEscolaridad();
	            
	            // Agregar encabezados de columna
	            table.addCell("Evneto");
	            table.addCell("Calificacion");
	            table.addCell("Creditos");
	            table.addCell("Fecha Inicio");
	            table.addCell("Fecha Fin");
	            table.addCell("Semestre");
	            table.addCell("Modalidad");
	            table.addCell("ITR");

	            // Agregar filas
	            for (EscolaridadDTO es : escolaridades) {
	                table.addCell(es.getEventoId().toString());
	                table.addCell(es.getCalificacion().toString());
	                table.addCell(es.getCreditos().toString());
	                table.addCell(es.getFechaInicio().toString());
	                table.addCell(es.getFechaFin().toString());
	                table.addCell(es.getSemestre().toString());
	                table.addCell(es.getModalidad().toString());
	                table.addCell(es.getNombreItr().toString());

	            }

	            // Agregar la tabla al documento
	            document.add(table);

	            // Cerrar el documento
	            document.close();

	            // Configurar la respuesta HTTP para la descarga
	            FacesContext facesContext = FacesContext.getCurrentInstance();
	            ExternalContext externalContext = facesContext.getExternalContext();
	            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
	            response.reset();
	            response.setContentType("application/pdf");
	            response.setHeader("Content-Disposition", "attachment;filename=tabla.pdf");

	            // Configurar la salida del PDF al flujo de respuesta
	            response.getOutputStream().write(baos.toByteArray());
	            response.getOutputStream().flush();
	            response.getOutputStream().close();
	            facesContext.responseComplete();

	        } catch (DocumentException | IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	
}
