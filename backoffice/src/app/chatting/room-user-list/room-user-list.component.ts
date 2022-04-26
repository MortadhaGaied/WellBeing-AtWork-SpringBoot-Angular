import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Chatroom } from '../chatroom';
import { ChatroomService } from '../chatroom.service';

@Component({
  selector: 'app-room-user-list',
  templateUrl: './room-user-list.component.html',
  styleUrls: ['./room-user-list.component.scss']
})
export class RoomUserListComponent implements OnInit {
  room:Chatroom;
  constructor(private service:ChatroomService,private router:Router,private route:ActivatedRoute) { }
  roomId:number;
  users:any[];
  ngOnInit(): void {
    this.retrieveRoomIdFromPath();
    this.retrieveRoomById(this.roomId);
    this.retrieveUsersByChatroom(this.room)

  }


  retrieveRoomIdFromPath():void{
    this.route.params.subscribe(params =>this.roomId=params['id']);
  }

  retrieveRoomById(id:number):void{
    this.service.findRoomById(id).subscribe(room=>this.room=room);
  }

  retrieveUsersByChatroom(room:Chatroom){
    this.service.getUsersByRoom(room.id).subscribe(users=>this.users=users);

}
  OnAddUserClicked():void{
    //TODO:implement dialog select list
  }

  onDeleteUserPressed(user: any){
    //TODO: implement remove user from chatroom
  }
}
