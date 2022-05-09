import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Route, Router } from '@angular/router';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private tostrService: ToastrService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  token: string | null = this.route.snapshot.queryParamMap.get('token');

  changeForm = this.fb.group({
    password: new FormControl('', Validators.required),
    confirmPassword: new FormControl('', Validators.required),
  });

  ngOnInit(): void {}

  onChangePass() {
    if (
      this.changeForm.value.password !== this.changeForm.value.confirmPassword
    ) {
      this.tostrService.error('Error', "Password doesn't match");
    } else {
      this.authService
        .change(this.changeForm.value.password, this.token)
        .subscribe({
          next: (res) => {
            if (res.success) {
              this.tostrService.success(res.message, 'Success');
              this.router.navigate(['/login']);
            } else {
              this.tostrService.error(res.message, 'Error');
            }
          },
          error: (err) => {
            console.log(err);

            this.tostrService.error(err.error.message, 'Error');
          },
        });
    }
  }
}
