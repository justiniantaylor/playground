import { Action } from '@ngrx/store';

import { MenuItem } from '../models';
import { MenuItemActions } from '../actions';

export type MenuItemState = MenuItem;

const initialState: MenuItemState = {
    id: null,
    code: '',
    description: '',
    unitPriceInCents: null,
    available: null,
    menuId: null
};

export default function (state = initialState, action: Action): MenuItemState {
    switch (action.type) {
        case MenuItemActions.RESET_BLANK_MENU_ITEM: {
            return initialState;
        }
        case MenuItemActions.GET_MENU_ITEM_SUCCESS: {
            return action.payload;
        }
        default: {
            return state;
        }
    }
}