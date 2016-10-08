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
var location_service_1 = require('./location.service');
var LocationComponent = (function () {
    function LocationComponent(locationService) {
        this.locationService = locationService;
        this.origin = '';
        this.destCode = '';
        this.locations = [];
        this.countries = [];
        this.errorMessage = '';
        this.isLoading = true;
        this.filteredOriginList = [];
        this.filteredDestList = [];
    }
    LocationComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.locationService
            .getAll()
            .subscribe(
        /* happy path */ function (p) { return _this.locations = p; }, 
        /* error path */ function (e) { return _this.errorMessage = e; }, 
        /* onComplete */ function () { return _this.isLoading = false; });
    };
    LocationComponent.prototype.filterDest = function (r) {
        if (r !== "") {
            this.filteredDestList = this.locations.filter(function (el) {
                return el.name.toLowerCase().indexOf(r.toLowerCase()) > -1;
            }.bind(this));
        }
        else {
            this.filteredDestList = [];
        }
    };
    LocationComponent.prototype.filter = function (r) {
        if (r !== "") {
            this.filteredOriginList = this.locations.filter(function (el) {
                return el.name.toLowerCase().indexOf(r.toLowerCase()) > -1;
            }.bind(this));
        }
        else {
            this.filteredOriginList = [];
        }
    };
    LocationComponent.prototype.select = function (item, id) {
        if (id == 'origin') {
            this.origin = item;
            this.filteredOriginList = [];
        }
        else if (id == 'destCode') {
            this.destCode = item;
            this.filteredDestList = [];
        }
    };
    LocationComponent.prototype.clicked = function () {
        var _this = this;
        console.log("in clocked");
        var locationOrigin = this.locations.filter(function (el) {
            return el.name.toLowerCase().indexOf(this.origin.toLowerCase()) > -1;
        }.bind(this));
        console.log(locationOrigin);
        var locationDest = this.locations.filter(function (el) {
            return el.name.toLowerCase().indexOf(this.destCode.toLowerCase()) > -1;
        }.bind(this));
        console.log(locationDest);
        this.locationService
            .get(locationOrigin[0].code, locationDest[0].code)
            .subscribe(function (p) { return _this.plan = p; });
    };
    LocationComponent = __decorate([
        core_1.Component({
            selector: 'my-app',
            templateUrl: 'template/plan-form.component.html',
            providers: [location_service_1.LocationService]
        }), 
        __metadata('design:paramtypes', [location_service_1.LocationService])
    ], LocationComponent);
    return LocationComponent;
}());
exports.LocationComponent = LocationComponent;
//# sourceMappingURL=location.component.js.map