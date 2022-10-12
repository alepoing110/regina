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
import com.cisco.regina.modelo.Items;
import com.cisco.regina.modelo.Unidades;
import com.cisco.regina.modelo.Usuarios;
import com.cisco.regina.modelo.otd.ItemsOTD;
import com.cisco.regina.repositorio.ItemsRepositorio;
import com.cisco.regina.repositorio.UnidadesRepositorio;
import com.cisco.regina.repositorio.UsuariosRepositorio;
import com.cisco.regina.servicio.ItemsServicio;

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
public class ItemsServicioImpl implements ItemsServicio{

    @Autowired
    ItemsRepositorio itemsRepositorio;

    @Autowired
    UnidadesRepositorio unidadesRepositorio;

    @Autowired
    UsuariosRepositorio usuariosRepositorio;

    @Override
    public List<ItemsOTD> buscarPorTermino(String termino, Integer pagina, Integer cantidad) {
        List<ItemsOTD> salidas = new ArrayList<>();
        int nropagina = 0;
        if (pagina-1==-1) {
            nropagina = 0;
        } else {
            nropagina = pagina - 1;
        }
        Page<Items> entradaItems = itemsRepositorio.findByItemContainingIgnoreCaseOrderByItem(termino, PageRequest.of(nropagina, cantidad));
        for (Items item : entradaItems) {
            Unidades unidad = unidadesRepositorio.findById(item.getIdunidad()).orElse(null);
            ItemsOTD dato = new ItemsOTD(item.getIditem(), item.getIdunidad(), item.getItem(), item.getCostounitario(), unidad.getUnidad());
            salidas.add(dato);
        }
        return salidas;
    }

    @Override
    public Integer cantidadBuscarPorTermino(String termino) {
        return itemsRepositorio.findByItemContainingIgnoreCaseOrderByItem(termino, null).getSize();
    }

    @Override
    public List<ItemsOTD> buscarTodos() {
        List<ItemsOTD> salidas = new ArrayList<>();
        List<Items> entradaItems = itemsRepositorio.findAll();
        for (Items item : entradaItems) {
            Unidades unidad = unidadesRepositorio.findById(item.getIdunidad()).orElse(null);
            ItemsOTD dato = new ItemsOTD(item.getIditem(), item.getIdunidad(), item.getItem(), item.getCostounitario(), unidad.getUnidad());
            salidas.add(dato);
        }
        return salidas;
    }

    @Override
    public ItemsOTD buscarPorId(Long id) {
        Items item = itemsRepositorio.findById(id).orElse(null);
        Unidades unidad = unidadesRepositorio.findById(item.getIdunidad()).orElse(null);
        ItemsOTD dato = new ItemsOTD(item.getIditem(), item.getIdunidad(), item.getItem(), item.getCostounitario(), unidad.getUnidad());
        return dato;
    }

    @Override
    public void adicionar(ItemsOTD dato) {
        Items entrada = new Items();
        entrada.setIdunidad(dato.getIdunidad());
        entrada.setItem(dato.getItem());
        entrada.setCostounitario(dato.getCostounitario());
        itemsRepositorio.save(entrada);
    }

    @Override
    public void modificar(ItemsOTD dato) {
        Items entrada = itemsRepositorio.findById(dato.getIditem()).orElse(null);
        entrada.setIdunidad(dato.getIdunidad());
        entrada.setItem(dato.getItem());
        entrada.setCostounitario(dato.getCostounitario());
        itemsRepositorio.save(entrada);
    }

    @Override
    public void borrar(Long id) {
        itemsRepositorio.deleteById(id);
    }

    @Override
    public byte[] generaXLS() {
        String[] columnas = { "Nro.", "Item", "Unidad", "Costo Unitario"};
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        try {
            Workbook libro = new XSSFWorkbook();
            Sheet hoja = libro.createSheet("Reporte");
            Row filacabecera = hoja.createRow(0);
            Cell celda = filacabecera.createCell(0);

            CellStyle celdaEstilo = celda.getCellStyle();
            celdaEstilo.setAlignment(HorizontalAlignment.CENTER);
            celda.setCellValue("REPORTE DE ITEMS");
            celda.setCellStyle(celdaEstilo);

            hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
            filacabecera = hoja.createRow(1);
            for (int col = 0; col < columnas.length; col++) {
                celda = filacabecera.createCell(col);
                celda.setCellValue(columnas[col]);
            }
            int filaid = 2;
            int id = 1;
            List<Items> datos = itemsRepositorio.findAll();
            for (Items _item : datos) {
                Unidades unidad = unidadesRepositorio.findById(_item.getIdunidad()).orElse(null);
                Row fila = hoja.createRow(filaid++);
                fila.createCell(0).setCellValue(id++);
                fila.createCell(1).setCellValue(_item.getItem());
                fila.createCell(2).setCellValue(unidad.getUnidad());
                fila.createCell(3).setCellValue(_item.getCostounitario().toString());
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

            FormatoGeneralPDF evento = new FormatoGeneralPDF(documento, "REPORTE DE ITEMS",  usuario.getUsuario());
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, evento);

            float[] ancho = {10f, 10f, 10f, 10f};
            Table tabla = new Table(ancho);
            tabla.setWidth(UnitValue.createPercentValue(100));

            com.itextpdf.layout.element.Cell celda;

            String[] columnas = { "Nro.", "Item", "Unidad", "Costo Unitario"};

            for (int col = 0; col < columnas.length; col++) {
                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(10).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(columnas[col]));
                tabla.addHeaderCell(celda);
            }

            List<Items> datos = itemsRepositorio.findAll();
            Integer contador = 1;
            for (Items _item : datos) {

                Unidades unidad = unidadesRepositorio.findById(_item.getIdunidad()).orElse(null);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(contador.toString()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getItem()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(unidad.getUnidad()));
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
