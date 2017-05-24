import { Injectable } from '@angular/core';
import { Effect, Actions } from '@ngrx/effects';
import { of } from 'rxjs/observable/of';
import { Observable } from 'rxjs/Observable'

import { OrderActions,
         NotificationActions } from '../actions';
import { OrderService } from '../services';

import { SlimLoadingBarService } from 'ng2-slim-loading-bar';

@Injectable()
export class OrderEffects {
    constructor ( private _update$: Actions,
                  private _slimLoadingBarService: SlimLoadingBarService,
                  private _notificationActions: NotificationActions,
                  private _orderActions: OrderActions,
                  private _svc: OrderService,
) {}

@Effect() loadOrders$ = this._update$
    .ofType(OrderActions.LOAD_ORDERS)
    .switchMap(() => this._svc.getOrders()
        .map(orders => {
            this._slimLoadingBarService.complete();
            return this._orderActions.loadOrdersSuccess(orders)
        })    
    );    

@Effect() getOrder$ = this._update$
    .ofType(OrderActions.GET_ORDER)
    .map(action => action.payload)
    .switchMap(id => this._svc.getOrder(id)
        .map(order => {
            this._slimLoadingBarService.complete();
            return this._orderActions.getOrderSuccess(order)
        })    
    );
    
@Effect() saveOrder$ = this._update$
    .ofType(OrderActions.SAVE_ORDER)
    .map(action => action.payload)
    .switchMap(order =>  this._svc.saveOrder(order)
        .map(order => {
            this._slimLoadingBarService.complete();
            
            let notification: Notification = {
                type: NotificationType.SUCCESS,
                status: 200,
                description: null,
                header: 'Saved',
                message: 'You successfully saved the order.'
            };
            this._store.dispatch(this._notificationActions.showSuccess(notification));
            
            return this._orderActions.saveOrderSuccess(order)
        })    
    );    

@Effect() addOrder$ = this._update$
    .ofType(OrderActions.ADD_ORDER)
    .map(action => action.payload)
    .switchMap(order => this._svc.saveOrder(order)
        .map(order => {
            this._slimLoadingBarService.complete();
            
            let notification: Notification = {
                type: NotificationType.SUCCESS,
                status: 200,
                description: null,
                header: 'Added',
                message: 'You successfully added the order.'
            };
            this._store.dispatch(this._notificationActions.showSuccess(notification));
            
            return this._orderActions.addOrderSuccess(order)
        })    
    );    

@Effect() deleteOrder$ = this._update$
    .ofType(OrderActions.DELETE_ORDER)
    .map(action => action.payload)
    .switchMap(order => this._svc.deleteOrder(order)
        .map(order => {
            this._slimLoadingBarService.complete();
            
            let notification: Notification = {
                type: NotificationType.SUCCESS,
                status: 200,
                description: null,
                header: 'Added',
                message: 'You successfully deleted the order.'
            };
            this._store.dispatch(this._notificationActions.showSuccess(notification));
            
            return this._orderActions.deleteOrderSuccess(order)
        })    
    );
}