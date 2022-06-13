import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DelFuncDialogComponent } from '../../components/del-func-dialog/del-func-dialog.component';
import { Funcionario } from '../../models/funcionario';
import { FuncionarioHttpService } from '../../services/funcionario-http.service';


@Component({
  selector: 'app-listar-funcionario',
  templateUrl: './listar-funcionario.component.html',
  styleUrls: ['./listar-funcionario.component.css']
})
export class ListarFuncionarioComponent implements OnInit {

  funcionarios: Funcionario[] = []

  columns: string[] = ['idFuncionario', 'nome', 'email', 'foto', 'actions']

  constructor(private funHttpServices: FuncionarioHttpService, private dialog: MatDialog, private snackbar: MatSnackBar) { }

  ngOnInit(): void {
    this.funHttpServices.getFuncionarios().subscribe(
      (funcionarios) => {
        this.funcionarios = funcionarios
      }
    )
  }

  confirmationDelete(idFuncionario: number) {
    let ref = this.dialog.open(DelFuncDialogComponent)

    ref.afterClosed().subscribe(
      canDelete => {
        if(canDelete) {
          this.funHttpServices.deleteFuncionarioById(idFuncionario).subscribe(
            () => {
              this.snackbar.open('Funcionário deletado!', 'Ok', {duration: 3000})
              this.funHttpServices.getFuncionarios().subscribe(
                (funcionarios) => {
                  this.funcionarios = funcionarios
                }
              )

            }
          )
        }
      }
    )
  }



}
