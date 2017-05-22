import '@ngrx/core/add/operator/select';
import { compose } from '@ngrx/core/compose';
import { combineReducers } from '@ngrx/store';

import menuListReducer, * as fromMenuList from './menu-list.reducer';
import menuReducer, * as fromMenu from './menu.reducer';

import menuItemListReducer, * as fromMenuItemList from './menu-item-list.reducer';
import menuItemReducer, * as fromMenuItem from './menu-item.reducer';

export interface AppState {
    menus: fromMenuList.MenuListState;
    menu: fromMenu.MenuState;

    menuItems: fromMenuItemList.MenuItemListState;
    menuItem: fromMenuItem.MenuItemState;
};

export default compose(combineReducers)({
    menus: menuListReducer,
    menu: menuReducer,

    menuItems: menuItemListReducer,
    menuItem: menuItemReducer
});