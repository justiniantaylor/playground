import { Injectable } from '@angular/core';
import { Http,
         Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { Menu } from '../models';

import { ErrorService } from './error.service';

const BASE_URL = '/resource/menu/';
const HEADER = { headers: new Headers({ 'Content-Type': 'application/json' }) };

@Injectable()
export class MenuService {

    constructor (private _http: Http,
                 private _errorService: ErrorService) {}

    getMenus(): Observable<Menu[]> {
        return this._http.get(BASE_URL)
            .map(res => res.json())
            .catch(e => this._errorService.handleError(e));
    }

    getMenu(id): Observable<Menu> {
        return this._http.get(BASE_URL + id)
            .map(res => res.json())
            .catch(e => this._errorService.handleError(e));
    }

    saveMenu(menu: Menu) {
        if (menu.id === 0 || menu.id == null) {
            return this._http.post(BASE_URL, JSON.stringify(menu), HEADER)
                .map(res => res.json())
                .catch(e => this._errorService.handleError(e));
        } else {
            return this._http.put(BASE_URL + menu.id, JSON.stringify(menu), HEADER)
                .map(res => res.json())
                .catch(e => this._errorService.handleError(e));
        }
    }

    deleteMenu(menu: Menu) {
        return this._http.delete(BASE_URL + menu.id)
            .map(res => menu)        
            .catch(e => this._errorService.handleError(e));
    }
}