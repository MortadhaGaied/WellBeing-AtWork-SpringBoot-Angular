import { Component, OnInit,Inject } from '@angular/core';
import { FileUpload } from '../../Models/file-upload.model';
import { Post } from '../../Models/Forum/Post';
import { ImageService } from '../../image.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ForumService } from '../forum.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-update-post',
  templateUrl: './update-post.component.html',
  styleUrls: ['./update-post.component.css']
})
export class UpdatePostComponent implements OnInit {
  idPost:number;
post:Post=new Post();
selectedFiles?: FileList;
currentFileUpload: FileUpload;
percentage = 0;
  constructor(@Inject(ForumService) private ev:ForumService,
  private uploadService: ImageService, private _router:Router,
  private route:ActivatedRoute,private dialogRef:MatDialogRef<UpdatePostComponent>,@Inject(MAT_DIALOG_DATA) public data:any) { }

  ngOnInit(): void {

    this.idPost=this.route.snapshot.params['id'];
    this.ev.getBlogById(this.data.idpost).subscribe(p=>this.post=p);
  }
  updatePost(){
    if(this.currentFileUpload!=undefined){
      this.post.file=this.currentFileUpload.url;
    }
    
    this.ev.updateBlog(this.post).subscribe(p=>{
      console.log(p);
      this.dialogRef.close();
      this._router.navigateByUrl("/").then(()=>window.location.reload());
    });
    

  }
  selectFile(event: any): void {
    this.selectedFiles = event.target.files;
   
    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);
      this.selectedFiles = undefined;
      if (file) {
        this.currentFileUpload = new FileUpload(file);
        this.uploadService.pushFileToStorage(this.currentFileUpload).subscribe(
          percentage => {
            this.percentage = Math.round(percentage ? percentage : 0);
            
          },
          error => {
            console.log(error);
          }
          
        );
      }
    }
 
  }
}
