import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeProject } from './employee-project';

describe('EmployeeProject', () => {
  let component: EmployeeProject;
  let fixture: ComponentFixture<EmployeeProject>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployeeProject]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmployeeProject);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
