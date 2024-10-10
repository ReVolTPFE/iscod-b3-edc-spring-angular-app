import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
	templateUrl: './home.component.html',
	styleUrls: ['./home.component.scss']
})
export class HomeComponent {
	constructor(public authService: AuthService) {
	}
}
