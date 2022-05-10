import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { RxStomp } from '@stomp/rx-stomp';
import { map } from 'rxjs';
import * as SockJS from 'sockjs-client';
import { TokenStorageService } from '../services/token-storage.service';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  user: any;
  constructor(
    private router: Router,
    private tokenService: TokenStorageService
  ) {
    this.user = tokenService.getUser();
    console.log(this.user.id);
  }

  client: RxStomp | null;
  public notifications: any[] = [];
  connectClicked() {
    if (!this.client || this.client.connected) {
      this.client = new RxStomp();
      this.client.configure({
        webSocketFactory: () =>
          new SockJS('http://localhost:8081/Wellbeignatwork/ws'),
        debug: (msg: string) => console.log(msg),
      });
      this.client.activate();

      this.watchForNotifications();

      console.info('connected!');
    }
  }

  private watchForNotifications() {
    if (this.client) {
      this.client
        .watch('/topic2/item/' + this.user.id)
        .pipe(
          map((response) => {
            const json = JSON.parse(response.body);
            console.log('Got ');
            console.log(json);
            return json;
          })
        )
        .subscribe((notification: any) =>
          this.notifications.push(notification)
        );
    }
  }
  disconnectClicked() {
    if (this.client && this.client.connected) {
      this.client.deactivate();
      this.client = null;
      console.info('disconnected :-/');
    }
  }

  startClicked() {
    if (this.client && this.client.connected) {
      this.client.publish({ destination: '/app/start/' + this.user.id });
    }
  }

  stopClicked() {
    if (this.client && this.client.connected) {
      this.client.publish({ destination: '/app/stop' + this.user.id });
    }
  }
}
