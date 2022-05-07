import { Component, OnInit } from '@angular/core';
import { CompatClient, IMessage, Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-stream-chat-box',
  templateUrl: './stream-chat-box.component.html',
  styleUrls: ['./stream-chat-box.component.css'],
})
export class StreamChatBoxComponent implements OnInit {
  constructor() {}
  isVisible: boolean = true;
  senderId: string;
  stompClient: CompatClient;
  messages: any[] = [];
  connected: boolean;
  message: string;

  toggleChatBoxVisibility() {
    this.isVisible = !this.isVisible;
  }

  ngOnInit(): void {
    this.connect();
    const localstorageData = JSON.parse(
      JSON.stringify(localStorage.getItem('user'))
    );
    this.senderId = JSON.parse(localstorageData).id;
    console.log(this.senderId);
  }

  handleClassNameChange(message: any): string {
    if (message.sender.id === this.senderId) return 'me';
    return 'you';
  }

  connect(): void {
    const socket = new SockJS('http://localhost:8081/Wellbeignatwork/ws');
    let _this = this;
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect(
      {},
      () => {
        _this.setConnection(true);
        _this.stompClient.subscribe('/topic2/message', (message: IMessage) => {
          console.log(JSON.parse(message.body));
          _this.messages.push(JSON.parse(message.body));
          console.log(this.messages);
        });
      },
      (err: any) => {
        console.log(err);
      }
    );
  }

  setConnection(connection: boolean) {
    this.connected = connection;
  }

  disconnect(): void {
    if (this.stompClient) {
      this.stompClient.disconnect();
      this.setConnection(false);
    }
  }

  sendMessage(msg: string): void {
    if (msg) {
      this.stompClient.send(
        '/app/chat/public/' + this.senderId,
        {},
        JSON.stringify({ content: msg })
      );
      this.message = '';
    }
  }
}
