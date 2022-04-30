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
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';


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
    StreamComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
