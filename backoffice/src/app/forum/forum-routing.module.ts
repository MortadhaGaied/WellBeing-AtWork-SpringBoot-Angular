import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddBlogComponent } from './blogs/add-blog/add-blog.component';
import { BlogsStatsComponent } from './blogs/blogs-stats/blogs-stats.component';
import { BlogsComponent } from './blogs/blogs.component';
import { ReportedPostComponent } from './reported-post/reported-post.component';

export const routes: Routes = [
  {
    path: "",
    children: [
      { path: "blogs", component: BlogsComponent },
      { path: "addBlog", component: AddBlogComponent },
      { path: "posts", component: ReportedPostComponent },
      { path: "poststat", component: BlogsStatsComponent }
  ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class ForumRoutingModule { }
