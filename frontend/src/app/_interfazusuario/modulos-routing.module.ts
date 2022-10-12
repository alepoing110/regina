import { E401Component } from './e401/e401.component';
import { GuardianGuard } from '../_configuracionSeguridad/guardian.guard';
import { PerfilesComponent } from './perfiles/perfiles.component';
import { EscritorioComponent } from './escritorio/escritorio.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UnidadesComponent } from './unidades/unidades.component';
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
import { ReportesingresosComponent } from './reportesingresos/reportesingresos.component';
import { ReportesegresosComponent } from './reportesegresos/reportesegresos.component';
import { PlanillasComponent } from './planillas/planillas.component';
import { ActividadesitemsComponent } from './actividadesitems/actividadesitems.component';
import { PlanillasempleadosComponent } from './planillasempleados/planillasempleados.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'escritorio',
  },
  {
    path: 'escritorio',
    component: EscritorioComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'e401',
    component: E401Component,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'perfiles',
    component: PerfilesComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'unidades',
    component: UnidadesComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'insumos',
    component: InsumosComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'usuarios',
    component: UsuariosComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'personas',
    component: PersonasComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'empleados',
    component: EmpleadosComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'instituciones',
    component: InstitucionesComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'proyectos',
    component: ProyectosComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'proyectos/:id',
    component: ActividadesComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'actividades/:id',
    component: ActividadesitemsComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'items',
    component: ItemsComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'items/:id',
    component: ItemsinsumosComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'preparacion',
    component: PreparacionComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'preparacion/:id',
    component: ArchivosdigitalesComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'proveedores',
    component: ProveedoresComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'ingresos',
    component: IngresosComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'salidas',
    component: SalidasComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'reportesingresos',
    component: ReportesingresosComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'reportesegresos',
    component: ReportesegresosComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'planillas',
    component: PlanillasComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },
  {
    path: 'planillas/:id',
    component: PlanillasempleadosComponent,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS',
    },
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ModulosRoutingModule {}
