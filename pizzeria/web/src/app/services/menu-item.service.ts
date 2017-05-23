import { Injectable } from '@angular/core';
import { Http,
         Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { MenuItem } from '../models';

import { ErrorService } from './error.service';

const BASE_URL = '/item/';
const HEADER = { headers: new Headers({ 'Content-Type': 'application/json' }) };

@Injectable()
export class MenuItemService {

    constructor (private _http: Http,
                 private _errorService: ErrorService) {}

    getMenuItems(menuId: number): Observable<MenuItem[]> {
        return this._http.get("/resource/menu/" + menuId + BASE_URL)
            .map(res => res.json())
            .catch(e => this._errorService.handleError(e));
    }

    getMenuItem(id): Observable<MenuItem> {
        return this._http.get("/resource/menu/" + BASE_URL + id)
            .map(res => res.json())
            .catch(e => this._errorService.handleError(e));
    }

    saveMenuItem(menuItem: MenuItem) {
        if (menuItem.id === 0 || menuItem.id == null) {
            return this._http.post("/resource/menu/" +  menuItem.menuId + BASE_URL, JSON.stringify(menuItem), HEADER)
                .map(res => res.json())
                .catch(e => this._errorService.handleError(e));
        } else {
            return this._http.put("/resource/menu/" +  menuItem.menuId + BASE_URL + menuItem.id, JSON.stringify(menuItem), HEADER)
                .map(res => res.json())
                .catch(e => this._errorService.handleError(e));
        }
    }

    deleteMenuItem(menuItem: MenuItem) {
        return this._http.delete("/resource/menu/" + menuItem.menuId + BASE_URL + menuItem.id)
            .map(res => menuItem)
            .catch(e => this._errorService.handleError(e));
    }
}