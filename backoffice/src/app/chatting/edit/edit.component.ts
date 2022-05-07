import { Component, Inject, Input, OnInit } from "@angular/core";
import { MatDialog, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { ActivatedRoute, Router } from "@angular/router";
import { Chatroom } from "../chatroom";
import { ChatroomService } from "../chatroom.service";

@Component({
  selector: "app-edit",
  templateUrl: "./edit.component.html",
  styleUrls: ["./edit.component.scss"],
})
export class EditComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Chatroom,
    private service: ChatroomService,
    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog
  ) {}

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
  roomId: number;
  file: File;
  //we need to show the selected image in the form before uploading it
  imageBase64Data: string | ArrayBuffer | null = "";
  //perform loading action while fetching data from the server
  isLoading: boolean = false;
  users: any[] = [];
  ngOnInit(): void {
    this.service.getAllUsers().subscribe((users) => {
      this.users = users;
    });
    //pass the room data from the parent component to the child using material dialog
    this.room = this.data;
  }

  onFileSelected(event: any): void {
    this.getBase64(event);
    this.file = event.target.files[0];
  }

  onFormSubmitted() {
    this.isLoading = true;
    console.log(this.room);
    this.service.updateRoom(this.room).subscribe({
      next: (data) => {
        if (data) {
          // update done successfully here
          if (this.file) {
            // a new file was selected here
            console.log(this.file);
            const formData = new FormData();
            formData.append("image", this.file, this.file.name);
            this.service.uploadImage(this.room.id, formData).subscribe({
              complete: () => {
                console.log(data);
                window.location.reload();
                this.dialog.closeAll();
                this.isLoading = false;
              },
              error: () => (this.isLoading = false),
            });
          } else {
            //if there is no files selected in the input
            console.log(data);
            window.location.reload();
            this.dialog.closeAll();
            this.isLoading = false;
          }
        } else {
          console.log("no data from the server");
        }
      },
      error: (error) => {
        window.alert(error.message);
      },
    });
  }

  handleReturnBtn(): void {
    window.location.reload();
    this.dialog.closeAll();
  }

  retrieveRoomId(): void {
    this.route.params.subscribe((param) => (this.roomId = param["id"]));
  }

  retrieveRoomById(id: number) {
    this.service
      .findRoomById(this.roomId)
      .subscribe((chatroom) => (this.room = chatroom));
  }
  getBase64(event: any) {
    let file = event.target.files[0];
    let reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      //console.log(reader.result);
      this.imageBase64Data = reader.result;
    };
    reader.onerror = function (error) {
      console.log("Error: ", error);
    };
  }
}
