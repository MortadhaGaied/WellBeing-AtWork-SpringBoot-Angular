import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { AppConstants } from "./app.constants";
import { UserType } from "./user";

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

@Injectable({
  providedIn: "root",
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post(
      AppConstants.AUTH_API + "signin",
      {
        email: credentials.email,
        password: credentials.password,
      },
      httpOptions
    );
  }

  register(user: UserType): Observable<any> {
    return this.http.post(
      AppConstants.AUTH_API + "signup",
      {
        displayName: user.displayName,
        email: user.email,
        password: user.password,
        matchingPassword: user.matchingPassword,
        socialProvider: "LOCAL",
      },
      httpOptions
    );
  }

  forget(email: any): Observable<any> {
    return this.http.post(
      `${AppConstants.AUTH_API}forget-password?email=${email}`,
      httpOptions
    );
  }

  change(password: string, token: string | null): Observable<any> {
    return this.http.post(
      `${AppConstants.AUTH_API}reset-password?token=${token}`,
      { newPassword: password },
      httpOptions
    );
  }
}
