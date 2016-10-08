import { Component , OnInit} from '@angular/core';
import {LocationService} from './location.service'
import {PlanDetail} from './plandetail'
import {Location} from './location'
import {DropdownModule} from "ng2-dropdown";

@Component({
  selector: 'my-app',
  templateUrl: 'template/plan-form.component.html',
  providers: [LocationService  ]
})
export class LocationComponent implements OnInit{ 
 public origin = '';
 public destCode = '';
public locations: Location[] = [];
public plan: PlanDetail;

public countries: string[] = [];
  errorMessage: string = '';
  isLoading: boolean = true;
  public filteredOriginList = [];
  public filteredDestList = [];

  constructor(private locationService : LocationService){ }

  ngOnInit(){
    this.locationService
      .getAll()
      .subscribe(
         /* happy path */ p => this.locations = p,
         /* error path */ e => this.errorMessage = e,
         /* onComplete */ () => this.isLoading = false);

        
  }

  filterDest(r : any ) {
    if (r !== ""){
        this.filteredDestList = this.locations.filter(function(el){
            return el.name.toLowerCase().indexOf(r.toLowerCase()) > -1;
        }.bind(this));
    }else{
        this.filteredDestList = [];
    }
    }

  filter(r : any ) {
    if (r !== ""){
        this.filteredOriginList = this.locations.filter(function(el){
            return el.name.toLowerCase().indexOf(r.toLowerCase()) > -1;
        }.bind(this));
    }else{
        this.filteredOriginList = [];
    }
}
 
select(item , id){
    if(id == 'origin'){
      this.origin = item
      this.filteredOriginList = [];
    }else if(id == 'destCode'){
      this.destCode = item
      this.filteredDestList = [];
    }
    
}

clicked(){

console.log("in clocked")
  let locationOrigin = this.locations.filter(function(el){
            return el.name.toLowerCase().indexOf(this.origin.toLowerCase()) > -1;
        }.bind(this));
console.log(locationOrigin);
  let locationDest = this.locations.filter(function(el){
            return el.name.toLowerCase().indexOf(this.destCode.toLowerCase()) > -1;
        }.bind(this));
console.log(locationDest);
  this.locationService
    .get(locationOrigin[0].code,locationDest[0].code)
    .subscribe(
        p => this.plan = p)
}
  }
