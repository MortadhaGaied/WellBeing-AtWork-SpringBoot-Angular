import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddBlogComponent } from './blogs/add-blog/add-blog.component';
import { BlogsComponent } from './blogs/blogs.component';

export const routes: Routes = [
  {
    path: "",
    children: [
      { path: "blogs", component: BlogsComponent },
      { path: "addBlog", component: AddBlogComponent }
  ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class ForumRoutingModule { }
