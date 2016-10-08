import { Injectable } from '@angular/core';
import { Http, Response , Headers} from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Location } from './location';
import {PlanDetail} from './plandetail'

@Injectable()
export class LocationService{
  private baseUrl: string = 'http://localhost:8090';

  constructor(private http : Http){
  }

  getAll(): Observable<Location[]>{
    let locations$ = this.http
      .get(`${this.baseUrl}/locations`,{headers: new Headers()})
      .map(function(response){ return response.json(); })
      .catch(handleError);
      return locations$;
  }

  get(origin: string , dest: string): Observable<PlanDetail> {
    let plan$ = this.http
      .get(`${this.baseUrl}/selectionDetail?origin=${origin}&dest=${dest}`, {headers: this.getHeaders()})
      .map(function(response){return response.json();});
      return plan$;
  }

  private getHeaders(){
    let headers = new Headers();
    headers.append('Accept', 'application/json');
    return headers;
  }
  
}







// this could also be a private method of the component class
function handleError (error: any) {
  // log error
  // could be something more sofisticated
  let errorMsg = error.message || `Yikes! There was was a problem with our hyperdrive device and we couldn't retrieve your data!`
  console.error(errorMsg);

  // throw an application level error
  return Observable.throw(errorMsg);
}