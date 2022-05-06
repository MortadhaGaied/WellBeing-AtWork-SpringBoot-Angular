import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CompatClient } from '@stomp/stompjs';
import { Room } from '../models/room';
import { ChatroomServiceService } from '../services/chatroom-service.service';
import { WebsocketsService } from '../services/websockets.service';
import { Message } from '../models/message';
@Component({
  selector: 'app-chatroom',
  templateUrl: './chatroom.component.html',
  styleUrls: ['./chatroom.component.css'],
})
export class ChatroomComponent implements OnInit {
  constructor(
    private http: HttpClient,
    private webSocketsService: WebsocketsService,
    private chatroomService: ChatroomServiceService
  ) {
    const localstorageData = JSON.parse(
      JSON.stringify(localStorage.getItem('user'))
    );
    this.user = JSON.parse(localstorageData);
  }

  title = 'chat';
  stompClient: CompatClient;
  message: string;
  chatRooms: Room[] = [];
  messages: Message[] = [];
  clickedRoom: Room;
  FullMessages: Message[] = [];
  data: Map<Room, Message[]> = new Map<Room, Message[]>();
  user: any;
  searchFilter: string;

  ngOnInit(): void {
    console.log(this.user);

    this.webSocketsService.connect();
    this.getAllRooms();
    setTimeout(() => {
      this.subscribeToAllTopics();
    }, 500);
  }
  ngAfterViewInit(): void {
    console.log('after onInit');
  }

  ngOnDestroy(): void {
    console.log('saving ...');
    this.saveDiscussion(this.FullMessages);
    this.webSocketsService.disconnect();
    console.log('done saving ...');
  }

  getAllRooms() {
    this.chatroomService.getAllRooms().subscribe(async (rooms) => {
      this.chatRooms = rooms;
    });
  }

  sendMessage(content: string, room: Room, sender: any) {
    this.webSocketsService.sendMessage(content, room, sender);
    //this.stompClient.send(room,{},JSON.stringify({content:msg}))
  }

  subscribeToTopic(topic: string): void {
    this.stompClient.subscribe(topic, (msg) => {
      console.log(msg);
      this.messages.push(JSON.parse(msg.body));
    });
  }

  subscribeToAllTopics() {
    this.chatRooms.forEach((chatroom) => {
      this.webSocketsService
        .subscribeToTopic()
        .subscribe('/topic2/room/' + chatroom.id, (message) => {
          console.log(JSON.parse(message.body));
          this.messages.push(JSON.parse(message.body).content);
          this.FullMessages.push(JSON.parse(message.body));
          this.updateMessages();
          console.log(this.data);
        });
    });
  }

  handleOnClick(channel: Room): void {
    this.clickedRoom = channel;
    console.log(this.clickedRoom);
  }

  updateMessages() {
    this.chatRooms.forEach((chatroom) => {
      let messagesPerChatroom: Message[] = [];
      this.FullMessages.forEach((message) => {
        console.log(message.chatroom?.id);
        console.log(chatroom.id);
        if (message.chatroom?.id == chatroom.id) {
          messagesPerChatroom.push(message);
        }
      });
      this.data.set(chatroom, messagesPerChatroom);
    });
  }

  saveDiscussion(messages: any[]) {
    this.http
      .post('http://localhost:8089/chatroom/save-discussion', {
        messages: messages,
      })
      .subscribe();
  }
}
