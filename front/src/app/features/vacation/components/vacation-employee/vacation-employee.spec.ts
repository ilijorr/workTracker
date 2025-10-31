import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VacationEmployee } from './vacation-employee';

describe('VacationEmployee', () => {
  let component: VacationEmployee;
  let fixture: ComponentFixture<VacationEmployee>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VacationEmployee]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VacationEmployee);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
