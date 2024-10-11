import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable, tap } from "rxjs";
import {AuthService} from "./auth.service";

interface ProjectBody {
  name: string;
  description: string;
  startedAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private userId = this.authService.getUserId();
  private baseApiUrl = 'http://localhost:8080/api/user/' + this.userId + '/project';

  constructor(
    private httpClient: HttpClient,
    private authService: AuthService,
  ) { }

  createProject(formData: ProjectBody): Observable<any> {
    return this.httpClient.post<any>(this.baseApiUrl+"/create", formData).pipe(
      tap(response => {
        if (response) {
          console.log(response)
        }
      })
    );
  }

  getProjects(): Observable<any> {
    return this.httpClient.get<ProjectBody[]>(this.baseApiUrl).pipe(
      tap(response => {

      })
    );
  }

  getProject(projectId: number): Observable<any> {
    return this.httpClient.get<any>(this.baseApiUrl + '/' + projectId).pipe(
      tap(response => {

      })
    );
  }

  getProjectTasks(projectId: number): Observable<any> {
    return this.httpClient.get<any>(this.baseApiUrl + '/' + projectId + '/task').pipe(
      tap(response => {

      })
    );
  }
}
