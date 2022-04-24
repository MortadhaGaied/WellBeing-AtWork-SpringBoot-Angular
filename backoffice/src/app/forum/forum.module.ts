import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule,  } from '@angular/forms';
import { ForumRoutingModule, routes } from './forum-routing.module';
import { BlogsComponent } from './blogs/blogs.component';
import { RouterModule } from '@angular/router';
import { ComponentsModule } from "../component/component.module";
import { AddBlogComponent } from './blogs/add-blog/add-blog.component';
import { BlogDetailComponent } from './blogs/blog-detail/blog-detail.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatChipsModule} from '@angular/material/chips';
import {MatIconModule} from '@angular/material/icon';
import {MatFormFieldModule} from '@angular/material/form-field';
import { UpdateBlogComponent } from './blogs/update-blog/update-blog.component';

@NgModule({
  declarations: [
    BlogsComponent,
    AddBlogComponent,
    BlogDetailComponent,
    UpdateBlogComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ComponentsModule,
    MatDialogModule,
    FormsModule,
    MatChipsModule,
    MatIconModule,MatFormFieldModule,
    
    
  ]
})
export class ForumModule { }
