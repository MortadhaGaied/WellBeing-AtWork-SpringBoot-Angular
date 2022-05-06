import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  /*   dep: any = [
    { id: 'IT', name: 'IT' },
    { id: 'RH', name: 'RH' },
    { id: 'MAINTENANCE', name: 'MAINTENANCE' },
  ]; */
  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private tokenService: TokenStorageService,
    private toastrService: ToastrService,
    private router: Router
  ) {}

  registerForm = this.formBuilder.group({
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    email: new FormControl('', Validators.required),
    displayName: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    matchingPassword: new FormControl('', Validators.required),
    gender: new FormControl('', Validators.required),
    recaptchaReactive: new FormControl(null, Validators.required),

    /* departement: new FormControl(null), */
  });

  ngOnInit(): void {}

  onRegister() {
    console.log(this.registerForm.value);
    this.authService.register(this.registerForm.value).subscribe({
      next: (res) => {
        console.log(res);
        this.toastrService.success(
          'User Registred Successfully, Please Check Your Email To Confirm Your Account.',
          'Success'
        );
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.log(err);
        this.toastrService.error(
          'Please Check Your Input And Try Again!',
          'Error'
        );
      },
    });
  }
}
