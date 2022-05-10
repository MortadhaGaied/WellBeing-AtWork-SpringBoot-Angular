import { NgModule } from '@angular/core';
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

import { MockLoginComponent } from './live-stream/mock-login/mock-login.component';

import { StreamViwerScreenComponent } from './live-stream/stream-viwer-screen/stream-viwer-screen.component';
import { ChatroomsComponent } from './chatroom/chatrooms/chatrooms.component';
import { ChatroomComponent } from './chatroom/chatroom/chatroom.component';
import { AddRoomComponent } from './chatroom/add-room/add-room.component';
import { ChatBoxComponent } from './chatroom/chat-box/chat-box.component';
import { RoomSearchFilterPipe } from './live-stream/pipes/room-search-filter.pipe';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { environment } from '../environments/environment';
import { initializeApp } from 'firebase/app';

import { RoomUserListComponent } from './chatroom/room-user-list/room-user-list.component';
import { MatDialogModule } from '@angular/material/dialog';
import { UserSeachFilterPipe } from './chatroom/pipes/user-seach-filter.pipe';
import { NotificationsPopupComponent } from './notification/notifications-popup/notifications-popup.component';

import { authInterceptorProviders } from './services/auth.interceptor';
import { ChangePasswordComponent } from './core/auth/change-password/change-password.component';
import { LoginComponent } from './core/auth/login/login.component';
import { RegisterComponent } from './core/auth/register/register.component';
import { ForgetPasswordComponent } from './core/auth/forget-password/forget-password.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { RecaptchaFormsModule, RecaptchaModule } from 'ng-recaptcha';
import { QuizComponent } from './core/quiz/quiz.component';
import { NgxUiLoaderHttpModule, NgxUiLoaderModule } from 'ngx-ui-loader';
import { NgxPaginationModule } from 'ngx-pagination';
import { WeatherApiComponent } from './core/weather-api/weather-api.component';
import { QRCodeModule } from 'angularx-qrcode';
import { StartComponent } from './core/start/start.component';
import { InstructionsComponent } from './core/instructions/instructions.component';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import {
  MatFormFieldModule,
  MAT_FORM_FIELD_DEFAULT_OPTIONS,
} from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { LoadQuizComponent } from './core/load-quiz/load-quiz.component';

import { PublicityComponent } from './core/publicity/publicity.component';
import { SidebarComponent } from './core/sidebar/sidebar.component';
import { BrowserModule } from '@angular/platform-browser';

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
    LoginComponent,
    RegisterComponent,
    ForgetPasswordComponent,
    ChangePasswordComponent,
    QuizComponent,
    SidebarComponent,
    InstructionsComponent,
    LoadQuizComponent,
    StartComponent,
    PublicityComponent,
    WeatherApiComponent,
  ],
  imports: [
    MatDialogModule,
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    CarouselModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule, // required animations module
    ToastrModule.forRoot(), // ToastrModule added
    RecaptchaModule,
    RecaptchaFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    QRCodeModule,
    FormsModule,
    HttpClientModule,
    MatSnackBarModule,
    MatCardModule,
    MatToolbarModule,
    MatIconModule,
    MatListModule,
    MatSelectModule,
    MatSlideToggleModule,
    // CKEditorModule
    MatProgressSpinnerModule,
    NgxPaginationModule,
    MatDialogModule,
    NgxUiLoaderModule,
    NgxUiLoaderHttpModule.forRoot({
      showForeground: true,
    }),
  ],
  providers: [authInterceptorProviders],

  bootstrap: [AppComponent],
})
export class AppModule {}
