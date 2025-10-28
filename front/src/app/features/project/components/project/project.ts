import {Component, inject, OnInit, signal} from '@angular/core';
import {ProjectService} from '../../project.service';
import {ActivatedRoute} from '@angular/router';
import {Project as ProjectModel} from '../../../../models/Project';

@Component({
  selector: 'app-project',
  imports: [],
  templateUrl: './project.html',
  styleUrl: './project.css',
})
export class Project implements OnInit {
  private projectService: ProjectService = inject(ProjectService);
  private route: ActivatedRoute = inject(ActivatedRoute);

  projectId = signal<number>(-1);
  project = signal<ProjectModel | null>(null);
  isLoading = signal(false);

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        const projectId = parseInt(id, 10);
        this.projectId.set(projectId);
        this.loadProject(projectId);
      }

    })
  }

  private loadProject(id: number) {
    this.isLoading.set(true);

    this.projectService.get(id).subscribe({
      next: project => {
        this.project.set(project);
      },
      error: error => {
        console.error('Failed to load project', error);
      }
    })
    this.isLoading.set(false);
  }

}
