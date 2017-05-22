import { Injectable } from '@angular/core';
import { Effect, Actions } from '@ngrx/effects';
import { of } from 'rxjs/observable/of';

import { MenuActions } from '../actions';
import { MenuService } from '../services';

@Injectable()
export class MenuEffects {
    constructor ( private _update$: Actions,
                  private _menuActions: MenuActions,
                  private _svc: MenuService,
) {}

@Effect() loadMenus$ = this._update$
    .ofType(MenuActions.LOAD_MENUS)
    .switchMap(() => this._svc.getMenus())
    .map(menus => this._menuActions.loadMenusSuccess(menus));

@Effect() getMenu$ = this._update$
    .ofType(MenuActions.GET_MENU)
    .map(action => action.payload)
    .switchMap(id => this._svc.getMenu(id))
    .map(menu => this._menuActions.getMenuSuccess(menu))
    .catch(error => of(this._menuActions.getMenuFailure(error)));

@Effect() saveMenu$ = this._update$
    .ofType(MenuActions.SAVE_MENU)
    .map(action => action.payload)
    .switchMap(menu =>  this._svc.saveMenu(menu))
    .map(menu => this._menuActions.saveMenuSuccess(menu));

@Effect() addMenu$ = this._update$
    .ofType(MenuActions.ADD_MENU)
    .map(action => action.payload)
    .switchMap(menu => this._svc.saveMenu(menu))
    .map(menu => this._menuActions.addMenuSuccess(menu));

@Effect() deleteMenu$ = this._update$
    .ofType(MenuActions.DELETE_MENU)
    .map(action => action.payload)
    .switchMap(menu => this._svc.deleteMenu(menu))
    .map(menu => this._menuActions.deleteMenuSuccess(menu));
}