import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HeaderComponent } from './shared/header/header.component';
import { SideBarComponent } from './shared/side-bar/side-bar.component';
import { FooterComponent } from './shared/footer/footer.component';
import { HomePageComponent } from './core/home-page/home-page.component';
import { AdvertismentCarouselComponent } from './core/advertisment-carousel/advertisment-carousel.component';
import { SponsordComponent } from './core/sponsord/sponsord.component';
import { EventWidgetComponent } from './core/event-widget/event-widget.component';
import { QaWidgetComponent } from './core/qa-widget/qa-widget.component';
import { SharePostComponent } from './core/share-post/share-post.component';
import { ChatRoomsComponent } from './core/chat-rooms/chat-rooms.component';
import { StoriesComponent } from './core/stories/stories.component';
import { CreatePostComponent } from './core/create-post/create-post.component';
import { LinksComponent } from './core/links/links.component';
import { RecentBlogComponent } from './core/recent-blog/recent-blog.component';
import { CompleteProfileComponent } from './core/complete-profile/complete-profile.component';
import { StreamComponent } from './live-stream/stream/stream.component';
import { AppRoutingModule } from './app-routing.module';
import { StreamScreenComponent } from './live-stream/stream-screen/stream-screen.component';
import { StreamChatBoxComponent } from './live-stream/stream-chat-box/stream-chat-box.component';
import { StreamConfigBoxComponent } from './live-stream/stream-config-box/stream-config-box.component';
import { FormsModule } from '@angular/forms';
import { MockLoginComponent } from './live-stream/mock-login/mock-login.component';
import { HttpClientModule } from '@angular/common/http';
import { StreamViwerScreenComponent } from './live-stream/stream-viwer-screen/stream-viwer-screen.component';
import { ChatroomsComponent } from './chatroom/chatrooms/chatrooms.component';
import { ChatroomComponent } from './chatroom/chatroom/chatroom.component';
import { AddRoomComponent } from './chatroom/add-room/add-room.component';
import { ChatBoxComponent } from './chatroom/chat-box/chat-box.component';
import { RoomSearchFilterPipe } from './live-stream/pipes/room-search-filter.pipe';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { environment } from '../environments/environment';
import { initializeApp } from 'firebase/app';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RoomUserListComponent } from './chatroom/room-user-list/room-user-list.component';
import { MatDialogModule } from '@angular/material/dialog';
import { UserSeachFilterPipe } from './chatroom/pipes/user-seach-filter.pipe';
import { NotificationsPopupComponent } from './notification/notifications-popup/notifications-popup.component';
initializeApp(environment.firebase);
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SideBarComponent,
    FooterComponent,
    HomePageComponent,
    AdvertismentCarouselComponent,
    SponsordComponent,
    EventWidgetComponent,
    QaWidgetComponent,
    SharePostComponent,
    ChatRoomsComponent,
    StoriesComponent,
    CreatePostComponent,
    LinksComponent,
    RecentBlogComponent,
    CompleteProfileComponent,
    StreamComponent,
    StreamScreenComponent,
    StreamChatBoxComponent,
    StreamConfigBoxComponent,
    MockLoginComponent,
    StreamViwerScreenComponent,
    ChatroomsComponent,
    ChatroomComponent,
    AddRoomComponent,
    ChatBoxComponent,
    RoomSearchFilterPipe,
    RoomUserListComponent,
    UserSeachFilterPipe,
    NotificationsPopupComponent,
  ],
  imports: [
    MatDialogModule,
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    CarouselModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
