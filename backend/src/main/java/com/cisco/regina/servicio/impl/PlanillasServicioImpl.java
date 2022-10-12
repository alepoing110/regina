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
import com.cisco.regina.modelo.Planillas;
import com.cisco.regina.modelo.Usuarios;
import com.cisco.regina.modelo.otd.PlanillasOTD;
import com.cisco.regina.repositorio.PlanillasRepositorio;
import com.cisco.regina.repositorio.UsuariosRepositorio;
import com.cisco.regina.servicio.PlanillasServicio;

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
public class PlanillasServicioImpl implements PlanillasServicio{

    @Autowired
    PlanillasRepositorio planillasRepositorio;

    @Autowired
    UsuariosRepositorio usuariosRepositorio;

    @Override
    public List<PlanillasOTD> buscarPorTermino(String termino, Integer pagina, Integer cantidad) {
        List<PlanillasOTD> salidas = new ArrayList<>();
        int nropagina = 0;
        if (pagina-1==-1) {
            nropagina = 0;
        } else {
            nropagina = pagina - 1;
        }
        Page<Planillas> entradaplanillas = planillasRepositorio.findByPlanillaContainingIgnoreCaseOrderByPlanilla(termino, PageRequest.of(nropagina, cantidad));
        for (Planillas unidad : entradaplanillas) {
            PlanillasOTD dato = cambio(unidad);
            salidas.add(dato);
        }
        return salidas;
    }

    @Override
    public Integer cantidadBuscarPorTermino(String termino) {
        return planillasRepositorio.findByPlanillaContainingIgnoreCaseOrderByPlanilla(termino, null).getSize();
    }

    @Override
    public List<PlanillasOTD> buscarTodos() {
        List<PlanillasOTD> salidas = new ArrayList<>();
        List<Planillas> entradaplanillas = planillasRepositorio.findAll();
        for (Planillas unidad : entradaplanillas) {
            PlanillasOTD dato = cambio(unidad);
            salidas.add(dato);
        }
        return salidas;
    }

    @Override
    public PlanillasOTD buscarPorId(Long id) {
        Planillas entradaunidad = planillasRepositorio.findById(id).orElse(null);
        PlanillasOTD salida = cambio(entradaunidad);
        return salida;
    }

    @Override
    public void adicionar(PlanillasOTD dato) {
        Planillas entrada = new Planillas();
        entrada.setPlanilla(dato.getPlanilla());
        entrada.setGestion(dato.getGestion());
        entrada.setMes(dato.getMes());
        planillasRepositorio.save(entrada);
    }

    @Override
    public void modificar(PlanillasOTD dato) {
        Planillas entrada = planillasRepositorio.findById(dato.getIdplanilla()).orElse(null);
        entrada.setPlanilla(dato.getPlanilla());
        entrada.setGestion(dato.getGestion());
        entrada.setMes(dato.getMes());
        planillasRepositorio.save(entrada);
    }

    @Override
    public void borrar(Long id) {
        planillasRepositorio.deleteById(id);
    }

    private PlanillasOTD cambio(Planillas entrada) {
        String mes = "";
        if (entrada.getMes()==1) { mes="ENERO"; }
        if (entrada.getMes()==2) { mes="FEBRERO"; }
        if (entrada.getMes()==3) { mes="MARZO"; }
        if (entrada.getMes()==4) { mes="ABRIL"; }
        if (entrada.getMes()==5) { mes="MAYO"; }
        if (entrada.getMes()==6) { mes="JUNIO"; }
        if (entrada.getMes()==7) { mes="JULIO"; }
        if (entrada.getMes()==8) { mes="AGOSTO"; }
        if (entrada.getMes()==9) { mes="SEPTIEMBRE"; }
        if (entrada.getMes()==10) { mes="OCTUBRE"; }
        if (entrada.getMes()==11) { mes="NOVIEMBRE"; }
        if (entrada.getMes()==12) { mes="DICIEMBRE"; }
        PlanillasOTD salida = new PlanillasOTD(entrada.getIdplanilla(), entrada.getPlanilla(), entrada.getGestion(), entrada.getMes(), entrada.getCosto_total(), mes);
        return salida;
    }

