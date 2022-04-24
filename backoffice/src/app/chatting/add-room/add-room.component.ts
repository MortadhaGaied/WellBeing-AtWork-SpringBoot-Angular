import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Chatroom } from '../chatroom';
import { ChatroomService } from '../chatroom.service';

@Component({
  selector: 'app-add-room',
  templateUrl: './add-room.component.html',
  styleUrls: ['./add-room.component.scss']
})
export class AddRoomComponent implements OnInit {

  room: Chatroom = {
    id: 0,
    roomName: "",
    //this key helps creating unique rooms for one to one chatting
    uniqueKey: "",
    MaxBadWords: 0,
    capacity: 0,
    averageResponseTime: "",
    users: [],
    messages: [],
    isVisible: false,
    status: "",
    cap: "",
  };


  constructor(private service:ChatroomService,private router:Router) { }

  ngOnInit(): void {
  }


  onFormSubmitted() {
    console.log(this.room);
    this.service.createRoom(this.room).subscribe(
      (data) => {
        if(data){
          this.router.navigateByUrl("/chat/chat-rooms")
        }

      },
      (error) => {window.alert(error.message)}

    );
  }

  handleReturnBtn():void{
    this.router.navigateByUrl("/chat/chat-rooms")
  }
}
