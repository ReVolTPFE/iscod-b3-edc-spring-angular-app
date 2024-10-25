import { TestBed } from '@angular/core/testing';
import {AuthGuard} from "./auth.guard";
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";


describe('AuthGuard', () => {
  let guard: AuthGuard;
  let authServiceMock: any;
  let routerMock: any;

  beforeEach(() => {
    authServiceMock = {
      isAuthenticated: jest.fn()
    };
    routerMock = {
      navigate: jest.fn()
    };

    TestBed.configureTestingModule({
      providers: [
        AuthGuard,
        { provide: AuthService, useValue: authServiceMock },
        { provide: Router, useValue: routerMock }
      ]
    });

    guard = TestBed.inject(AuthGuard);
  });

  it('should allow navigation if user is authenticated', () => {
    authServiceMock.isAuthenticated.mockReturnValue(true);

    const canActivateResult = guard.canActivate({} as any, {} as any);

    expect(canActivateResult).toBe(true);
  });

  it('should redirect if user is not authenticated', () => {
    authServiceMock.isAuthenticated.mockReturnValue(false);

    const canActivateResult = guard.canActivate({} as any, {} as any);

    expect(canActivateResult).toBe(false);

    expect(routerMock.navigate).toHaveBeenCalledWith(['/login'], {
      queryParams: {returnUrl: undefined}
    });
  });
});
