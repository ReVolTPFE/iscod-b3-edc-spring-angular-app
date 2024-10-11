import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {NotFoundComponent} from "./pages/not-found/not-found.component";
import {ErrorComponent} from "./pages/error/error.component";
import {AuthGuard} from "./guards/auth.guard";
import {LoginTemplateDrivenComponent} from "./pages/login-template-driven/login-template-driven.component";
import {HomeComponent} from "./pages/home/home.component";
import {ProjectComponent} from "./pages/project/project.component";
import {ProjectDetailComponent} from "./pages/project-detail/project-detail.component";
import {RegisterComponent} from "./pages/register/register.component";
import {TaskComponent} from "./pages/task/task.component";

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: 'login',
    component: LoginTemplateDrivenComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: 'project',
    canActivate: [AuthGuard],
    component: ProjectComponent,
  },
  {
    path: 'project/:projectId',
    canActivate: [AuthGuard],
    component: ProjectDetailComponent,
  },
  {
    path: 'project/:projectId/task/:taskId',
    canActivate: [AuthGuard],
    component: TaskComponent,
  },
  {
    path: 'not-found',
    component: NotFoundComponent,
  },
  {
    path: 'error',
    component: ErrorComponent,
    data: { message: 'La page est introuvable' },
  },
  {
    path: '**',
    redirectTo: '/not-found',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
