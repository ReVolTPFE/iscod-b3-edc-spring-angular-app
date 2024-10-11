import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable, tap } from "rxjs";
import {AuthService} from "./auth.service";

interface ProjectBody {
  name: string;
  description: string;
  startedAt: string;
}

interface TaskBody {
  name: string;
  description: string;
  status: string;
  priority: string;
  dueDate: string;
  endedAt: string|null;
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

  addUserInProject(projectId: number, userEmail: string): Observable<any> {
    return this.httpClient.post<any>(this.baseApiUrl+"/" + projectId + "/addUser", userEmail).pipe(
      tap(response => {

      })
    );
  }

  addTaskInProject(projectId: number, task: TaskBody) {
    return this.httpClient.post<any>(this.baseApiUrl+"/" + projectId + "/task/create", task).pipe(
      tap(response => {

      })
    );
  }

  editTaskInProject(projectId: number, taskId: number, task: TaskBody) {
    return this.httpClient.patch<any>(this.baseApiUrl+"/" + projectId + "/task/" + taskId + '/edit', task).pipe(
      tap(response => {

      })
    );
  }

  getTask(projectId: number, taskId: number): Observable<any> {
    return this.httpClient.get<any>(this.baseApiUrl + '/' + projectId + '/task/' + taskId).pipe(
      tap(response => {

      })
    );
  }

  changeUserRole(projectId: number, userToChangeRoleId: number, role: string) {
    return this.httpClient.put<any>(this.baseApiUrl+"/" + projectId + "/changeUserRole/" + userToChangeRoleId, role).pipe(
      tap(response => {

      })
    );
  }
}
