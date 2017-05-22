import { Action } from '@ngrx/store';

import { Menu } from '../models';
import { MenuActions } from '../actions';

import * as _ from 'lodash';

export type MenuListState = Menu[];

const initialState: MenuListState = [];

export default function (state = initialState, action: Action): MenuListState {
    switch (action.type) {
        case MenuActions.LOAD_MENUS_SUCCESS: {
            return action.payload;
        }
        case MenuActions.ADD_MENU_SUCCESS: {
            return [...state, action.payload];
        }
        case MenuActions.SAVE_MENU_SUCCESS: {
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
        case MenuActions.DELETE_MENU_SUCCESS: {
            return state.filter(menu => {
                return menu.id !== action.payload.id;
            });
        }
        default: {
            return state;
        }
    }
}