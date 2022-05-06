import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChatroomComponent } from './chatroom/chatroom/chatroom.component';
import { HomePageComponent } from './core/home-page/home-page.component';
import { MockLoginComponent } from './live-stream/mock-login/mock-login.component';
import { StreamViwerScreenComponent } from './live-stream/stream-viwer-screen/stream-viwer-screen.component';
import { StreamComponent } from './live-stream/stream/stream.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  {
    path: 'live-stream',
    children: [
      {
        path: ':id',
        component: StreamComponent,
      },
    ],
  },
  { path: 'room-based', component: ChatroomComponent },
  { path: 'watch', component: StreamViwerScreenComponent },
  { path: 'login', component: MockLoginComponent },
  //{ path: '**', redirectTo: '', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
