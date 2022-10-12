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
import com.cisco.regina.modelo.Unidades;
import com.cisco.regina.modelo.Usuarios;
import com.cisco.regina.modelo.otd.UnidadesOTD;
import com.cisco.regina.repositorio.UnidadesRepositorio;
import com.cisco.regina.repositorio.UsuariosRepositorio;
import com.cisco.regina.servicio.UnidadesServicio;

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
public class UnidadesServicioImpl implements UnidadesServicio{

    @Autowired
    UnidadesRepositorio unidadesRepositorio;

    @Autowired
    UsuariosRepositorio usuariosRepositorio;

    @Override
    public List<UnidadesOTD> buscarPorTermino(String termino, Integer pagina, Integer cantidad) {
        List<UnidadesOTD> salidas = new ArrayList<>();
        int nropagina = 0;
        if (pagina-1==-1) {
            nropagina = 0;
        } else {
            nropagina = pagina - 1;
        }
        Page<Unidades> entradaunidades = unidadesRepositorio.findByUnidadContainingIgnoreCaseOrderByUnidad(termino, PageRequest.of(nropagina, cantidad));
        for (Unidades unidad : entradaunidades) {
            UnidadesOTD dato = cambio(unidad);
            salidas.add(dato);
        }
        return salidas;
    }

    @Override
    public Integer cantidadBuscarPorTermino(String termino) {
        return unidadesRepositorio.findByUnidadContainingIgnoreCaseOrderByUnidad(termino, null).getSize();
    }

    @Override
    public List<UnidadesOTD> buscarTodos() {
        List<UnidadesOTD> salidas = new ArrayList<>();
        List<Unidades> entradaunidades = unidadesRepositorio.findAll();
        for (Unidades unidad : entradaunidades) {
            UnidadesOTD dato = cambio(unidad);
            salidas.add(dato);
        }
        return salidas;
    }

    @Override
    public UnidadesOTD buscarPorId(Long id) {
        Unidades entradaunidad = unidadesRepositorio.findById(id).orElse(null);
        UnidadesOTD salida = cambio(entradaunidad);
        return salida;
    }

    @Override
    public void adicionar(UnidadesOTD dato) {
        Unidades entrada = new Unidades();
        entrada.setUnidad(dato.getUnidad());
        entrada.setSigla(dato.getSigla());
        unidadesRepositorio.save(entrada);
    }

    @Override
    public void modificar(UnidadesOTD dato) {
        Unidades entrada = unidadesRepositorio.findById(dato.getIdunidad()).orElse(null);
        entrada.setUnidad(dato.getUnidad());
        entrada.setSigla(dato.getSigla());
        unidadesRepositorio.save(entrada);
    }

    @Override
    public void borrar(Long id) {
        unidadesRepositorio.deleteById(id);
    }

    private UnidadesOTD cambio(Unidades entrada) {
        UnidadesOTD salida = new UnidadesOTD(entrada.getIdunidad(), entrada.getUnidad(), entrada.getSigla());
        return salida;
    }

    @Override
    public byte[] generaXLS() {
        String[] columnas = { "Nro.", "Unidad", "Sigla"};
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        try {
            Workbook libro = new XSSFWorkbook();
            Sheet hoja = libro.createSheet("Reporte");
            Row filacabecera = hoja.createRow(0);
            Cell celda = filacabecera.createCell(0);

            CellStyle celdaEstilo = celda.getCellStyle();
            celdaEstilo.setAlignment(HorizontalAlignment.CENTER);
            celda.setCellValue("REPORTE DE UNIDADES");
            celda.setCellStyle(celdaEstilo);

            hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
            filacabecera = hoja.createRow(1);
            for (int col = 0; col < columnas.length; col++) {
                celda = filacabecera.createCell(col);
                celda.setCellValue(columnas[col]);
            }
            int filaid = 2;
            int id = 1;
            List<Unidades> datos = unidadesRepositorio.findAll();
            for (Unidades _item : datos) {

                Row fila = hoja.createRow(filaid++);
                fila.createCell(0).setCellValue(id++);
                fila.createCell(1).setCellValue(_item.getUnidad());
                fila.createCell(2).setCellValue(_item.getSigla());
            }
            for (int i = 0; i < 3; i++) {
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

            FormatoGeneralPDF evento = new FormatoGeneralPDF(documento, "REPORTE DE UNIDADES",  usuario.getUsuario());
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, evento);

            float[] ancho = {10f, 10f, 10f};
            Table tabla = new Table(ancho);
            tabla.setWidth(UnitValue.createPercentValue(100));

            com.itextpdf.layout.element.Cell celda;

            String[] columnas = { "Nro.", "Unidad", "Sigla"};

            for (int col = 0; col < columnas.length; col++) {
                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(10).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(columnas[col]));
                tabla.addHeaderCell(celda);
            }

            List<Unidades> datos = unidadesRepositorio.findAll();
            Integer contador = 1;
            for (Unidades _item : datos) {

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(contador.toString()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getUnidad()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getSigla()));
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
