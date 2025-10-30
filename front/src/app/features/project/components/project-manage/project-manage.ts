import {Component, inject, OnInit, signal} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpErrorResponse} from '@angular/common/http';
import {ProjectService} from '../../project.service';
import {Project} from '../../../../models/Project';
import {PageResponse} from '../../../../models/response/PageResponse';
import {CreateProjectRequest} from '../../../../models/request/CreateProjectRequest';
import {DataTableComponent} from '../../../../shared/components/data-table/data-table';
import {TableConfigService} from '../../../../shared/services/table-config.service';
import {TableConfig} from '../../../../shared/models/table-column';

@Component({
  selector: 'app-project-manage',
  imports: [CommonModule, FormsModule, ReactiveFormsModule, DataTableComponent],
  templateUrl: './project-manage.html',
  styleUrl: './project-manage.css',
})
export class ProjectManageComponent implements OnInit {
  private projectService: ProjectService = inject(ProjectService);
  private tableConfigService: TableConfigService = inject(TableConfigService);
  private formBuilder: FormBuilder = inject(FormBuilder);

  // Projects table
  projects = signal<PageResponse<Project> | null>(null);
  isLoadingProjects = signal(false);

  // Form data
  projectForm: FormGroup;
  isSubmitting = signal(false);

  projectTableConfig: TableConfig;

  constructor() {
    this.projectForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.minLength(10)]]
    });

    this.projectTableConfig = this.tableConfigService.getProjectTableConfig();
  }

  ngOnInit() {
    this.loadProjects();
  }

  private loadProjects() {
    this.isLoadingProjects.set(true);
    this.projectService.getAll().subscribe({
      next: (response: PageResponse<Project>) => {
        this.projects.set(response);
        this.isLoadingProjects.set(false);
      },
      error: (error) => {
        console.error('Failed to load projects:', error);
        this.isLoadingProjects.set(false);
        throw error;
      }
    });
  }

  submitForm() {
    if (this.projectForm.invalid) return;

    const request: CreateProjectRequest = {
      name: this.projectForm.value.name,
      description: this.projectForm.value.description
    };

    this.isSubmitting.set(true);

    this.projectService.create(request).subscribe({
      next: () => {
        this.isSubmitting.set(false);
        this.loadProjects();
        this.resetForm();
      },
      error: (error: HttpErrorResponse) => {
        console.error('Failed to create project:', error);
        this.isSubmitting.set(false);
        throw error;
      }
    });
  }

  resetForm() {
    this.projectForm.reset();
  }

  isFormValid(): boolean {
    return this.projectForm.valid;
  }
}
