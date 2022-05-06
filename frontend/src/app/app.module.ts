import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule, HttpClient } from '@angular/common/http';
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
import { EventListComponent } from './event/event-list/event-list.component';
import { EventDetailComponent } from './event/event-detail/event-detail.component';
import { NgxMaterialRatingModule } from 'ngx-material-rating';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { NgxStarRatingModule } from 'ngx-star-rating';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { GameComponent } from './event/game/game.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import * as Hammer from 'hammerjs';

import { FullCalendarModule } from '@fullcalendar/angular';
import interactionPlugin from '@fullcalendar/interaction';
import dayGridPlugin from '@fullcalendar/daygrid';
import {NgxPrintModule} from 'ngx-print';
FullCalendarModule.registerPlugins([interactionPlugin, dayGridPlugin]);
import {
  HammerGestureConfig,
  HAMMER_GESTURE_CONFIG,
  HammerModule,
} from '@angular/platform-browser';
import { WeatherComponent } from './event/weather/weather.component';
import { CalendarComponent } from './event/calendar/calendar.component';

class HammerConfig extends HammerGestureConfig {
  override overrides = <any>{
    swipe: { direction: Hammer.DIRECTION_ALL },
  };
}

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
    EventListComponent,
    EventDetailComponent,
    GameComponent,
    WeatherComponent,
    CalendarComponent,
 

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgxMaterialRatingModule,
    PdfViewerModule,
    FormsModule,
    ReactiveFormsModule,
    NgxStarRatingModule,
    NgxPrintModule,
    FullCalendarModule,
    HammerModule,
    BrowserAnimationsModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: environment.production,
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    })
  ],
 
  providers: [
    {
      provide: HAMMER_GESTURE_CONFIG,
      useClass: HammerConfig,
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
