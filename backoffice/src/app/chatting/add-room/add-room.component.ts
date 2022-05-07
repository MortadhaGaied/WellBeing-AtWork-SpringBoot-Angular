import { Component, OnInit } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { Router } from "@angular/router";
import Swal from "sweetalert2";
import { Chatroom } from "../chatroom";
import { ChatroomService } from "../chatroom.service";

@Component({
  selector: "app-add-room",
  templateUrl: "./add-room.component.html",
  styleUrls: ["./add-room.component.scss"],
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
    image: "",
    ownerId: 0,
  };

  image: File;
  isLoading: boolean = false;
  users: any[] = [];
  constructor(
    private service: ChatroomService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.service.getAllUsers().subscribe((users) => {
      this.users = users;
    });
  }
  onImageSelected(event: any) {
    this.image = event.target.files[0];
    console.log(this.image);
  }

  onFormSubmitted() {
    console.log(this.room);
    this.isLoading = true;
    this.service.createRoom(this.room).subscribe(
      (data) => {
        if (data) {
          if (this.image) {
            const form = new FormData();
            form.append("image", this.image, this.image.name);
            //console.log(form.get('image'))
            this.service
              .uploadImage(JSON.parse(JSON.stringify(data)).id, form)
              .subscribe((data2) => {
                this.isLoading = false;
                window.location.reload();
                this.dialog.closeAll();
              });
          } else {
            this.isLoading = false;
            window.location.reload();
            this.dialog.closeAll();
          }
        }
      },
      (error) => {
        this.isLoading = false;
        Swal.fire({
          icon: "error",
          title: "failure",
          text: "pls verify you input",
          footer: '<a href="">try again ?</a>',
        });
      }
    );
  }

  handleReturnBtn(): void {
    this.router.navigateByUrl("/chat/chat-rooms");
  }
}
