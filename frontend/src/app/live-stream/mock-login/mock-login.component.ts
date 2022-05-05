import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MockLoginServiceService } from '../services/mock-login-service.service';

@Component({
  selector: 'app-mock-login',
  templateUrl: './mock-login.component.html',
  styleUrls: ['./mock-login.component.css'],
})
export class MockLoginComponent implements OnInit {
  form: any = {
    username: null,
    password: null,
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  constructor(
    private authService: MockLoginServiceService,

    private router: Router
  ) {}
  ngOnInit(): void {}
  onSubmit(): void {
    const { username, password } = this.form;
    this.authService.login(username, password).subscribe({
      next: (data) => {
        localStorage.setItem('user', JSON.stringify(data));

        console.log(data);
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.router.navigate(['/']);
        //this.reloadPage();
      },
      error: (err) => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      },
    });
  }
  reloadPage(): void {
    window.location.reload();
  }
}
