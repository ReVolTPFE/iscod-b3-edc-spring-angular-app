import { Component } from '@angular/core';
import {ProjectService} from "../../services/project.service";

interface Project {
  id: number;
  name: string;
  description: string;
  startedAt: string;
}

interface TaskHistory {
  name: string;
  description: string;
  status: string;
  priority: string;
  dueDate: string;
  endedAt: string|null;
}

interface Task {
  id: number;
  projectId: number;
  projectName: string;
  taskHistories: TaskHistory[];
  users: { userId: number, username: string, email: string}[];
}

@Component({
  selector: 'app-task-by-status',
  templateUrl: './task-by-status.component.html',
  styleUrls: ['./task-by-status.component.scss']
})
export class TaskByStatusComponent {
  projects: Project[] = [];
  tasks: Task[] = [];
  selectedView: string = 'all';

  constructor(
    private projectService: ProjectService
  ) {
    this.getProjects();
  }

  selectView(view: string) {
    this.selectedView = view;
  }

  getProjects() {
    this.projectService.getProjects().subscribe({
      next: (response) => {
        this.projects = [];

        response.forEach((project: Project) => {
          this.projects.push(project);
          this.getProjectTasks(project);
        });
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  getProjectTasks(project: Project) {
    this.projectService.getProjectTasks(project.id).subscribe({
      next: (response) => {
        response.forEach((task: Task) => {
          task.projectName = project.name;
          this.tasks.push(task);
        });
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
