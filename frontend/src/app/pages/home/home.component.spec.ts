import { ComponentFixture, TestBed } from '@angular/core/testing';
import {HomeComponent} from "./home.component";
import {AuthService} from "../../services/auth.service";

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  beforeEach(async () => {
    const authServiceMock = {
      isAuthenticated: jest.fn().mockReturnValue(true),
    };

    await TestBed.configureTestingModule({
      declarations: [ HomeComponent ],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
      ],
    })
      .compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
