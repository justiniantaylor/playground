import { Injectable } from '@angular/core';
import { Http,
         Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { Menu } from '../models';

const BASE_URL = '/resource/menu/';
const HEADER = { headers: new Headers({ 'Content-Type': 'application/json' }) };

@Injectable()
export class MenuService {

    constructor (private _http: Http) {}

    getMenus(): Observable<Menu[]> {
        return this._http.get(BASE_URL)
            .map(res => res.json());
    }

    getMenu(id): Observable<Menu> {
        return this._http.get(BASE_URL + id)
            .map(res => res.json());
    }

    saveMenu(menu: Menu) {
        if (menu.id === 0 || menu.id == null) {
            return this._http.post(BASE_URL, JSON.stringify(menu), HEADER)
                .map(res => res.json());
        } else {
            return this._http.put(BASE_URL + menu.id, JSON.stringify(menu), HEADER)
                .map(res => res.json());
        }
    }

    deleteMenu(menu: Menu) {
        return this._http.delete(BASE_URL + menu.id)
            .map(res => menu);
    }
}