import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { ArtefactosModule } from '../_artefactos/artefactos.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ModulosRoutingModule } from './modulos-routing.module';
import { EscritorioComponent } from './escritorio/escritorio.component';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { PerfilesComponent } from './perfiles/perfiles.component';
import { E401Component } from './e401/e401.component';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { InsumosComponent } from './insumos/insumos.component';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { PersonasComponent } from './personas/personas.component';
import { EmpleadosComponent } from './empleados/empleados.component';
import { ProyectosComponent } from './proyectos/proyectos.component';
import { ActividadesComponent } from './actividades/actividades.component';
import { ItemsComponent } from './items/items.component';
import { InstitucionesComponent } from './instituciones/instituciones.component';
import { PreparacionComponent } from './preparacion/preparacion.component';
import { ArchivosdigitalesComponent } from './archivosdigitales/archivosdigitales.component';
import { ItemsinsumosComponent } from './itemsinsumos/itemsinsumos.component';
import { ProveedoresComponent } from './proveedores/proveedores.component';
import { IngresosComponent } from './ingresos/ingresos.component';
import { SalidasComponent } from './salidas/salidas.component';
import { PlanillasComponent } from './planillas/planillas.component';
import { ReportesingresosComponent } from './reportesingresos/reportesingresos.component';
import { ReportesegresosComponent } from './reportesegresos/reportesegresos.component';
import { ActividadesitemsComponent } from './actividadesitems/actividadesitems.component';
import { PlanillasempleadosComponent } from './planillasempleados/planillasempleados.component';

@NgModule({
  declarations: [EscritorioComponent, PerfilesComponent, E401Component, InsumosComponent, UsuariosComponent, PersonasComponent, EmpleadosComponent, ProyectosComponent, ActividadesComponent, ItemsComponent, InstitucionesComponent, PreparacionComponent, ArchivosdigitalesComponent, ItemsinsumosComponent, ProveedoresComponent, IngresosComponent, SalidasComponent, PlanillasComponent, ReportesingresosComponent, ReportesegresosComponent, ActividadesitemsComponent, PlanillasempleadosComponent],
  imports: [
    CommonModule,
    ModulosRoutingModule,
    ArtefactosModule,
    CKEditorModule,
    NgbPaginationModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class ModulosModule { }
