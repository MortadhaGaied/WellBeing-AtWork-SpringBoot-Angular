import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ForumService } from '../../core/forum.service';
import { Post } from '../../Models/Forum/Post';

@Component({
  selector: 'app-blog-detail',
  templateUrl: './blog-detail.component.html',
  styleUrls: ['./blog-detail.component.css']
})
export class BlogDetailComponent implements OnInit {
  idBlog:number;
  blog:Post=new Post();
  constructor(private route:ActivatedRoute,private _service:ForumService) { }

  ngOnInit(): void {
    this.idBlog=this.route.snapshot.params['id'];
    this._service.getBlogById(this.idBlog).subscribe(b=>{
      this.blog=b;
      console.log("detail: "+this.blog);
    });
  }
  exportPdf(){
    this._service.downloadpdf(this.idBlog).subscribe(x=>{
      const blob=new Blob([x],{type:'application/pdf'});
      if(window.navigator && (window.navigator as any).msSaveOrOpenBlob){
        (window.navigator as any).msSaveOrOpenBlob(blob);
        return;
      }
      const data =window.URL.createObjectURL(blob);
      const link =document.createElement('a');
      link.href=data;
      link.download='blog.pdf';
      link.dispatchEvent(new MouseEvent('click',{bubbles:true,cancelable:true,view:window}));
      setTimeout(function(){
        window.URL.revokeObjectURL(data);
        link.remove();
      },100);
    });
  }
}
