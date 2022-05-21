package com.tienda.online.utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tienda.online.model.Producto;

public class ProductoExporterExcel {

	private XSSFWorkbook libro;
	private XSSFSheet hoja;
	
	private List<Producto> listaProductos;

	public ProductoExporterExcel(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
		libro = new XSSFWorkbook();
		hoja = libro.createSheet("Productos");
	}
	
	private void escribirCabeceraTabla() {
		Row fila = hoja.createRow(0);
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setBold(true);
		fuente.setFontHeight(16);
		estilo.setFont(fuente);
		
		Cell celda = fila.createCell(0);
		celda.setCellValue("ID");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(1);
		celda.setCellValue("Nombre");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(2);
		celda.setCellValue("Descripcion");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(3);
		celda.setCellValue("Categoria");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(4);
		celda.setCellValue("Precio");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(5);
		celda.setCellValue("Stock");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(6);
		celda.setCellValue("Fecha");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(7);
		celda.setCellValue("Impuesto");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(8);
		celda.setCellValue("Empleado");
		celda.setCellStyle(estilo);
		
	}
	
	private void escribirDatosTabla() {
		
		int numeroFilas = 1;
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		
		for (Producto producto : listaProductos) {
			Row fila = hoja.createRow(numeroFilas++);
			
			Cell celda = fila.createCell(0);
			celda.setCellValue(producto.getId());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(1);
			celda.setCellValue(producto.getNombre());
			hoja.autoSizeColumn(1);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(2);
			celda.setCellValue(producto.getDescripcion());
			hoja.autoSizeColumn(2);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(3);
			celda.setCellValue(producto.getCategoria());
			hoja.autoSizeColumn(3);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(4);
			celda.setCellValue(producto.getPrecio());
			hoja.autoSizeColumn(4);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(5);
			celda.setCellValue(producto.getStock());
			hoja.autoSizeColumn(5);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(6);
			celda.setCellValue(producto.getFechaAlta().toString());
			hoja.autoSizeColumn(6);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(7);
			celda.setCellValue(producto.getImpuesto());
			hoja.autoSizeColumn(7);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(8);
			celda.setCellValue(producto.getUsuario().getId());
			hoja.autoSizeColumn(8);
			celda.setCellStyle(estilo);
			
		}
	}
	
	
	public void exportar(HttpServletResponse response) throws IOException {
		escribirCabeceraTabla();
		escribirDatosTabla();
		
		ServletOutputStream outputStream = response.getOutputStream();
		libro.write(outputStream);
		
		libro.close();
		outputStream.close();
		
	}
}
