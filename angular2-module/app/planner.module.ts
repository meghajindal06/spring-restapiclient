import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {LocationComponent }   from './location.component';
import { FormsModule } from '@angular/forms';
import {HttpModule} from '@angular/http';
import {DropdownModule} from 'ng2-dropdown'	;

@NgModule({
  imports:      [ BrowserModule , FormsModule, HttpModule ,DropdownModule],
  declarations: [ LocationComponent],
  bootstrap:    [ LocationComponent]
})
export class PlannerModule { }
