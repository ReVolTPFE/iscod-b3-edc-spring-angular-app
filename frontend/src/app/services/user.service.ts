import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable, tap } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseApiUrl = 'http://localhost:8080/api/user';

  constructor(
    private httpClient: HttpClient,
  ) { }

  getUsers(): Observable<any> {
    return this.httpClient.get<any>(this.baseApiUrl).pipe(
      tap(response => {

      })
    );
  }
}
