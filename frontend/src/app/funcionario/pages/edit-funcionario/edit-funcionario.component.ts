import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Route, Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { ConfirmExiteDialogComponent } from '../../components/confirm-exite-dialog/confirm-exite-dialog.component';
import { CanDeactivate } from '../../models/canDeactivate';
import { Funcionario } from '../../models/funcionario';
import { FuncionarioHttpService } from '../../services/funcionario-http.service';

@Component({
  selector: 'app-edit-funcionario',
  templateUrl: './edit-funcionario.component.html',
  styleUrls: ['./edit-funcionario.component.css']
})
export class EditFuncionarioComponent implements OnInit, CanDeactivate {

  func!: Funcionario

  funcionario: FormGroup = this.fb.group({
    nome: [ '', [Validators.required]],
    email: [ '', [Validators.required, Validators.email]],
    foto: [null]
  })

  foto!: File

  constructor(
    private route: ActivatedRoute, 
    private funHttpService: FuncionarioHttpService, 
    private fb: FormBuilder, 
    private snackbar: MatSnackBar,
    private router: Router,
    private dialog: MatDialog
    ) { }

  canDeactivate(): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
     if (this.funcionario.dirty) {
      const ref = this.dialog.open(ConfirmExiteDialogComponent)

      return ref.afterClosed()
    }

    return true
  }

  ngOnInit(): void {
    const idFuncionario: number = parseInt(this.route.snapshot.paramMap.get('idFuncionario') || '0')

    this.funHttpService.getFuncionarioById(idFuncionario).subscribe(
      (fun) => {
        this.func = fun

        this.funcionario.patchValue({
          nome: this.func.nome,
          email: this.func.email
        })
      }
    )
  }

  submit(): void {
    this.func.nome = this.funcionario.value.nome
    this.func.email = this.funcionario.value.email

    this.funHttpService.uptadeFuncionario(this.func).subscribe(
      () => {
        if ( this.foto != undefined ) {
          const formData = new FormData()

          formData.append('foto', this.foto)

          const filename = `funcionario-${this.func.idFuncionario}.${this.foto.type.split('/')[1]}`

          this.funHttpService.addfoto(this.func.idFuncionario || 0, formData, filename).subscribe(
            () => {
              this.funcionario.reset()
              this.snackbar.open('Funcionario editado com sucesso!', 'Ok', {duration: 3000})

              this.router.navigateByUrl('/funcionarios')
            },
            (error: HttpErrorResponse) => {
              this.snackbar.open(`Ocorreu um erro ao salvar a edição do dados funcionário! (Erro ${error.status})`, 'Ok', {duration: 3000})
            }
          )
        } else {
            this.funcionario.reset()
            this.snackbar.open('Funcionario editado com sucesso!', 'Ok', {duration: 3000})

            this.router.navigateByUrl('/funcionarios')
        }
      },
      (error: HttpErrorResponse) => {
        this.snackbar.open(`Ocorreu um erro ao salvar a edição do dados funcionário! (Erro ${error.status})`, 'Ok', {duration: 3000})
      }
    )
  }

  fileChange(event: any): void {
    this.foto = event.target.files[0]
  }

}
