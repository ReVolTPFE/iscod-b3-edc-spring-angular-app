import { ComponentFixture, TestBed } from '@angular/core/testing';
import {ErrorComponent} from "./error.component";
import {ActivatedRoute} from "@angular/router";
import {ProjectService} from "../../services/project.service";

describe('ErrorComponent', () => {
  let component: ErrorComponent;
  let fixture: ComponentFixture<ErrorComponent>;

  beforeEach(async () => {
    const activatedRouteMock = {
      snapshot: {
        data: {
          message: 'Error thrown'
        }
      }
    };

    await TestBed.configureTestingModule({
      declarations: [ ErrorComponent ],
      providers: [
        { provide: ActivatedRoute, useValue: activatedRouteMock }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ErrorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
