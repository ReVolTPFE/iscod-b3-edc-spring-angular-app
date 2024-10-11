import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ProjectService} from "../../services/project.service";

interface Project {
  id: number;
  name: string;
  description: string;
  startedAt: string;
}

@Component({
  selector: 'app-project-detail',
  templateUrl: './project-detail.component.html',
  styleUrls: ['./project-detail.component.scss']
})
export class ProjectDetailComponent {
  protected project?: Project;
  protected tasks?: Record<any, any>;
  projectId = Number(this.route.snapshot.paramMap.get('projectId'));

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private projectService: ProjectService
  ) {
    this.getProject(this.projectId);

    this.getProjectTasks(this.projectId);
  }

  getProject(projectId: number) {
    this.projectService.getProject(projectId).subscribe({
      next: (response) => {
        this.project = response;
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  getProjectTasks(projectId: number) {
    this.projectService.getProjectTasks(projectId).subscribe({
      next: (response) => {
        this.tasks = response;
        console.log(response)
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  getLastTaskHistory(task: any) {
    return task.taskHistories.length > 0 ? task.taskHistories[task.taskHistories.length - 1] : null;
  }
}
