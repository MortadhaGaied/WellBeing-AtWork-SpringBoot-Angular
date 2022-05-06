import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Room } from '../models/room';
import { ChatroomServiceService } from '../services/chatroom-service.service';

@Component({
  selector: 'app-room-user-list',
  templateUrl: './room-user-list.component.html',
  styleUrls: ['./room-user-list.component.css'],
})
export class RoomUserListComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Room,
    private service: ChatroomServiceService,
    private dialog: MatDialog,
    private router: Router
  ) {
    if (localStorage.getItem('user')) {
      const localstorageData = JSON.parse(
        JSON.stringify(localStorage.getItem('user'))
      );
      this.currentUser = JSON.parse(localstorageData);
      console.log(this.currentUser.id);
    } else {
      this.router.navigateByUrl('/login');
    }
  }
  currentUser: any;
  public searchValue: string = '';

  public users: any[] = [];
  isLoading: boolean = false;
  userMap: Map<any, boolean> = new Map();

  /**TODO:
   * 1) get all users and show them in the dropList
   * 2) set icons for each user based on if the user already a member of the chatroom or not
   * 3) implement a method to add the user to the chatroom if the user isnt a member yet
   */

  ngOnInit(): void {
    this.getAllUsers();
  }

  public getAllUsers(): void {
    this.service.getAllUsers().subscribe({
      next: (users: any[]) => (this.users = users),
    });
  }

  public checkIfUserIsMember(chatroom: Room, user: any): boolean {
    let result = false; // initial value
    chatroom.users?.map((roomUser) => {
      if (roomUser.id === user.id) {
        result = true;
      }
    });
    /*console.log(
      "result from checking user existance in room " +
        chatroom.roomName +
        "is : " +
        result
    );*/
    return result;
  }

  onAddUserToChatRoom(user: any, roomId: number) {
    this.service
      .inviteUserToRoom(user.id, roomId, this.currentUser.id)
      .subscribe({
        next: () => {
          console.log('user invited to chatroom');
          console.log('userId' + user.id + 'room id : ' + roomId);
          Swal.fire({
            icon: 'success',
            title: 'user invited successfully',
            text: user.displayName + ' has been notified',
          });
          //console.log('user added to chatroom' + this.data.id);
          /**
         this method is to force the update on the parent component (in our case usersListComponent)
         */
          //this.data.users?.push(user);
        },
        error: (error) => {
          //console.log(error);
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Something went wrong!',
            footer: error.error.message,
          });
        },
      });
  }

  onDialogClosed(): void {
    window.location.reload();
    this.dialog.closeAll();
  }

  setHoverColor(event: MouseEvent) {
    const item: HTMLLIElement = <HTMLLIElement>event.target;

    switch (event.type) {
      case 'mouseenter':
        item.className = 'list-group-item active';
        break;
      case 'mouseleave':
        item.className = 'list-group-item';
        break;
      default:
        item.className = 'list-group-item';
        break;
    }
  }
}
