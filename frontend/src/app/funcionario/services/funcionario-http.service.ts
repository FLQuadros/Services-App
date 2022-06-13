import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Funcionario } from '../models/funcionario';

@Injectable({
  providedIn: 'root'
})
export class FuncionarioHttpService {

  private readonly baseURL = 'http://localhost:8080/servicos/funcionarios'

  constructor(private http: HttpClient) { }

  getFuncionarios(): Observable<Funcionario[]> {
    return this.http.get<Funcionario[]>(this.baseURL)
  }

  getFuncionarioById(idFuncionario: number): Observable<Funcionario> {
    return this.http.get<Funcionario>(`${this.baseURL}/${idFuncionario}`)
  }

  deleteFuncionarioById(idFuncionario: number): Observable<void> {
    return this.http.delete<void>(`${this.baseURL}/${idFuncionario}`)
  }

  createFuncionario(funcionario: Funcionario): Observable<Funcionario> {
    return this.http.post<Funcionario>(this.baseURL, funcionario)
  }

  addfoto(idFuncionario: number, data: FormData, filename: string): Observable<void> {
    return this.http.post<void>(`http://localhost:8080/servicos/funcionario/uploadFoto/${idFuncionario}?nome=${filename}`, data)
  }

  uptadeFuncionario(funcionario: Funcionario): Observable<Funcionario> {
    return this.http.put<Funcionario>(`${this.baseURL}/${funcionario.idFuncionario}`, funcionario)
  }
  


}
