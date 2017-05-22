import { Action } from '@ngrx/store';

import { MenuItem } from '../models';
import { MenuItemActions } from '../actions';

import * as _ from 'lodash';

export type MenuItemListState = MenuItem[];

const initialState: MenuItemListState = [];

export default function (state = initialState, action: Action): MenuItemListState {
    switch (action.type) {
        case MenuItemActions.LOAD_MENU_ITEMS_SUCCESS: {
            return action.payload;
        }
        case MenuItemActions.ADD_MENU_ITEM_SUCCESS: {
            return [...state, action.payload];
        }
        case MenuItemActions.SAVE_MENU_ITEM_SUCCESS: {
            let index = _.findIndex(state, {id: action.payload.id});
            if (index >= 0) {
                return [
                    ...state.slice(0, index),
                    action.payload,
                    ...state.slice(index + 1)
                ];
            }
            return state;
        }
        case MenuItemActions.DELETE_MENU_ITEM_SUCCESS: {
            return state.filter(menuItem => {
                return menuItem.id !== action.payload.id;
            });
        }
        default: {
            return state;
        }
    }
}