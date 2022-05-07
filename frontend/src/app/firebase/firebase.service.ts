import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { getMessaging, getToken, onMessage } from 'firebase/messaging';
import { environment } from 'src/environments/environment';
import Swal from 'sweetalert2';
@Injectable({
  providedIn: 'root',
})
export class FirebaseService {
  constructor(private http: HttpClient, private router: Router) {
    if (localStorage.getItem('user')) {
      const localstorageData = JSON.parse(
        JSON.stringify(localStorage.getItem('user'))
      );
      this.user = JSON.parse(localstorageData);
      console.log(this.user.id);
    } else {
      this.router.navigateByUrl('/login');
    }
  }
  user: any;
  message: any = null;
  requestPermission() {
    const messaging = getMessaging();
    getToken(messaging, { vapidKey: environment.firebase.vapidKey })
      .then((currentToken) => {
        if (currentToken) {
          console.log('Hurraaa!!! we got the token.....');
          console.log(currentToken);
          this.registerTokenToUser(currentToken, this.user.id);
        } else {
          console.log(
            'No registration token available. Request permission to generate one.'
          );
        }
      })
      .catch((err) => {
        console.log('An error occurred while retrieving token. ', err);
      });
  }

  listen() {
    const messaging = getMessaging();
    onMessage(messaging, (payload) => {
      console.log('Message received. ', payload);
      this.message = payload;
      console.log(payload);
      /*Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: payload.notification?.body,
        footer: '<p href="">try again later</p>',
      });*/
    });
  }

  registerTokenToUser(token: string, userID: number) {
    console.log(userID);
    this.http
      .post(
        'http://localhost:8081/Wellbeignatwork/notification/token/' +
          userID +
          '/' +
          token,
        {}
      )
      .subscribe({
        next: (data) => {
          console.log(data);
        },
        error: (error) =>
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'error while communicating with firebase  ',
            footer: '<p href="">try again later</p>',
          }),
      });
  }
}
