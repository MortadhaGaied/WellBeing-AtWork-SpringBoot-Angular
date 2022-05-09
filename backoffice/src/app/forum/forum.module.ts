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
import { BlogsStatsComponent } from './blogs/blogs-stats/blogs-stats.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import { ReportedPostComponent } from './reported-post/reported-post.component';
import {ChartsModule} from 'ng2-charts'
@NgModule({
  declarations: [
    BlogsComponent,
    AddBlogComponent,
    BlogDetailComponent,
    UpdateBlogComponent,
    BlogsStatsComponent,
    ReportedPostComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ComponentsModule,
    MatDialogModule,
    FormsModule,
    MatChipsModule,
    MatIconModule,MatFormFieldModule,
    MatPaginatorModule,
    ChartsModule
    
    
  ]
})
export class ForumModule { }
