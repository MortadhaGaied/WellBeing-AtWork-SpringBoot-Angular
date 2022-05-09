import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChatroomComponent } from './chatroom/chatroom/chatroom.component';
import { ChangePasswordComponent } from './core/auth/change-password/change-password.component';
import { ForgetPasswordComponent } from './core/auth/forget-password/forget-password.component';
import { LoginComponent } from './core/auth/login/login.component';
import { RegisterComponent } from './core/auth/register/register.component';
import { HomePageComponent } from './core/home-page/home-page.component';
import { AuthenticationGuard } from './guards/authentication.guard';
import { MockLoginComponent } from './live-stream/mock-login/mock-login.component';
import { StreamViwerScreenComponent } from './live-stream/stream-viwer-screen/stream-viwer-screen.component';
import { StreamComponent } from './live-stream/stream/stream.component';

const routes: Routes = [
  {
    path: '',
    component: HomePageComponent,
    canActivate: [AuthenticationGuard],
  },
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

  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: 'forget-password',
    component: ForgetPasswordComponent,
  },
  {
    path: 'change-password',
    component: ChangePasswordComponent,
  },
  { path: '**', redirectTo: '', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
