import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { AuthService } from "../../services/auth-services/auth.service";
import { TokenStorageService } from "../../services/auth-services/token-storage.service";
import { ActivatedRoute, Router } from "@angular/router";
import Swal from "sweetalert2";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  constructor(
    private authService: AuthService,
    private tokenService: TokenStorageService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  form: FormGroup = new FormGroup({
    email: new FormControl("", [Validators.required, Validators.email]),
    password: new FormControl("", Validators.required),
  });

  ngOnInit(): void {
    if (this.tokenService.getToken()) {
      this.router.navigate(["/"]);
    }
  }

  submit() {
    this.authService.login(this.form.value).subscribe({
      next: (res) => {
        this.tokenService.saveToken(res.accessToken);
        this.tokenService.saveUser(res.user);
        this.router.navigate(["/"]);
      },
      error: (err) => {
        Swal.fire(
          "Invalid Credentials",
          "Please Check Your Credentials and Try Again!",
          "error"
        );
      },
    });
  }
}