    @Override
    public byte[] generaXLS() {
        String[] columnas = { "Nro.", "Planilla", "Gestión", "Mes", "Total planilla"};
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        try {
            Workbook libro = new XSSFWorkbook();
            Sheet hoja = libro.createSheet("Reporte");
            Row filacabecera = hoja.createRow(0);
            Cell celda = filacabecera.createCell(0);

            CellStyle celdaEstilo = celda.getCellStyle();
            celdaEstilo.setAlignment(HorizontalAlignment.CENTER);
            celda.setCellValue("REPORTE DE PLANILLAS");
            celda.setCellStyle(celdaEstilo);

            hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
            filacabecera = hoja.createRow(1);
            for (int col = 0; col < columnas.length; col++) {
                celda = filacabecera.createCell(col);
                celda.setCellValue(columnas[col]);
            }
            int filaid = 2;
            int id = 1;
            List<Planillas> datos = planillasRepositorio.findAll();
            for (Planillas _item : datos) {

                Row fila = hoja.createRow(filaid++);
                fila.createCell(0).setCellValue(id++);
                fila.createCell(1).setCellValue(_item.getPlanilla());
                fila.createCell(2).setCellValue(_item.getGestion().toString());
                String mes = "";
                if (_item.getMes()==1) { mes="ENERO"; }
                if (_item.getMes()==2) { mes="FEBRERO"; }
                if (_item.getMes()==3) { mes="MARZO"; }
                if (_item.getMes()==4) { mes="ABRIL"; }
                if (_item.getMes()==5) { mes="MAYO"; }
                if (_item.getMes()==6) { mes="JUNIO"; }
                if (_item.getMes()==7) { mes="JULIO"; }
                if (_item.getMes()==8) { mes="AGOSTO"; }
                if (_item.getMes()==9) { mes="SEPTIEMBRE"; }
                if (_item.getMes()==10) { mes="OCTUBRE"; }
                if (_item.getMes()==11) { mes="NOVIEMBRE"; }
                if (_item.getMes()==12) { mes="DICIEMBRE"; }
                fila.createCell(3).setCellValue(mes);
                fila.createCell(4).setCellValue(_item.getCosto_total().toString());
            }
            for (int i = 0; i < 5; i++) {
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

            FormatoGeneralPDF evento = new FormatoGeneralPDF(documento, "REPORTE DE PLANILLAS",  usuario.getUsuario());
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, evento);

            float[] ancho = {10f, 10f, 10f, 10f, 10f};
            Table tabla = new Table(ancho);
            tabla.setWidth(UnitValue.createPercentValue(100));

            com.itextpdf.layout.element.Cell celda;

            String[] columnas = { "Nro.", "Planilla", "Gestión", "Mes", "Total planilla"};

            for (int col = 0; col < columnas.length; col++) {
                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(10).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(columnas[col]));
                tabla.addHeaderCell(celda);
            }

            List<Planillas> datos = planillasRepositorio.findAll();
            Integer contador = 1;
            for (Planillas _item : datos) {

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(contador.toString()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getPlanilla()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getGestion().toString()));
                tabla.addCell(celda);

                String mes = "";
                if (_item.getMes()==1) { mes="ENERO"; }
                if (_item.getMes()==2) { mes="FEBRERO"; }
                if (_item.getMes()==3) { mes="MARZO"; }
                if (_item.getMes()==4) { mes="ABRIL"; }
                if (_item.getMes()==5) { mes="MAYO"; }
                if (_item.getMes()==6) { mes="JUNIO"; }
                if (_item.getMes()==7) { mes="JULIO"; }
                if (_item.getMes()==8) { mes="AGOSTO"; }
                if (_item.getMes()==9) { mes="SEPTIEMBRE"; }
                if (_item.getMes()==10) { mes="OCTUBRE"; }
                if (_item.getMes()==11) { mes="NOVIEMBRE"; }
                if (_item.getMes()==12) { mes="DICIEMBRE"; }

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(mes));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getCosto_total().toString()));
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
