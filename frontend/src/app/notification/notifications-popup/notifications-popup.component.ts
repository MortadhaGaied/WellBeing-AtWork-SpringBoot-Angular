import { Component, OnInit } from '@angular/core';
import { ChatroomServiceService } from 'src/app/chatroom/services/chatroom-service.service';
import Swal from 'sweetalert2';
import { NotificationService } from '../notification.service';

@Component({
  selector: 'app-notifications-popup',
  templateUrl: './notifications-popup.component.html',
  styleUrls: ['./notifications-popup.component.css'],
})
export class NotificationsPopupComponent implements OnInit {
  constructor(
    public notificationService: NotificationService,
    private chatroomService: ChatroomServiceService
  ) {}

  ngOnInit(): void {
    console.log(this.notificationService.notifications);
  }
  isLoading: boolean;

  onUserAcceptInvitation(redirectionLink: string, notification: any) {
    this.isLoading = true;
    this.chatroomService
      .acceptUserInvitation(redirectionLink, notification.data.senderID)
      .subscribe({
        next: (res) => {
          this.isLoading = false;
          Swal.fire({
            icon: 'success',
            title: 'you have joined the room successfully',
            //text: user.displayName + ' has been notified',
          });
          this.deleteNotification(notification);
        },
      });
  }

  deleteNotification(notification: any) {
    this.notificationService.notifications.splice(
      this.notificationService.notifications.indexOf(notification)
    );
  }
}
