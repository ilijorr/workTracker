import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-error-page',
  imports: [CommonModule, RouterModule],
  templateUrl: './error-page.html',
  styleUrl: './error-page.css'
})
export class ErrorPageComponent {

  reloadPage() {
    window.location.reload();
  }
}