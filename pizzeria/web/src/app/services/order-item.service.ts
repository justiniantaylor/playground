import { Injectable } from '@angular/core';
import { Http,
         Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { OrderItem } from '../models';

const BASE_URL = '/item/';
const HEADER = { headers: new Headers({ 'Content-Type': 'application/json' }) };

@Injectable()
export class OrderItemService {

    constructor (private _http: Http) {}

    getOrderItems(orderId: number): Observable<OrderItem[]> {
        return this._http.get("/resource/order/" + orderId + BASE_URL)
            .map(res => res.json());
    }

    getOrderItem(id): Observable<OrderItem> {
        return this._http.get("/resource/order/" + BASE_URL + id)
            .map(res => res.json());
    }

    saveOrderItem(orderItem: OrderItem) {
        if (orderItem.id === 0 || orderItem.id == null) {
            return this._http.post("/resource/order/" +  orderItem.orderId + BASE_URL, JSON.stringify(orderItem), HEADER)
                .map(res => res.json());
        } else {
            return this._http.put("/resource/order/" +  orderItem.orderId + BASE_URL + orderItem.id, JSON.stringify(orderItem), HEADER)
                .map(res => res.json());
        }
    }

    deleteOrderItem(orderItem: OrderItem) {
        return this._http.delete("/resource/order/" + orderItem.orderId + BASE_URL + orderItem.id)
            .map(res => orderItem);
    }
}