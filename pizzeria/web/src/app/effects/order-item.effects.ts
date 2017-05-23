import { Injectable } from '@angular/core';
import { Effect, Actions } from '@ngrx/effects';
import { of } from 'rxjs/observable/of';
import { Observable } from 'rxjs/Observable'

import { OrderItemActions,
         NotificationActions } from '../actions';
import { OrderItemService } from '../services';

@Injectable()
export class OrderItemEffects {
    constructor ( private _update$: Actions,
                  private _notificationActions: NotificationActions,
                  private _orderItemActions: OrderItemActions,
                  private _svc: OrderItemService,
) {}

@Effect() loadOrderItems$ = this._update$
    .ofType(OrderItemActions.LOAD_ORDER_ITEMS)
    .switchMap(orderId => this._svc.getOrderItems(orderId)
        .map(orderItems => this._orderItemActions.loadOrderItemsSuccess(orderItems)) 
        .catch((error) => Observable.of( this._notificationActions.showError(error)))  
    );    

@Effect() getOrderItem$ = this._update$
    .ofType(OrderItemActions.GET_ORDER_ITEM)
    .map(action => action.payload)
    .switchMap(id => this._svc.getOrderItem(id)
        .map(orderItem => this._orderItemActions.getOrderItemSuccess(orderItem))
        .catch((error) => Observable.of( this._notificationActions.showError(error)))    
    );    

@Effect() saveOrderItem$ = this._update$
    .ofType(OrderItemActions.SAVE_ORDER_ITEM)
    .map(action => action.payload)
    .switchMap(orderItem =>  this._svc.saveOrderItem(orderItem)
        .map(orderItem => this._orderItemActions.saveOrderItemSuccess(orderItem))  
        .catch((error) => Observable.of( this._notificationActions.showError(error)))  
    );    

@Effect() addOrderItem$ = this._update$
    .ofType(OrderItemActions.ADD_ORDER_ITEM)
    .map(action => action.payload)
    .switchMap(orderItem => this._svc.saveOrderItem(orderItem)
        .map(orderItem => this._orderItemActions.addOrderItemSuccess(orderItem))    
        .catch((error) => Observable.of( this._notificationActions.showError(error)))
    );

@Effect() deleteOrderItem$ = this._update$
    .ofType(OrderItemActions.DELETE_ORDER_ITEM)
    .map(action => action.payload)
    .switchMap(orderItem => this._svc.deleteOrderItem(orderItem)
        .map(orderItem => this._orderItemActions.deleteOrderItemSuccess(orderItem))   
        .catch((error) => Observable.of( this._notificationActions.showError(error))) 
    );    
}