import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from 'src/app/auth/services/authentication.service';
import { ConfirmExiteDialogComponent } from '../../components/confirm-exite-dialog/confirm-exite-dialog.component';
import { CanDeactivate } from '../../models/canDeactivate';
import { FuncionarioHttpService } from '../../services/funcionario-http.service';


@Component({
  selector: 'app-novo-funcionario',
  templateUrl: './novo-funcionario.component.html',
  styleUrls: ['./novo-funcionario.component.css']
})
export class NovoFuncionarioComponent implements OnInit, CanDeactivate {

  @ViewChild('fileInput')
  fileInput!: ElementRef

  funcionario: FormGroup = this.fb.group({
    nome: ['', [ Validators.required ]],
    email: ['', [ Validators.required, Validators.email ]],
    foto: ['']
  })

  foto!: File

  private canExit!: boolean

  constructor(
    private fb: FormBuilder, 
    private funHttpService: FuncionarioHttpService, 
    private router: Router,
    private snackbar: MatSnackBar,
    private dialog: MatDialog,
    private authService: AuthenticationService
    ) { }

  canDeactivate(): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {

    this.authService.logged().subscribe(
      (logged) => {
        this.canExit = !logged
      }
    )

    if (this.canExit) {
      return true
    } else 

    if (this.funcionario.dirty) {
      const ref = this.dialog.open(ConfirmExiteDialogComponent)

      return ref.afterClosed()
    }

    return true
  }

  ngOnInit(): void {
  }

  selectImage():void {
    this.fileInput.nativeElement.click()
  }

  submit(): void {
    let funcionario = this.funcionario.value
    funcionario.foto = null

    this.funHttpService.createFuncionario(funcionario).subscribe(
      (fun) => {
        if ( this.foto != undefined ) {
          const formData: FormData = new FormData()

          formData.append('foto', new Blob([this.foto], { type: this.foto.type }))

          const filename = `funcionario-${fun.idFuncionario}.${this.foto.type.split('/')[1]}`

          this.funHttpService.addfoto(fun.idFuncionario || 0, formData, filename).subscribe(
            () => {
              this.funcionario.reset()
              this.snackbar.open('Funcionario cadastrado com sucesso!', 'Ok', {duration: 3000})

              this.router.navigateByUrl('/funcionarios')
            },
            (error: HttpErrorResponse) => {
              this.snackbar.open(`Ocorreu um erro ao salvar um novo funcionário! (Erro ${error.status})`, 'Ok', {duration: 3000})
            }
          )
        } else {
          this.funcionario.reset()
          this.snackbar.open('Funcionario cadastrado com sucesso!', 'Ok', {duration: 3000})

          this.router.navigateByUrl('/funcionarios')
        }
      },
      (error: HttpErrorResponse) => {
        this.snackbar.open(`Ocorreu um erro ao cadastrar um novo funcionário! (Erro ${error.status})`, 'Ok', {duration: 3000})
      }
    )
  }

  fileChange(event: any) {
    this.foto = event.target.files[0]

    console.log(this.foto)
  }

}
