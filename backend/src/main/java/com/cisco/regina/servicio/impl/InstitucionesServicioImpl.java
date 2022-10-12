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
import com.cisco.regina.modelo.Usuarios;
import com.cisco.regina.repositorio.InstitucionesRepositorio;
import com.cisco.regina.repositorio.UsuariosRepositorio;
import com.cisco.regina.servicio.InstitucionesServicio;

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
public class InstitucionesServicioImpl implements InstitucionesServicio{

    @Autowired
    InstitucionesRepositorio institucionesRepositorio;

    @Autowired
    UsuariosRepositorio usuariosRepositorio;

    @Override
    public List<Instituciones> buscarPorTermino(String termino, Integer pagina, Integer cantidad) {
        int nropagina = 0;
        if (pagina-1==-1) {
            nropagina = 0;
        } else {
            nropagina = pagina - 1;
        }
        Page<Instituciones> salidas = institucionesRepositorio.findByInstitucionContainingIgnoreCaseOrderByInstitucion(termino, PageRequest.of(nropagina, cantidad));
        return salidas.getContent();
    }

    @Override
    public Integer cantidadBuscarPorTermino(String termino) {
        return institucionesRepositorio.findByInstitucionContainingIgnoreCaseOrderByInstitucion(termino, null).getSize();
    }

    @Override
    public List<Instituciones> buscarTodos() {
        List<Instituciones> salidas = institucionesRepositorio.findAll();
        return salidas;
    }

    @Override
    public Instituciones buscarPorId(Long id) {
        Instituciones salida = institucionesRepositorio.findById(id).orElse(null);
        return salida;
    }

    @Override
    public void adicionar(Instituciones dato) {
        institucionesRepositorio.save(dato);
    }

    @Override
    public void modificar(Instituciones dato) {
        institucionesRepositorio.save(dato);
    }

    @Override
    public void borrar(Long id) {
        institucionesRepositorio.deleteById(id);
    }

    @Override
    public byte[] generaXLS() {
        String[] columnas = { "Nro.", "Institución", "Representante", "Dirección", "Telefono", "Celular", "Correo" };
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        try {
            Workbook libro = new XSSFWorkbook();
            Sheet hoja = libro.createSheet("Reporte");
            Row filacabecera = hoja.createRow(0);
            Cell celda = filacabecera.createCell(0);

            CellStyle celdaEstilo = celda.getCellStyle();
            celdaEstilo.setAlignment(HorizontalAlignment.CENTER);
            celda.setCellValue("REPORTE DE INSTITUCIONES");
            celda.setCellStyle(celdaEstilo);

            hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
            filacabecera = hoja.createRow(1);
            for (int col = 0; col < columnas.length; col++) {
                celda = filacabecera.createCell(col);
                celda.setCellValue(columnas[col]);
            }
            int filaid = 2;
            int id = 1;
            List<Instituciones> datos = institucionesRepositorio.findAll();
            for (Instituciones _item : datos) {
                Row fila = hoja.createRow(filaid++);
                fila.createCell(0).setCellValue(id++);
                fila.createCell(1).setCellValue(_item.getInstitucion());
                fila.createCell(2).setCellValue(_item.getRepresentante());
                fila.createCell(3).setCellValue(_item.getDireccion());
                fila.createCell(4).setCellValue(_item.getTelefono());
                fila.createCell(5).setCellValue(_item.getCelular());
                fila.createCell(6).setCellValue(_item.getCorreo());
            }
            for (int i = 0; i < 7; i++) {
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

            FormatoGeneralPDF evento = new FormatoGeneralPDF(documento, "REPORTE DE INSTITUCIONES",  usuario.getUsuario());
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, evento);

            float[] ancho = {10f, 10f, 10f, 10f, 10f, 10f, 10f};
            Table tabla = new Table(ancho);
            tabla.setWidth(UnitValue.createPercentValue(100));

            com.itextpdf.layout.element.Cell celda;

            String[] columnas = { "Nro.", "Institución", "Representante", "Dirección", "Telefono", "Celular", "Correo" };

            for (int col = 0; col < columnas.length; col++) {
                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(10).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(columnas[col]));
                tabla.addHeaderCell(celda);
            }

            List<Instituciones> datos = institucionesRepositorio.findAll();
            Integer contador = 1;
            for (Instituciones _item : datos) {

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(contador.toString()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getInstitucion()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getRepresentante()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getDireccion()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getTelefono()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getCelular()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getCorreo()));
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
