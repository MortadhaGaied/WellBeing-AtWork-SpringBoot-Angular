import { HttpClient } from '@angular/common/http';
import {
  AfterViewInit,
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import Swal from 'sweetalert2';
import { Message } from '../models/message';

import { Room } from '../models/room';
import { RoomUserListComponent } from '../room-user-list/room-user-list.component';
import { ChatroomServiceService } from '../services/chatroom-service.service';
import { WebsocketsService } from '../services/websockets.service';

@Component({
  selector: 'app-chat-box',
  templateUrl: './chat-box.component.html',
  styleUrls: ['./chat-box.component.css'],
})
export class ChatBoxComponent implements OnInit, OnChanges {
  user: any;

  @Input() chatroom: Room;
  @Input() FullMessages: any[] = [];
  @Input() data: Map<Room, Message[]>;
  message: string;
  constructor(
    private http: HttpClient,
    private webSocketsService: WebsocketsService,
    private dialog: MatDialog,
    private service: ChatroomServiceService
  ) {
    const localstorageData = JSON.parse(
      JSON.stringify(localStorage.getItem('user'))
    );
    this.user = JSON.parse(localstorageData);
  }
  ngOnChanges(changes: SimpleChanges): void {
    this.getUsersByCHatROom();
    console.log(this.chatroom);
    this.retrieveDiscussionPerRoom();
  }

  ngOnInit(): void {
    console.log('this chatroom is ');
    //this.getUsersByCHatROom();
  }

  getUsersByCHatROom() {
    this.service.getUsersByRoom(this.chatroom.id).subscribe({
      next: (users) => {
        console.log('get usersBy room invoked');
        this.chatroom.users = users;
        console.log(this.chatroom);
      },
      error: (error) =>
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'couldnt get users for chatroom : ' + this.chatroom.roomName,
          footer: 'pls try again later',
        }),
    });
  }

  sendMessage(content: string, room: Room, sender: Room) {
    this.webSocketsService.sendMessage(content, room, sender);
    this.message = '';
    //this.stompClient.send(room,{},JSON.stringify({content:msg}))
  }

  saveDiscussion(messages: Message[] | undefined) {
    this.http
      .post('http://localhost:8081/chatroom/save-discussion', {
        messages: messages,
      })
      .subscribe({
        next: (error) =>
          Swal.fire('Good job!', 'disucussion saved ! ', 'success'),
        error: (error) =>
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Something went wrong!',
            footer: '<a href="">Why do I have this issue?</a>',
          }),
      });
  }

  checkMyMessages(message: Message): string {
    let result = 'chat-message-left pb-4';
    if (message.sender.id === this.user.id) result = 'chat-message-right pb-4';
    return result;
  }
  openAddUserToRoomDialog() {
    //this.getUsersByCHatROom();
    this.dialog.open(RoomUserListComponent, {
      data: this.chatroom,
      height: '700px',
      width: '600px',
    });
  }
  // saveDiscussion();

  checkUserBanned() {
    let result = false;
    if (this.chatroom.bannList.includes(this.user.id)) {
      result = true;
    }
    return result;
  }

  retrieveDiscussionPerRoom() {
    this.service.retrieveDiscussionPerRoom(this.chatroom.id).subscribe({
      next: (messages) => {
        this.data.set(this.chatroom, [...messages]);
        console.log(messages);
      },
    });
  }
}
