import { Injectable } from '@angular/core';
import { Http,
         Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { Order } from '../models';

import { ErrorService } from './error.service';

const BASE_URL = '/resource/order/';
const HEADER = { headers: new Headers({ 'Content-Type': 'application/json' }) };

@Injectable()
export class OrderService {

    constructor (private _http: Http,
                 private _errorService: ErrorService) {}

    getOrders(): Observable<Order[]> {
        return this._http.get(BASE_URL)
            .map(res => res.json())
            .catch(e => this._errorService.handleError(e));
    }

    getOrder(id): Observable<Order> {
        return this._http.get(BASE_URL + id)
            .map(res => res.json())
            .catch(e => this._errorService.handleError(e));
    }

    saveOrder(order: Order) {
        if (order.id === 0 || order.id == null) {
            return this._http.post(BASE_URL, JSON.stringify(order), HEADER)
                .map(res => res.json())
                .catch(e => this._errorService.handleError(e));
        } else {
            return this._http.put(BASE_URL + order.id, JSON.stringify(order), HEADER)
                .map(res => res.json())
                .catch(e => this._errorService.handleError(e));
        }
    }

    deleteOrder(order: Order) {
        return this._http.delete(BASE_URL + order.id)
            .map(res => order)
            .catch(e => this._errorService.handleError(e));
    }
}