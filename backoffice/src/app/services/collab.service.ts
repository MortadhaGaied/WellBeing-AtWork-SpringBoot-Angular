import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CollabType } from './collab-type';

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

@Injectable({
  providedIn: 'root'
})
export class CollabService {

  constructor(private http: HttpClient) {}

  getAllCollab(): Observable<CollabType[]> {
    return this.http.get<CollabType[]>('http://localhost:8081/Wellbeignatwork/Collaboration/retrieveAllCollaborations', httpOptions);
  }

  getCollab(collab: CollabType): Observable<CollabType> {
    return this.http.get<CollabType>(`http://localhost:8081/Wellbeignatwork/Collaboration/retrieveCollaboration/${collab.idCollaboration}`, httpOptions);
  }

  addCollab(collab: CollabType): Observable<CollabType> {
    return this.http.post<CollabType>('http://localhost:8081/Wellbeignatwork/Collaboration/addCollaboration', collab, httpOptions);
  }

  updateCollab(collab: CollabType): Observable<CollabType> {
    return this.http.put<CollabType>(`http://localhost:8081/Wellbeignatwork/Collaboration/UpdateCollaboration/${collab.idCollaboration}`, httpOptions);
  }

  deleteCollab(collab: CollabType): Observable<boolean> {
    return this.http.delete<boolean>(`http://localhost:8081/Wellbeignatwork/Collaboration/deleteCollaboration/${collab.idCollaboration}`, httpOptions);
  }

}
