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
import com.cisco.regina.modelo.Personas;
import com.cisco.regina.modelo.Roles;
import com.cisco.regina.modelo.Usuarios;
import com.cisco.regina.modelo.Usuariosroles;
import com.cisco.regina.modelo.otd.UsuariosOTD;
import com.cisco.regina.repositorio.PersonasRepositorio;
import com.cisco.regina.repositorio.RolesRepositorio;
import com.cisco.regina.repositorio.UsuariosRepositorio;
import com.cisco.regina.repositorio.UsuariosrolesRepositorio;
import com.cisco.regina.servicio.UsuariosServicio;

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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuariosServicioImpl implements UsuariosServicio, UserDetailsService{

    @Autowired
    PersonasRepositorio personasRepositorio;

    @Autowired
    UsuariosRepositorio usuariosRepositorio;

    @Autowired
    UsuariosrolesRepositorio usuariosrolesRepositorio;

    @Autowired
    RolesRepositorio rolesRepositorio;

    @Override
    public UserDetails loadUserByUsername(String entradausuario) throws UsernameNotFoundException {
        Usuarios usuario = usuariosRepositorio.findByUsuario(entradausuario);
        if (usuario == null) {
            throw new UsernameNotFoundException("No se encontro al usuario: " + entradausuario);
        } else {
            List<GrantedAuthority> roles = new ArrayList<>();
            List<Usuariosroles> usuarioroles = usuariosrolesRepositorio.findByIdusuario(usuario.getIdusuario());
            usuarioroles.forEach((usuariorol) -> {
                Roles rol = rolesRepositorio.findById(usuariorol.getIdrol()).orElse(null);
                roles.add(new SimpleGrantedAuthority(rol.getRol()));
            });
            return new User(Long.toString(usuario.getIdusuario()), usuario.getClave(), roles);
        }

    }

    @Override
    public void cambiarClave(Usuarios dato) {
        Authentication autentificado = SecurityContextHolder.getContext().getAuthentication();
        usuariosRepositorio.cambiarClave(Long.parseLong(autentificado.getName()), dato.getClave());
    }

    @Override
    public List<UsuariosOTD> buscarPorTermino(String termino, Integer pagina, Integer cantidad) {
        List<UsuariosOTD> salidas = new ArrayList<>();
        int nropagina = 0;
        if (pagina-1==-1) {
            nropagina = 0;
        } else {
            nropagina = pagina - 1;
        }
        Page<Usuarios> entradaUsuarios = usuariosRepositorio.findByUsuarioContainingIgnoreCaseOrderByUsuario(termino, PageRequest.of(nropagina, cantidad));
        for (Usuarios usuario : entradaUsuarios) {
            List<Usuariosroles> usuariosroles = usuariosrolesRepositorio.findByIdusuario(usuario.getIdusuario());
            Roles rol = rolesRepositorio.findById(usuariosroles.get(0).getIdrol()).orElse(null);
            Personas persona = personasRepositorio.findById(usuario.getIdpersona()).orElse(null);
            UsuariosOTD dato = new UsuariosOTD(usuario.getIdusuario(), usuario.getIdpersona(), persona.getPrimerapellido()+ " "+persona.getSegundoapellido()+" "+persona.getPrimernombre()+" "+persona.getSegundonombre(), usuario.getUsuario(), "", usuario.getEstado(), rol.getIdrol(), rol.getRol());
            salidas.add(dato);
        }
        return salidas;
    }

    @Override
    public Integer cantidadBuscarPorTermino(String termino) {
        return usuariosRepositorio.findByUsuarioContainingIgnoreCaseOrderByUsuario(termino, null).getSize();
    }

    @Override
    public List<UsuariosOTD> buscarTodos() {
        List<UsuariosOTD> salidas = new ArrayList<>();
        List<Usuarios> entradaUsuarios = usuariosRepositorio.findAll();
        for (Usuarios usuario : entradaUsuarios) {
            List<Usuariosroles> usuariosroles = usuariosrolesRepositorio.findByIdusuario(usuario.getIdusuario());
            Roles rol = rolesRepositorio.findById(usuariosroles.get(0).getIdrol()).orElse(null);
            Personas persona = personasRepositorio.findById(usuario.getIdpersona()).orElse(null);
            UsuariosOTD dato = new UsuariosOTD(usuario.getIdusuario(), usuario.getIdpersona(), persona.getPrimerapellido()+ " "+persona.getSegundoapellido()+" "+persona.getPrimernombre()+" "+persona.getSegundonombre(), usuario.getUsuario(), "", usuario.getEstado(), rol.getIdrol(), rol.getRol());
            salidas.add(dato);
        }
        return salidas;
    }

    @Override
    public UsuariosOTD buscarPorId(Long id) {
        List<Usuariosroles> usuariosroles = usuariosrolesRepositorio.findByIdusuario(id);
        Roles rol = rolesRepositorio.findById(usuariosroles.get(0).getIdrol()).orElse(null);
        Usuarios usuario = usuariosRepositorio.findById(id).orElse(null);
        Personas persona = personasRepositorio.findById(usuario.getIdpersona()).orElse(null);
        UsuariosOTD dato = new UsuariosOTD(usuario.getIdusuario(), usuario.getIdpersona(), persona.getPrimerapellido()+ " "+persona.getSegundoapellido()+" "+persona.getPrimernombre()+" "+persona.getSegundonombre(), usuario.getUsuario(), "", usuario.getEstado(), rol.getIdrol(), rol.getRol());
        return dato;
    }

    @Override
    public void adicionar(UsuariosOTD dato) {
        Usuarios entrada = new Usuarios();
        entrada.setIdpersona(dato.getIdpersona());
        entrada.setEstado(true);
        entrada.setUsuario(dato.getUsuario());
        entrada.setClave("$2a$08$4tBLrOlIjVGWhZnkjEBTk.3V.CKU0PWYpOq8gx2acsIrq.enVkaJK");
        usuariosRepositorio.save(entrada);
        Usuariosroles usuariorol = new Usuariosroles();
        usuariorol.setIdrol(dato.getIdrol());
        usuariorol.setIdusuario(entrada.getIdusuario());
        usuariosrolesRepositorio.save(usuariorol);
    }

    @Override
    public void modificar(UsuariosOTD dato) {
        Usuarios entrada = usuariosRepositorio.findById(dato.getIdusuario()).orElse(null);
        entrada.setIdusuario(dato.getIdusuario());
        entrada.setIdpersona(dato.getIdpersona());
        entrada.setEstado(true);
        entrada.setUsuario(dato.getUsuario());
        entrada.setClave("$2a$08$Onouw/Xg83Sq6YFPxUIdSeXL69HGKIVH872vSggmBbaQyPl9R8PP.");
        usuariosRepositorio.save(entrada);
        usuariosrolesRepositorio.deleteByIdusuario(dato.getIdusuario());
        Usuariosroles usuariorol = new Usuariosroles();
        usuariorol.setIdrol(dato.getIdrol());
        usuariorol.setIdusuario(entrada.getIdusuario());
        usuariosrolesRepositorio.save(usuariorol);
    }

    @Override
    public void borrar(Long id) {
        usuariosrolesRepositorio.deleteByIdusuario(id);
        usuariosRepositorio.deleteById(id);
    }

    @Override
    public byte[] generaXLS() {
        String[] columnas = { "Nro.", "Usuario", "Primer Apellido", "Segundo Apellido", "Primer Nombre", "Segundo Nombre", "Carnet de Identidad", "Rol" };
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        try {
            Workbook libro = new XSSFWorkbook();
            Sheet hoja = libro.createSheet("Reporte");
            Row filacabecera = hoja.createRow(0);
            Cell celda = filacabecera.createCell(0);

            CellStyle celdaEstilo = celda.getCellStyle();
            celdaEstilo.setAlignment(HorizontalAlignment.CENTER);
            celda.setCellValue("REPORTE DE USUARIOS");
            celda.setCellStyle(celdaEstilo);

            hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
            filacabecera = hoja.createRow(1);
            for (int col = 0; col < columnas.length; col++) {
                celda = filacabecera.createCell(col);
                celda.setCellValue(columnas[col]);
            }
            int filaid = 2;
            int id = 1;
            List<Usuarios> datos = usuariosRepositorio.findAll();
            for (Usuarios _item : datos) {
                List<Usuariosroles> usuariosroles = usuariosrolesRepositorio.findByIdusuario(_item.getIdusuario());
                Roles rol = rolesRepositorio.findById(usuariosroles.get(0).getIdrol()).orElse(null);
                Usuarios usuario = usuariosRepositorio.findById(_item.getIdusuario()).orElse(null);
                Personas persona = personasRepositorio.findById(usuario.getIdpersona()).orElse(null);
                Row fila = hoja.createRow(filaid++);
                fila.createCell(0).setCellValue(id++);
                fila.createCell(1).setCellValue(_item.getUsuario());
                fila.createCell(2).setCellValue(persona.getPrimerapellido());
                fila.createCell(3).setCellValue(persona.getSegundoapellido());
                fila.createCell(4).setCellValue(persona.getPrimernombre());
                fila.createCell(5).setCellValue(persona.getSegundonombre());
                fila.createCell(6).setCellValue(persona.getDip());
                fila.createCell(7).setCellValue(rol.getRol());
            }
            for (int i = 0; i < 8; i++) {
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

            FormatoGeneralPDF evento = new FormatoGeneralPDF(documento, "REPORTE DE USUARIOS",  usuario.getUsuario());
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, evento);

            float[] ancho = {10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f};
            Table tabla = new Table(ancho);
            tabla.setWidth(UnitValue.createPercentValue(100));

            com.itextpdf.layout.element.Cell celda;

            String[] columnas = { "Nro.", "Usuario", "Primer Apellido", "Segundo Apellido", "Primer Nombre", "Segundo Nombre", "Carnet de Identidad", "Rol" };

            for (int col = 0; col < columnas.length; col++) {
                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(10).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(columnas[col]));
                tabla.addHeaderCell(celda);
            }

            List<Usuarios> datos = usuariosRepositorio.findAll();
            Integer contador = 1;
            for (Usuarios _item : datos) {

                List<Usuariosroles> usuariosroles = usuariosrolesRepositorio.findByIdusuario(_item.getIdusuario());
                Roles rol = rolesRepositorio.findById(usuariosroles.get(0).getIdrol()).orElse(null);
                Usuarios usuarioentrada = usuariosRepositorio.findById(_item.getIdusuario()).orElse(null);
                Personas persona = personasRepositorio.findById(usuarioentrada.getIdpersona()).orElse(null);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(contador.toString()));
                tabla.addCell(celda);

                celda = new com.itextpdf.layout.element.Cell().setTextAlignment(TextAlignment.LEFT).setFontSize(9).setFontColor(ColorConstants.BLACK);
                celda.add(new Paragraph(_item.getUsuario()));
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
                celda.add(new Paragraph(rol.getRol()));
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
