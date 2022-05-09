import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BlogDetailComponent } from './blog/blog-detail/blog-detail.component';
import { BlogListComponent } from './blog/blog-list/blog-list.component';
import { HomePageComponent } from './core/home-page/home-page.component';
import { PostDetailComponent } from './core/post-detail/post-detail.component';
import { StreamComponent } from './live-stream/stream/stream.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'live-stream', component: StreamComponent },
  { path: 'blog-list', component: BlogListComponent },
  { path: 'blog-detail/:id', component: BlogDetailComponent },
  { path: 'post-detail/:id', component: PostDetailComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
