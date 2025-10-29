import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleBasedProject } from './role-based-project';

describe('RoleBasedProject', () => {
  let component: RoleBasedProject;
  let fixture: ComponentFixture<RoleBasedProject>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RoleBasedProject]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RoleBasedProject);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
