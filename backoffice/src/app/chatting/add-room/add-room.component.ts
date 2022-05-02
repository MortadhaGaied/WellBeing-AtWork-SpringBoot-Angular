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
    visible: false,
    status: "",
    cap: "",
    image:""
  };

  image:File;
  isLoading:boolean=false;
  constructor(private service:ChatroomService,private router:Router) { }

  ngOnInit(): void {
  }
  onImageSelected(event:any){
    this.image=event.target.files[0];
    console.log(this.image)
  }

  onFormSubmitted() {
    console.log(this.room);
    this.isLoading=true;
    this.service.createRoom(this.room).subscribe(
      (data) => {
        if(data){
          const form = new FormData();
          form.append('image',this.image,this.image.name)
            //console.log(form.get('image'))
          this.service.uploadImage(JSON.parse(JSON.stringify(data)).id,form).subscribe((data2)=>{
            this.isLoading=false;
            this.router.navigateByUrl("/chat/chat-rooms")})

        }

      },
      (error) => {
        this.isLoading=false;
        window.alert(error.message)}

    );
  }

  handleReturnBtn():void{
    this.router.navigateByUrl("/chat/chat-rooms")
  }
}
