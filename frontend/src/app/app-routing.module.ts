import { E404Component } from './e404/e404.component';
import { GuardianGuard } from './_configuracionSeguridad/guardian.guard';
import { PlantillaComponent } from './_plantillas/plantilla.component';
import { AccesoComponent } from './acceso/acceso.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: 'acceso',
    component: AccesoComponent,
  },
  {
    path: '',
    component: PlantillaComponent,
    loadChildren: () =>
    import('./_interfazusuario/modulos.module').then( (m) => m.ModulosModule),
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS'
    }
  },
  {
    path: '**',
    component: E404Component,
    canActivate: [GuardianGuard],
    data: {
      rol: 'ROLE_TODOS'
    }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule],
})
export class AppRoutingModule {}
