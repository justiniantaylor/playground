import { Injectable } from '@angular/core';
import { Effect, Actions } from '@ngrx/effects';
import { of } from 'rxjs/observable/of';
import { Observable } from 'rxjs/Observable'

import { MenuActions,
         NotificationActions } from '../actions';
import { MenuService } from '../services';

import { SlimLoadingBarService } from 'ng2-slim-loading-bar';

@Injectable()
export class MenuEffects {
    constructor ( private _update$: Actions,
                  private _slimLoadingBarService: SlimLoadingBarService,
                  private _notificationActions: NotificationActions,
                  private _menuActions: MenuActions,
                  private _svc: MenuService,
) {}

@Effect() loadMenus$ = this._update$
    .ofType(MenuActions.LOAD_MENUS)
    .switchMap(() => this._svc.getMenus()
        .map(menus => {
            this._slimLoadingBarService.complete();
            return this._menuActions.loadMenusSuccess(menus)
        })  
        .catch((error) => Observable.of( this._notificationActions.showError(error))) 
    );    

@Effect() getMenu$ = this._update$
    .ofType(MenuActions.GET_MENU)
    .map(action => action.payload)
    .switchMap(id => this._svc.getMenu(id)
        .map(menu => {
            this._slimLoadingBarService.complete();
            return this._menuActions.getMenuSuccess(menu)
        })  
        .catch((error) => Observable.of( this._notificationActions.showError(error))) 
    );

@Effect() saveMenu$ = this._update$
    .ofType(MenuActions.SAVE_MENU)
    .map(action => action.payload)
    .switchMap(menu =>  this._svc.saveMenu(menu)
        .map(menu => {
            this._slimLoadingBarService.complete();
            
            let notification: Notification = {
                type: NotificationType.SUCCESS,
                status: 200,
                description: null,
                header: 'Saved',
                message: 'You successfully saved the menu.'
            };
            this._store.dispatch(this._notificationActions.showSuccess(notification));
            
            return this._menuActions.saveMenuSuccess(menu)
        })
        .catch((error) => Observable.of( this._notificationActions.showError(error))) 
    );    

@Effect() addMenu$ = this._update$
    .ofType(MenuActions.ADD_MENU)
    .map(action => action.payload)
    .switchMap(menu => this._svc.saveMenu(menu)
        .map(menu => {
            this._slimLoadingBarService.complete();
            
            let notification: Notification = {
                type: NotificationType.SUCCESS,
                status: 200,
                description: null,
                header: 'Added',
                message: 'You successfully added the menu.'
            };
            this._store.dispatch(this._notificationActions.showSuccess(notification));
            
            return this._menuActions.addMenuSuccess(menu)
        })
        .catch((error) => Observable.of( this._notificationActions.showError(error)))    
    );    

@Effect() deleteMenu$ = this._update$
    .ofType(MenuActions.DELETE_MENU)
    .map(action => action.payload)
    .switchMap(menu => this._svc.deleteMenu(menu)
        .map(menu => {
            this._slimLoadingBarService.complete();
            
            let notification: Notification = {
                type: NotificationType.SUCCESS,
                status: 200,
                description: null,
                header: 'Added',
                message: 'You successfully deleted the menu.'
            };
            this._store.dispatch(this._notificationActions.showSuccess(notification));
            
            return this._menuActions.deleteMenuSuccess(menu)
        })
        .catch((error) => Observable.of( this._notificationActions.showError(error)))    
    );    
}