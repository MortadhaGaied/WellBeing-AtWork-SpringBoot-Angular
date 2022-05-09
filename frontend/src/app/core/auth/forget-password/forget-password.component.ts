import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.css'],
})
export class ForgetPasswordComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private tostrService: ToastrService
  ) {}

  forgetForm = this.formBuilder.group({
    email: new FormControl('', [Validators.email, Validators.required]),
  });

  ngOnInit(): void {}

  onForget() {
    this.authService.forget(this.forgetForm.value.email).subscribe({
      next: (res) => {
        if (res.success) {
          this.tostrService.success(res.message, 'Success');
        } else {
          this.tostrService.error(res.message, 'Error');
        }
      },
      error: (err) => {
        this.tostrService.error(err.error.message, 'Error');
      },
    });
  }
}
