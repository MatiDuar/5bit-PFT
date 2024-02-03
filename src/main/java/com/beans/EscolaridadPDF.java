package com.beans;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.persistencia.dto.EscolaridadDTO;
import com.persistencia.entities.Usuario;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
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
	            Document document = new Document(PageSize.A4);
	            PdfWriter.getInstance(document, baos);
	            document.open();
	            Font fontEncabezado = new Font(Font.FontFamily.TIMES_ROMAN,24,Font.BOLD);
	            
	            Paragraph titulo = new Paragraph("UNIVERSIDAD CETU\n ESCOLARIDAD\n\n",fontEncabezado);
	            titulo.setAlignment(Element.ALIGN_CENTER);
	            
	            Font fontEstudiante = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.UNDERLINE | Font.BOLD);
	            Paragraph datosEstudianteTitulo = new Paragraph("DATOS DEL ESTUDIANTE: \n\n",fontEstudiante);
	            
	            Usuario user=ges.getUsuarioLogeado();
	            
	            Paragraph datosEstudiante = new Paragraph("Nombre: "+user.getNombre1()+"\n"
	            											+ "Apellido: "+user.getApellido1()+"\n"
	            											+ "Documento: "+user.getDocumento()+"\n\n");
	            
	            document.add(titulo);
	            document.add(datosEstudianteTitulo);
	            document.add(datosEstudiante);
	            
	            
	            // Crear una tabla con 3 columnas
	            PdfPTable table = new PdfPTable(8);
	
	            table.setWidthPercentage(100);
	            Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD);
	            
	          List<EscolaridadDTO> escolaridades = ges.getEscolaridad();
	            
	          
	            // Agregar encabezados de columna
	            table.addCell(new Paragraph("Evento",boldFont));
	            table.addCell(new Paragraph("Calificación",boldFont));
	            table.addCell(new Paragraph("Créditos",boldFont));
	            table.addCell(new Paragraph("Fecha Inicio",boldFont));
	            table.addCell(new Paragraph("Fecha Fin",boldFont));
	            table.addCell(new Paragraph("Semestre",boldFont));
	            table.addCell(new Paragraph("Modalidad",boldFont));
	            table.addCell(new Paragraph("ITR",boldFont));

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
