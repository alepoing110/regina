package com.cisco.regina.servicio.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

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
import com.cisco.regina.modelo.Empleados;
import com.cisco.regina.modelo.Insumos;
import com.cisco.regina.modelo.Personas;
import com.cisco.regina.modelo.Usuarios;
import com.cisco.regina.modelo.otd.EmpleadosOTD;
import com.cisco.regina.repositorio.EmpleadosRepositorio;
import com.cisco.regina.repositorio.InsumosRepositorio;
import com.cisco.regina.repositorio.PersonasRepositorio;
import com.cisco.regina.repositorio.UsuariosRepositorio;
import com.cisco.regina.servicio.EmpleadosServicio;

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
@Transactional
public class EmpleadosServicioImpl implements EmpleadosServicio{

    @Autowired
    PersonasRepositorio personasRepositorio;

    @Autowired
    UsuariosRepositorio usuariosRepositorio;

    @Autowired
    EmpleadosRepositorio empleadosRepositorio;

    @Autowired
    InsumosRepositorio insumosRepositorio;

    @Override
    public List<EmpleadosOTD> buscarPorTermino(String termino, Integer pagina, Integer cantidad) {
        List<EmpleadosOTD> salidas = new ArrayList<>();
        int nropagina = 0;
        if (pagina-1==-1) {
            nropagina = 0;
        } else {
            nropagina = pagina - 1;
        }
        Page<Empleados> entradaEmpleados = empleadosRepositorio.findByCargoContainingIgnoreCaseOrderByCargo(termino, PageRequest.of(nropagina, cantidad));
        for (Empleados empleado : entradaEmpleados) {
            Personas persona = personasRepositorio.findById(empleado.getIdpersona()).orElse(null);
            Insumos insumo = insumosRepositorio.findById(empleado.getIdinsumo()).orElse(null);
            EmpleadosOTD dato = new EmpleadosOTD(empleado.getIdempleado(), empleado.getIdpersona(), empleado.getIdinsumo(), persona.getPrimerapellido()+ " "+persona.getSegundoapellido()+" "+persona.getPrimernombre()+" "+persona.getSegundonombre(), empleado.getCargo(), insumo.getInsumo());
            salidas.add(dato);
        }
        return salidas;
    }

    @Override
    public Integer cantidadBuscarPorTermino(String termino) {
        return empleadosRepositorio.findByCargoContainingIgnoreCaseOrderByCargo(termino, null).getSize();
    }

    @Override
    public List<EmpleadosOTD> buscarTodos() {
        List<EmpleadosOTD> salidas = new ArrayList<>();
        List<Empleados> entradaEmpleados = empleadosRepositorio.findAll();
        for (Empleados empleado : entradaEmpleados) {
            Personas persona = personasRepositorio.findById(empleado.getIdpersona()).orElse(null);
            Insumos insumo = insumosRepositorio.findById(empleado.getIdinsumo()).orElse(null);
            EmpleadosOTD dato = new EmpleadosOTD(empleado.getIdempleado(), empleado.getIdpersona(), empleado.getIdinsumo(), persona.getPrimerapellido()+ " "+persona.getSegundoapellido()+" "+persona.getPrimernombre()+" "+persona.getSegundonombre(), empleado.getCargo(), insumo.getInsumo());
            salidas.add(dato);
        }
        return salidas;
    }

    @Override
    public EmpleadosOTD buscarPorId(Long id) {
        Empleados empleado = empleadosRepositorio.findById(id).orElse(null);
        Personas persona = personasRepositorio.findById(empleado.getIdpersona()).orElse(null);
        Insumos insumo = insumosRepositorio.findById(empleado.getIdinsumo()).orElse(null);
        EmpleadosOTD dato = new EmpleadosOTD(empleado.getIdempleado(), empleado.getIdpersona(), empleado.getIdinsumo(), persona.getPrimerapellido()+ " "+persona.getSegundoapellido()+" "+persona.getPrimernombre()+" "+persona.getSegundonombre(), empleado.getCargo(), insumo.getInsumo());
        return dato;
    }

    @Override
    public void adicionar(EmpleadosOTD dato) {
        Empleados entrada = new Empleados();
        entrada.setIdpersona(dato.getIdpersona());
        entrada.setIdinsumo(dato.getIdinsumo());
        entrada.setCargo("");
        empleadosRepositorio.save(entrada);
    }

    @Override
    public void modificar(EmpleadosOTD dato) {
        Empleados entrada = empleadosRepositorio.findById(dato.getIdempleado()).orElse(null);
        entrada.setIdpersona(dato.getIdpersona());
        entrada.setIdinsumo(dato.getIdinsumo());
        entrada.setCargo("");
        empleadosRepositorio.save(entrada);
    }

    @Override
    public void borrar(Long id) {
        empleadosRepositorio.deleteById(id);
    }

    @Override
    public byte[] generaXLS() {
        String[] columnas = { "Nro.", "Primer Apellido", "Segundo Apellido", "Primer Nombre", "Segundo Nombre", "Carnet de Identidad", "Cargo"};
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        try {
            Workbook libro = new XSSFWorkbook();
            Sheet hoja = libro.createSheet("Reporte");
            Row filacabecera = hoja.createRow(0);
            Cell celda = filacabecera.createCell(0);

            CellStyle celdaEstilo = celda.getCellStyle();
            celdaEstilo.setAlignment(HorizontalAlignment.CENTER);
            celda.setCellValue("REPORTE DE EMPLEADOS");
            celda.setCellStyle(celdaEstilo);

            hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
            filacabecera = hoja.createRow(1);
            for (int col = 0; col < columnas.length; col++) {
                celda = filacabecera.createCell(col);
                celda.setCellValue(columnas[col]);
            }
            int filaid = 2;
            int id = 1;
            List<Empleados> datos = empleadosRepositorio.findAll();
            for (Empleados _item : datos) {
                Personas persona = personasRepositorio.findById(_item.getIdpersona()).orElse(null);
                Row fila = hoja.createRow(filaid++);
                fila.createCell(0).setCellValue(id++);
                fila.createCell(1).setCellValue(persona.getPrimerapellido());
                fila.createCell(2).setCellValue(persona.getSegundoapellido());
                fila.createCell(3).setCellValue(persona.getPrimernombre());
                fila.createCell(4).setCellValue(persona.getSegundonombre());
                fila.createCell(5).setCellValue(persona.getDip());
                fila.createCell(6).setCellValue(_item.getCargo());
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

            FormatoGeneralPDF evento = new FormatoGeneralPDF(documento, "REPORTE DE EMPLEADOS",  usuario.getUsuario());
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, evento);

            float[] ancho = {10f, 10f, 10f, 10f, 10f, 10f, 10f};
            Table tabla = new Table(ancho);
            tabla.setWidth(UnitValue.createPercentValue(100));

            com.itextpdf.layout.element.Cell celda;

            String[] columnas = { "Nro.", "Primer Apellido", "Segundo Apellido", "Primer Nombre", "Segundo Nombre", "Carnet de Identidad", "Cargo"};

            for (int col = 0; col < columnas.length; col++) {
                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(10).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(columnas[col]));
                tabla.addHeaderCell(celda);
            }

            List<Empleados> datos = empleadosRepositorio.findAll();
            Integer contador = 1;
            for (Empleados _item : datos) {

                Personas persona = personasRepositorio.findById(_item.getIdpersona()).orElse(null);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(contador.toString()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(persona.getPrimerapellido()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(persona.getSegundoapellido()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(persona.getPrimernombre()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(persona.getSegundonombre().toString()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(persona.getDip().toString()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getCargo()));
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
