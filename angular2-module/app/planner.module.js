"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var platform_browser_1 = require('@angular/platform-browser');
var location_component_1 = require('./location.component');
var forms_1 = require('@angular/forms');
var http_1 = require('@angular/http');
var ng2_dropdown_1 = require('ng2-dropdown');
var PlannerModule = (function () {
    function PlannerModule() {
    }
    PlannerModule = __decorate([
        core_1.NgModule({
            imports: [platform_browser_1.BrowserModule, forms_1.FormsModule, http_1.HttpModule, ng2_dropdown_1.DropdownModule],
            declarations: [location_component_1.LocationComponent],
            bootstrap: [location_component_1.LocationComponent]
        }), 
        __metadata('design:paramtypes', [])
    ], PlannerModule);
    return PlannerModule;
}());
exports.PlannerModule = PlannerModule;
//# sourceMappingURL=planner.module.js.map