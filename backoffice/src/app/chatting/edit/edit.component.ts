import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Chatroom } from '../chatroom';
import { ChatroomService } from '../chatroom.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss']
})
export class EditComponent implements OnInit {
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
    visible: false,
    status: "",
    cap: "",
    image:""
  };
  constructor(private service:ChatroomService,private router:Router,private route:ActivatedRoute) { }


  roomId:number;

  ngOnInit(): void {

  this.retrieveRoomId();
  this.retrieveRoomById(this.roomId);

  }

  onFormSubmitted() {
    console.log(this.room)
    this.service.updateRoom(this.room).subscribe(
      (data) => {
        if(data){
          console.log(data)
          this.router.navigateByUrl("/chat/chat-rooms")
        }

      },
      (error) => {window.alert(error.message)}

    );
  }

  handleReturnBtn():void{
    this.router.navigateByUrl("/chat/chat-rooms")
  }

  retrieveRoomId():void{
    this.route.params.subscribe(param=>this.roomId=param['id']);
  }

  retrieveRoomById(id:number){
    this.service.findRoomById(this.roomId).subscribe(chatroom=>this.room=chatroom)
  }
}
