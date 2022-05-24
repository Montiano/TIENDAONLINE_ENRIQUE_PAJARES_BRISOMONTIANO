package com.tienda.online.utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tienda.online.model.DetallePedido;

public class FacturaExporterPDF {

	private List<DetallePedido> listaDetalles;
	
	public FacturaExporterPDF(List<DetallePedido> listaDetalles) {
		super();
		this.listaDetalles = listaDetalles;
	}

	public void exportar(HttpServletResponse response) throws DocumentException, IOException{
		
		PdfWriter writer;
		Document documento = new Document(PageSize.A4, 20, 20, 70, 50);
		
		writer = PdfWriter.getInstance(documento, response.getOutputStream());
		
		writer.setPageEvent(new PDFHeaderFooter());
		
		documento.open();
		
		Paragraph paragraph = new Paragraph();
		paragraph.add("\n\n");
		documento.add(paragraph);
		
		PdfPTable tabla = new PdfPTable(4);
		
		PdfPCell celda = new PdfPCell();
		
		celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
		celda.setPadding(5);
		celda.setBorderWidth(1);
		
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		//fuente.setColor(Color.WHITE);
		
		celda.setPhrase(new Phrase("Producto", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Cantidad", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Precio", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Total", fuente));		
		tabla.addCell(celda);
		
		for (DetallePedido detallePedido : listaDetalles) {
			tabla.addCell(detallePedido.getNombre());
			tabla.addCell(String.valueOf(detallePedido.getUnidades()));
			tabla.addCell(String.valueOf(detallePedido.getPrecioUnidad()));
			tabla.addCell(String.valueOf(detallePedido.getTotal()));
			
		}
		
		documento.add(tabla);
		documento.close();
		writer.close();
		
//		try {
//	        File path = new File("./resources/salida.pdf");
//	        Desktop.getDesktop().open(path);
//	    } catch (IOException ex) {
//	        ex.printStackTrace();
//	    }
	}
}
