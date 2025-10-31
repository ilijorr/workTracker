import {Component, inject} from '@angular/core';
import {NgComponentOutlet} from '@angular/common';
import {AuthService} from '../../../../core/services/auth-service';
import {Project} from '../admin-project/project';
import {EmployeeProject} from '../employee-project/employee-project';
import {ErrorPageComponent} from '../../../../core/components/error-page/error-page';

@Component({
  selector: 'app-role-based-project',
  imports: [
    NgComponentOutlet
  ],
  templateUrl: './role-based-project.html',
  styleUrl: './role-based-project.css',
})
export class RoleBasedProject {
  authService: AuthService = inject(AuthService);

  getComponentForRole() {
    const role = this.authService.getUserRole()?.toLowerCase();

    switch (role) {
      case 'role_admin':
        return Project;
      case 'role_employee':
        return EmployeeProject;
      default:
        console.error(role, " is not supported by the app");
        return ErrorPageComponent;
    }
  }

}
