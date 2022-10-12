package com.cisco.regina.servicio.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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
import com.cisco.regina.modelo.Insumos;
import com.cisco.regina.modelo.Unidades;
import com.cisco.regina.modelo.Usuarios;
import com.cisco.regina.modelo.otd.InsumosOTD;
import com.cisco.regina.repositorio.InsumosRepositorio;
import com.cisco.regina.repositorio.UnidadesRepositorio;
import com.cisco.regina.repositorio.UsuariosRepositorio;
import com.cisco.regina.servicio.InsumosServicio;

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
public class InsumosServicioImpl implements InsumosServicio{

    @Autowired
    InsumosRepositorio insumosRepositorio;

    @Autowired
    UnidadesRepositorio unidadesRepositorio;

    @Autowired
    UsuariosRepositorio usuariosRepositorio;

    @Override
    public List<InsumosOTD> buscarPorTermino(String termino, Integer pagina, Integer cantidad) {
        List<InsumosOTD> salidas = new ArrayList<>();
        int nropagina = 0;
        if (pagina-1==-1) {
            nropagina = 0;
        } else {
            nropagina = pagina - 1;
        }
        Page<Insumos> entradaInsumos = insumosRepositorio.findByInsumoContainingIgnoreCaseOrderByInsumo(termino, PageRequest.of(nropagina, cantidad));
        for (Insumos insumo : entradaInsumos) {
            Unidades unidad = unidadesRepositorio.findById(insumo.getIdunidad()).orElse(null);
            InsumosOTD dato = new InsumosOTD(insumo.getIdinsumo(), insumo.getIdunidad(), insumo.getInsumo(), insumo.getTipo(), insumo.getCostounitario(), unidad.getUnidad());
            salidas.add(dato);
        }
        return salidas;
    }

    @Override
    public Integer cantidadBuscarPorTermino(String termino) {
        return insumosRepositorio.findByInsumoContainingIgnoreCaseOrderByInsumo(termino, null).getSize();
    }

    @Override
    public List<InsumosOTD> buscarTodos() {
        List<InsumosOTD> salidas = new ArrayList<>();
        List<Insumos> entradaInsumos = insumosRepositorio.findAllByOrderByInsumoAsc();
        for (Insumos insumo : entradaInsumos) {
            Unidades unidad = unidadesRepositorio.findById(insumo.getIdunidad()).orElse(null);
            InsumosOTD dato = new InsumosOTD(insumo.getIdinsumo(), insumo.getIdunidad(), insumo.getInsumo(), insumo.getTipo(), insumo.getCostounitario(), unidad.getUnidad());
            salidas.add(dato);
        }
        return salidas;
    }

    @Override
    public List<InsumosOTD> buscarTodosManoObra() {
        List<InsumosOTD> salidas = new ArrayList<>();
        List<Insumos> entradaInsumos = insumosRepositorio.findByTipoOrderByInsumoAsc("MANO DE OBRA");
        for (Insumos insumo : entradaInsumos) {
            Unidades unidad = unidadesRepositorio.findById(insumo.getIdunidad()).orElse(null);
            InsumosOTD dato = new InsumosOTD(insumo.getIdinsumo(), insumo.getIdunidad(), insumo.getInsumo(), insumo.getTipo(), insumo.getCostounitario(), unidad.getUnidad());
            salidas.add(dato);
        }
        return salidas;
    }

    @Override
    public InsumosOTD buscarPorId(Long id) {
        Insumos insumo = insumosRepositorio.findById(id).orElse(null);
        Unidades unidad = unidadesRepositorio.findById(insumo.getIdunidad()).orElse(null);
        InsumosOTD dato = new InsumosOTD(insumo.getIdinsumo(), insumo.getIdunidad(), insumo.getInsumo(), insumo.getTipo(), insumo.getCostounitario(), unidad.getUnidad());
        return dato;
    }

    @Override
    public void adicionar(InsumosOTD dato) {
        Insumos entrada = new Insumos();
        entrada.setIdunidad(dato.getIdunidad());
        entrada.setInsumo(dato.getInsumo());
        entrada.setTipo(dato.getTipo());
        entrada.setCostounitario(dato.getCostounitario());
        insumosRepositorio.save(entrada);
    }

    @Override
    public void modificar(InsumosOTD dato) {
        Insumos entrada = insumosRepositorio.findById(dato.getIdinsumo()).orElse(null);
        entrada.setIdunidad(dato.getIdunidad());
        entrada.setInsumo(dato.getInsumo());
        entrada.setTipo(dato.getTipo());
        entrada.setCostounitario(dato.getCostounitario());
        insumosRepositorio.save(entrada);
    }

    @Override
    public void borrar(Long id) {
        insumosRepositorio.deleteById(id);
    }

    @Override
    public byte[] generaXLS() {
        String[] columnas = { "Nro.", "Insumo", "Unidad", "Tipo", "Costo Unitario"};
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        try {
            Workbook libro = new XSSFWorkbook();
            Sheet hoja = libro.createSheet("Reporte");
            Row filacabecera = hoja.createRow(0);
            Cell celda = filacabecera.createCell(0);

            CellStyle celdaEstilo = celda.getCellStyle();
            celdaEstilo.setAlignment(HorizontalAlignment.CENTER);
            celda.setCellValue("REPORTE DE INSUMOS");
            celda.setCellStyle(celdaEstilo);

            hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
            filacabecera = hoja.createRow(1);
            for (int col = 0; col < columnas.length; col++) {
                celda = filacabecera.createCell(col);
                celda.setCellValue(columnas[col]);
            }
            int filaid = 2;
            int id = 1;
            List<Insumos> datos = insumosRepositorio.findAll();
            for (Insumos _item : datos) {
                Unidades unidad = unidadesRepositorio.findById(_item.getIdunidad()).orElse(null);
                Row fila = hoja.createRow(filaid++);
                fila.createCell(0).setCellValue(id++);
                fila.createCell(1).setCellValue(_item.getInsumo());
                fila.createCell(2).setCellValue(unidad.getUnidad());
                fila.createCell(3).setCellValue(_item.getTipo());
                fila.createCell(4).setCellValue(_item.getCostounitario().toString());
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

            FormatoGeneralPDF evento = new FormatoGeneralPDF(documento, "REPORTE DE INSUMOS",  usuario.getUsuario());
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, evento);

            float[] ancho = {10f, 10f, 10f, 10f, 10f};
            Table tabla = new Table(ancho);
            tabla.setWidth(UnitValue.createPercentValue(100));

            com.itextpdf.layout.element.Cell celda;

            String[] columnas = { "Nro.", "Insumo", "Unidad", "Tipo", "Costo Unitario"};

            for (int col = 0; col < columnas.length; col++) {
                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(10).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(columnas[col]));
                tabla.addHeaderCell(celda);
            }

            List<Insumos> datos = insumosRepositorio.findAll();
            Integer contador = 1;
            for (Insumos _item : datos) {

                Unidades unidad = unidadesRepositorio.findById(_item.getIdunidad()).orElse(null);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(contador.toString()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getInsumo()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(unidad.getUnidad()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getTipo()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.RIGHT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getCostounitario().toString()));
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
