package com.cisco.regina.servicio.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.cisco.regina.modelo.Instituciones;
import com.cisco.regina.modelo.Proyectos;
import com.cisco.regina.modelo.Usuarios;
import com.cisco.regina.repositorio.InstitucionesRepositorio;
import com.cisco.regina.repositorio.ProyectosRepositorio;
import com.cisco.regina.repositorio.UsuariosRepositorio;
import com.cisco.regina.servicio.ProyectosServicio;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProyectosServicioImpl implements ProyectosServicio{

    @Autowired
    ProyectosRepositorio proyectosRepositorio;

    @Autowired
    InstitucionesRepositorio institucionesRepositorio;

    @Autowired
    UsuariosRepositorio usuariosRepositorio;

    @Override
    public List<Proyectos> buscarPorTermino(String termino, Integer pagina, Integer cantidad) {
        int nropagina = 0;
        if (pagina-1==-1) {
            nropagina = 0;
        } else {
            nropagina = pagina - 1;
        }
        Page<Proyectos> salidas = proyectosRepositorio.findByProyectoContainingIgnoreCaseOrderByProyecto(termino, PageRequest.of(nropagina, cantidad));
        return salidas.getContent();
    }

    @Override
    public Integer cantidadBuscarPorTermino(String termino) {
        return proyectosRepositorio.findByProyectoContainingIgnoreCaseOrderByProyecto(termino, null).getSize();
    }

    @Override
    public List<Proyectos> buscarTodos() {
        List<Proyectos> salidas = proyectosRepositorio.findAll();
        return salidas;
    }

    @Override
    public Proyectos buscarPorId(Long id) {
        Proyectos salida = proyectosRepositorio.findById(id).orElse(null);
        return salida;
    }

    @Override
    public void adicionar(Proyectos dato) {
        proyectosRepositorio.save(dato);
    }

    @Override
    public void modificar(Proyectos dato) {
        Proyectos entrada = proyectosRepositorio.findById(dato.getIdproyecto()).orElse(null);
        proyectosRepositorio.save(entrada);
    }

    @Override
    public void borrar(Long id) {
        proyectosRepositorio.deleteById(id);
    }

    @Override
    public byte[] generaXLS() {
        String[] columnas = { "Nro.", "Proyecto", "Detalle", "Institución", "Costo total", "Costo ejecutado"};
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        try {
            Workbook libro = new XSSFWorkbook();
            Sheet hoja = libro.createSheet("Reporte");
            Row filacabecera = hoja.createRow(0);
            Cell celda = filacabecera.createCell(0);

            CellStyle celdaEstilo = celda.getCellStyle();
            celdaEstilo.setAlignment(HorizontalAlignment.CENTER);
            celda.setCellValue("REPORTE DE PROYECTOS");
            celda.setCellStyle(celdaEstilo);

            hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
            filacabecera = hoja.createRow(1);
            for (int col = 0; col < columnas.length; col++) {
                celda = filacabecera.createCell(col);
                celda.setCellValue(columnas[col]);
            }
            int filaid = 2;
            int id = 1;
            List<Proyectos> datos = proyectosRepositorio.findAll();
            for (Proyectos _item : datos) {
                Instituciones institucion = institucionesRepositorio.findById(_item.getIdinstitucion()).orElse(null);
                Row fila = hoja.createRow(filaid++);
                fila.createCell(0).setCellValue(id++);
                fila.createCell(1).setCellValue(_item.getProyecto());
                fila.createCell(2).setCellValue(_item.getDetalle());
                fila.createCell(3).setCellValue(institucion.getInstitucion());
                fila.createCell(4).setCellValue(_item.getCostototal().toString());
                fila.createCell(5).setCellValue(_item.getCostoejecutado().toString());
            }
            for (int i = 0; i < 6; i++) {
                hoja.autoSizeColumn(i);
            }
            libro.write(salida);
            libro.close();
        } catch (Exception e) {
            System.out.println("error=" + e);
        }
        return salida.toByteArray();
    }

    @Override
    public byte[] generaPDF() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        try {
            PdfWriter escribir = new PdfWriter(salida);
            PdfDocument pdf = new PdfDocument(escribir);
		    Document documento = new Document(pdf, PageSize.LETTER);
            documento.setMargins(70f, 30f, 50f, 30f);

            Authentication autentificado = SecurityContextHolder.getContext().getAuthentication();
            Usuarios usuario = usuariosRepositorio.findById(Long.parseLong(autentificado.getName())).orElse(null);

            FormatoGeneralPDF evento = new FormatoGeneralPDF(documento, "REPORTE DE PROYECTOS",  usuario.getUsuario());
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, evento);

            float[] ancho = {10f, 10f, 10f, 10f, 10f, 10f};
            Table tabla = new Table(ancho);
            tabla.setWidth(UnitValue.createPercentValue(100));

            com.itextpdf.layout.element.Cell celda;

            String[] columnas = { "Nro.", "Proyecto", "Detalle", "Institución", "Costo total", "Costo ejecutado"};

            for (int col = 0; col < columnas.length; col++) {
                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(10).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(columnas[col]));
                tabla.addHeaderCell(celda);
            }

            List<Proyectos> datos = proyectosRepositorio.findAll();
            Integer contador = 1;
            for (Proyectos _item : datos) {

                Instituciones institucion = institucionesRepositorio.findById(_item.getIdinstitucion()).orElse(null);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(contador.toString()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getProyecto()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getDetalle()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(institucion.getInstitucion()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.RIGHT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getCostototal().toString()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.RIGHT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getCostoejecutado().toString()));
                tabla.addCell(celda);

                contador++;
            }

            documento.add(tabla);
            documento.close();
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        return salida.toByteArray();
    }

}
