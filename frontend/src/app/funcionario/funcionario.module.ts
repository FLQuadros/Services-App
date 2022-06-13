import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FuncionarioRoutingModule } from './funcionario-routing.module';
import { NovoFuncionarioComponent } from './pages/novo-funcionario/novo-funcionario.component';
import { MaterialModule } from '../material/material.module';
import { ListarFuncionarioComponent } from './pages/listar-funcionario/listar-funcionario.component';
import { HttpClientModule } from '@angular/common/http';
import { FuncionarioHttpService } from './services/funcionario-http.service';
import { ReactiveFormsModule } from '@angular/forms';
import { FuncionarioComponent } from './pages/funcionario/funcionario.component';
import { IsNumberGuard } from './guards/is-number.guard';
import { DelFuncDialogComponent } from './components/del-func-dialog/del-func-dialog.component';
import { EditFuncionarioComponent } from './pages/edit-funcionario/edit-funcionario.component';
import { ConfirmExitGuard } from './guards/confirm-exit.guard';
import { ConfirmExiteDialogComponent } from './components/confirm-exite-dialog/confirm-exite-dialog.component';
import { CanEnterGuard } from './guards/can-enter.guard';




@NgModule({
  declarations: [
    NovoFuncionarioComponent,
    ListarFuncionarioComponent,
    FuncionarioComponent,
    DelFuncDialogComponent,
    EditFuncionarioComponent,
    ConfirmExiteDialogComponent
  ],
  imports: [
    CommonModule,
    FuncionarioRoutingModule,
    MaterialModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [
    IsNumberGuard,
    ConfirmExitGuard,
    CanEnterGuard
  ]
})
export class FuncionarioModule { }
