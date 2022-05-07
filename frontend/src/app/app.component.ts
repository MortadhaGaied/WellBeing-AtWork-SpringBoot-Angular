import { Component, OnInit } from '@angular/core';
import { RxStomp } from '@stomp/rx-stomp';
import { map } from 'rxjs';
import * as SockJS from 'sockjs-client';
import { FirebaseService } from './firebase/firebase.service';
import { NotificationService } from './notification/notification.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'frontend';

  constructor(
    private firebaseService: FirebaseService,
    public notificationService: NotificationService
  ) {}
  ngOnInit(): void {
    this.firebaseService.requestPermission();
    this.firebaseService.listen();
    this.notificationService.connectClicked();
    //this.notificationService.startClicked();
  }
}
