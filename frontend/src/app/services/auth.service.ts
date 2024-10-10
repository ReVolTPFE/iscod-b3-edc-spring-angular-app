import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable, tap } from "rxjs";
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly USER_ID = 'user_id';
  private apiUrl = 'http://localhost:8080/api/auth/login';

  constructor(
    private httpClient: HttpClient,
    private cookieService: CookieService
  ) { }

  login(formData: { email: string; password: string }): Observable<any> {
    return this.httpClient.post<any>(this.apiUrl, formData).pipe(
      tap(response => {
        if (response && response) {
          this.setUserId(response);
        }
      })
    );
  }

  logout(): void {
    this.cookieService.delete(this.USER_ID);
  }

  getUserId(): string | null {
    return this.cookieService.get(this.USER_ID) || null;
  }

  private setUserId(userId: string): void {
    this.cookieService.set(this.USER_ID, userId, 1);
  }

  isAuthenticated(): boolean {
    return this.cookieService.check(this.USER_ID);
  }
}
