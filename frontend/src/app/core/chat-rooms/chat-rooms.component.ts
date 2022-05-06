import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { OwlOptions } from 'ngx-owl-carousel-o';
import { ChatroomServiceService } from 'src/app/chatroom/services/chatroom-service.service';
import { Room } from '../../chatroom/models/room';
import { SlidesOutputData } from 'ngx-owl-carousel-o';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-chat-rooms',
  templateUrl: './chat-rooms.component.html',
  styleUrls: ['./chat-rooms.component.css'],
})
export class ChatRoomsComponent implements OnInit {
  constructor(private service: ChatroomServiceService) {}

  ngOnInit(): void {
    this.retrieveAllRooms();
  }
  activeSlides: SlidesOutputData;

  chatRooms: Room[] = [];
  customOptions: OwlOptions = {
    loop: true,
    margin: 10,
    mouseDrag: false,
    touchDrag: false,
    pullDrag: false,
    dots: false,
    navSpeed: 700,
    navText: ['', ''],
    responsive: {
      0: {
        items: 1,
      },
      400: {
        items: 2,
      },
      740: {
        items: 3,
      },
      940: {
        items: 4,
      },
    },
    nav: true,
  };
  retrieveAllRooms() {
    this.service.getAllRooms().subscribe({
      next: (rooms: Room[]) => {
        this.chatRooms = rooms;
      },
      error: (error) =>
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'cant get chat rooms from the server ',
          footer: '<p href="">try later</p>',
        }),
    });
  }
}
