import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './core/home-page/home-page.component';
import { StreamComponent } from './live-stream/stream/stream.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'live-stream', component: StreamComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
