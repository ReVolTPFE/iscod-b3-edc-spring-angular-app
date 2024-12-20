import { Component } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  constructor(public authService: AuthService, public router: Router) {
  }

  logout(event: Event) {
    event.preventDefault();
    this.authService.logout();
    this.router.navigateByUrl('/');
  }
}
