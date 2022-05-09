
import { HttpClient } from'@angular/common/http';
import { Injectable,EventEmitter } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../../Models/Forum/Post';
import { take } from 'rxjs/operators';
import { saveAs } from 'file-saver';
@Injectable({
  providedIn: 'root'
})
export class BlogsServiceService {

  blog:Post;
  constructor(private _http:HttpClient) { }
  $eventEmit = new EventEmitter();
  getAllPost():Observable<Post[]>{
    return this._http.get<Post[]>("http://localhost:8081/Wellbeignatwork/Post/all-post")
  }
  deleteBlogById(id:number){
    console.log("delete"+id);
    return this._http.delete("http://localhost:8081/Wellbeignatwork/Post/delete-post/"+id);
  }
  getPostReactions(id:number){
    return this._http.get<number>("http://localhost:8081/Wellbeignatwork/React/getNbrReactionByPost/"+id);
  }
  updateBlog(blog:Post ){
    return this._http.put<Post>("http://localhost:8081/Wellbeignatwork/Post/update-post",blog);
  }
  addBlog(blog:Post){
    return this._http.post<Post>("http://localhost:8081/Wellbeignatwork/Post/add-post/1",blog);
}
  getBlogById(idBlog: number): Observable<Post> {
    return this._http.get<Post>("http://localhost:8081/Wellbeignatwork/Post/getPostById/"+idBlog);
   }
  downloadpdf(id:number):Observable<Blob>{
    
    return this._http.get("http://localhost:8081/Wellbeignatwork/Post/downloadArticle/"+id,{responseType:'blob'});
  }
    sendEventData(idBlog : number):any{
      
      this.getBlogById(idBlog).pipe(take(1)).subscribe(x=>{
        console.log(x.content);
        this.blog=x;
        this.$eventEmit.emit(this.blog);
        return x;
      });
      
    }
    getBlogByTag(tag: string): Observable<Post[]> {
      return this._http.get<Post[]>("http://localhost:8081/Wellbeignatwork/Post/getPostByTag/"+tag);
     }
     unreportPost(idPost:number){
      return this._http.get("http://localhost:8081/Wellbeignatwork/Post/unreportPost/"+idPost);
    }
    getPostsInteraction():Observable<number[]>{
      return this._http.get<number[]>("http://localhost:8081/Wellbeignatwork/Post/postInteraction");
    }
    getpostsatisfaction(id:number):Observable<number>{
      return this._http.get<number>("http://localhost:8081/Wellbeignatwork/Comment/PostSatisfaction/"+id);
    }
}
