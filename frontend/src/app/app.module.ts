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
    LoginComponent,
    RegisterComponent,
    ForgetPasswordComponent,
    ChangePasswordComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule, // required animations module
    ToastrModule.forRoot(), // ToastrModule added
    RecaptchaModule,
    RecaptchaFormsModule,
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent],
})
export class AppModule {}
