import { Injectable } from '@angular/core';
import { Http,
         Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { MenuItem } from '../models';

const BASE_URL = '/resource/menu/item/';
const HEADER = { headers: new Headers({ 'Content-Type': 'application/json' }) };

@Injectable()
export class MenuItemService {

    constructor (private _http: Http) {}

    getMenuItems(): Observable<MenuItem[]> {
        return this._http.get(BASE_URL)
            .map(res => res.json());
    }

    getMenuItem(id): Observable<MenuItem> {
        return this._http.get(BASE_URL + id)
            .map(res => res.json());
    }

    saveMenuItem(menuItem: MenuItem) {
        if (menuItem.id === 0 || menuItem.id == null) {
            return this._http.post(BASE_URL, JSON.stringify(menuItem), HEADER)
                .map(res => res.json());
        } else {
            return this._http.put(BASE_URL + menuItem.id, JSON.stringify(menuItem), HEADER)
                .map(res => res.json());
        }
    }

    deleteMenuItem(menuItem: MenuItem) {
        return this._http.delete(BASE_URL + menuItem.id)
            .map(res => menuItem);
    }
}