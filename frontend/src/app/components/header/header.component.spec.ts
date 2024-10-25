import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderComponent } from './header.component';
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {RouterTestingModule} from "@angular/router/testing";

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;

  const authServiceMock = {
    logout: jest.fn(),
    isAuthenticated: jest.fn().mockReturnValue(true),
  };

  const routerMock = {
    navigateByUrl: jest.fn(),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HeaderComponent ],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: Router, useValue: routerMock },
      ],
      imports: [RouterTestingModule],
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call authService.logout and router.navigateByUrl("/") on logout', () => {
    const event = new MouseEvent('click');

    component.logout(event);

    expect(authServiceMock.logout).toHaveBeenCalled();

    expect(routerMock.navigateByUrl).toHaveBeenCalledWith('/');
  });
});
