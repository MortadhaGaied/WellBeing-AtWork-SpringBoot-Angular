import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class MockLoginServiceService {
  URL: string = 'http://localhost:8081/Wellbeignatwork/api/login';
  constructor(private http: HttpClient) {}

  login(userName: string, password: string) {
    return this.http.post<any>(this.URL + '/' + userName + '/' + password, {});
  }
}
