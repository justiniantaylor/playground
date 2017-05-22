import { Action } from '@ngrx/store';

import { Menu } from '../models';
import { MenuActions } from '../actions';

export type MenuState = Menu;

const initialState: MenuState = {
    id: null,
    startDate: '',
    endDate: ''
};

export default function (state = initialState, action: Action): MenuState {
    switch (action.type) {
        case MenuActions.RESET_BLANK_MENU: {
            return initialState;
        }
        case MenuActions.GET_MENU_SUCCESS: {
            return action.payload;
        }
        case MenuActions.GET_MENU_FAILURE: {
            return action.payload;
        }
        default: {
            return state;
        }
    }
}