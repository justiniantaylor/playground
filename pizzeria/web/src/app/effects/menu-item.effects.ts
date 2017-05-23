import { Injectable } from '@angular/core';
import { Effect, Actions } from '@ngrx/effects';
import { of } from 'rxjs/observable/of';
import { Observable } from 'rxjs/Observable'

import { MenuItemActions,
         NotificationActions } from '../actions';
import { MenuItemService } from '../services';

@Injectable()
export class MenuItemEffects {
    constructor ( private _update$: Actions,
                  private _notificationActions: NotificationActions,
                  private _menuItemActions: MenuItemActions,
                  private _svc: MenuItemService,
) {}

@Effect() loadMenuItems$ = this._update$
    .ofType(MenuItemActions.LOAD_MENU_ITEMS)
    .map(action => action.payload)
    .switchMap(menuId => this._svc.getMenuItems(menuId)     
        .map(menuItems => this._menuItemActions.loadMenuItemsSuccess(menuItems))   
        .catch((error) => Observable.of( this._notificationActions.showError(error)))
    );

@Effect() getMenuItem$ = this._update$
    .ofType(MenuItemActions.GET_MENU_ITEM)
    .map(action => action.payload)
    .switchMap(id => this._svc.getMenuItem(id)
        .map(menuItem => this._menuItemActions.getMenuItemSuccess(menuItem))
        .catch((error) => Observable.of( this._notificationActions.showError(error)))
    );    

@Effect() saveMenuItem$ = this._update$
    .ofType(MenuItemActions.SAVE_MENU_ITEM)
    .map(action => action.payload)
    .switchMap(menuItem =>  this._svc.saveMenuItem(menuItem)
        .map(menuItem => this._menuItemActions.saveMenuItemSuccess(menuItem))  
        .catch((error) => Observable.of( this._notificationActions.showError(error)))
    );    

@Effect() addMenuItem$ = this._update$
    .ofType(MenuItemActions.ADD_MENU_ITEM)
    .map(action => action.payload)
    .switchMap(menuItem => this._svc.saveMenuItem(menuItem)
        .map(menuItem => this._menuItemActions.addMenuItemSuccess(menuItem))
        .catch((error) => Observable.of( this._notificationActions.showError(error)))   
    )    

@Effect() deleteMenuItem$ = this._update$
    .ofType(MenuItemActions.DELETE_MENU_ITEM)
    .map(action => action.payload)
    .switchMap(menuItem => this._svc.deleteMenuItem(menuItem)
        .map(menuItem => this._menuItemActions.deleteMenuItemSuccess(menuItem))  
        .catch((error) => Observable.of( this._notificationActions.showError(error))) 
    );    
}