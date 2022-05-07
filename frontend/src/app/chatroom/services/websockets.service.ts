import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CompatClient, Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { Room } from '../models/room';

@Injectable({
  providedIn: 'root',
})
export class WebsocketsService {
  constructor(private http: HttpClient) {}
  URL: string = 'http://localhost:8081/Wellbeignatwork/ws';
  stompClient: CompatClient;

  connect(): void {
    const socket = new SockJS(this.URL);
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, (frame: any) => {
      console.log('connected : ' + frame);
    });
  }

  disconnect(): void {
    this.stompClient.disconnect(() => {
      console.log('disconnecting ... ');
    });
  }

  sendMessage(msg: string, room: Room, sender: any) {
    console.log('sender is :: ' + sender.id);
    console.log('room id is ::' + room.id);
    this.stompClient.send(
      '/app/chat/' + room.id + '/' + sender.id,
      {},
      JSON.stringify({ content: msg })
    );
  }

  subscribeToTopic(): CompatClient {
    return this.stompClient;
  }
}
