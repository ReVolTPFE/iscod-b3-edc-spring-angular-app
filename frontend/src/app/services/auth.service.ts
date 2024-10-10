import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable, tap } from "rxjs";
import { CookieService } from 'ngx-cookie-service';

interface AuthResponse {
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly TOKEN_KEY = 'auth_token';
  private apiUrl = 'http://localhost:8080/api/auth/login';

  constructor(
    private httpClient: HttpClient,
    private cookieService: CookieService
  ) { }

  login(formData: { email: string; password: string }): Observable<AuthResponse> {
    return this.httpClient.post<AuthResponse>(this.apiUrl, formData).pipe(
      tap(response => {
        if (response && response.token) {
          this.setToken(response.token);
        }
      })
    );
  }

  logout(): void {
    this.cookieService.delete(this.TOKEN_KEY);
  }

  getToken(): string | null {
    return this.cookieService.get(this.TOKEN_KEY) || null;
  }

  private setToken(token: string): void {
    this.cookieService.set(this.TOKEN_KEY, token, 1);
  }

  isAuthenticated(): boolean {
    return this.cookieService.check(this.TOKEN_KEY);
  }
}
