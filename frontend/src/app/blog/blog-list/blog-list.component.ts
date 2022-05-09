import { Component, OnInit } from '@angular/core';
import { Post } from '../../Models/Forum/Post';
import { ForumService } from '../../core/forum.service';
@Component({
  selector: 'app-blog-list',
  templateUrl: './blog-list.component.html',
  styleUrls: ['./blog-list.component.css']
})
export class BlogListComponent implements OnInit {
  listBlog: Post[];
  constructor(private _service:ForumService) { }

  ngOnInit(): void {
    this._service.getAllPostWithImage().subscribe
    (res=>
      {
        this.listBlog=res.filter(function(value, index, arr){ 
          return value.content.length > 200;
      });
        }
        
        
        );
  }

}
