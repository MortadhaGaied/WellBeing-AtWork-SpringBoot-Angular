import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { UserService } from 'src/app/services/user.service';
import { AppConstants } from '../../../services/app.constants';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private tokenService: TokenStorageService,
    private userService: UserService,
    private toastrService: ToastrService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  googleURL = AppConstants.GOOGLE_AUTH_URL;
  linkedinURL = AppConstants.LINKEDIN_AUTH_URL;
  currentUser = null;
  loginForm = this.formBuilder.group({
    email: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    recaptchaReactive: new FormControl(null, Validators.required),
  });

  ngOnInit(): void {
    const token: string | null = this.route.snapshot.queryParamMap.get('token');
    const error: string | null = this.route.snapshot.queryParamMap.get('error');
    if (this.tokenService.getToken()) {
      this.currentUser = this.tokenService.getUser();
    } else if (token) {
      this.tokenService.saveToken(token);
      this.userService.getCurrentUser().subscribe(
        (res) => {
          this.login(res);
        },
        (err) => {
          console.log(err);
          this.toastrService.error(err?.error?.message, err?.error?.error);
        }
      );
    }
  }

  onLogin() {
    this.authService.login(this.loginForm.value).subscribe({
      next: (res) => {
        this.tokenService.saveToken(res.accessToken);
        this.login(res.user);
      },
      error: (err) => {
        console.log(err);
        this.toastrService.error(err?.error?.message, err?.error?.error);
      },
    });
  }

  login(user: any) {
    this.tokenService.saveUser(user);
    this.toastrService.success('Your Logged In', 'Success');
    this.router.navigate(['/']);
  }
}
