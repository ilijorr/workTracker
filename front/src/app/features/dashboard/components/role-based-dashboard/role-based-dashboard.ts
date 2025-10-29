import {Component, inject} from '@angular/core';
import {NgComponentOutlet} from '@angular/common';
import {AuthService} from '../../../../core/services/auth-service';
import {AdminDashboard} from '../admin-dashboard/admin-dashboard';
import {EmployeeDashboard} from '../employee-dashboard/employee-dashboard';
import {ErrorPageComponent} from '../../../../core/components/error-page/error-page';

@Component({
  selector: 'app-role-based-dashboard',
  imports: [
    NgComponentOutlet
  ],
  templateUrl: './role-based-dashboard.html',
  styleUrl: './role-based-dashboard.css',
})
export class RoleBasedDashboard {
  authService: AuthService = inject(AuthService);

  getComponentForRole() {
    const role = this.authService.getUserRole()?.toLowerCase();

    switch (role) {
      case 'role_admin':
        return AdminDashboard;
      case 'role_employee':
        return EmployeeDashboard;
      default:
        console.error(role, " is not supported by the app");
        return ErrorPageComponent;
    }
  }

}
