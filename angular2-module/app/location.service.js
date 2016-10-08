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
var http_1 = require('@angular/http');
var Rx_1 = require('rxjs/Rx');
var LocationService = (function () {
    function LocationService(http) {
        this.http = http;
        this.baseUrl = 'http://localhost:8090';
    }
    LocationService.prototype.getAll = function () {
        var locations$ = this.http
            .get(this.baseUrl + "/locations", { headers: new http_1.Headers() })
            .map(function (response) { return response.json(); })
            .catch(handleError);
        return locations$;
    };
    LocationService.prototype.get = function (origin, dest) {
        var plan$ = this.http
            .get(this.baseUrl + "/selectionDetail?origin=" + origin + "&dest=" + dest, { headers: this.getHeaders() })
            .map(function (response) { return response.json(); });
        return plan$;
    };
    LocationService.prototype.getHeaders = function () {
        var headers = new http_1.Headers();
        headers.append('Accept', 'application/json');
        return headers;
    };
    LocationService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], LocationService);
    return LocationService;
}());
exports.LocationService = LocationService;
// this could also be a private method of the component class
function handleError(error) {
    // log error
    // could be something more sofisticated
    var errorMsg = error.message || "Yikes! There was was a problem with our hyperdrive device and we couldn't retrieve your data!";
    console.error(errorMsg);
    // throw an application level error
    return Rx_1.Observable.throw(errorMsg);
}
//# sourceMappingURL=location.service.js.map