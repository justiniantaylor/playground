import { Action } from '@ngrx/store';

import { Order } from '../models';
import { OrderActions } from '../actions';

import * as _ from 'lodash';

export type OrderListState = Order[];

const initialState: OrderListState = [];

export default function (state = initialState, action: Action): OrderListState {
    switch (action.type) {
        case OrderActions.LOAD_ORDERS_SUCCESS: {
            return action.payload;
        }
        case OrderActions.ADD_ORDER_SUCCESS: {
            return [...state, action.payload];
        }
        case OrderActions.SAVE_ORDER_SUCCESS: {
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
        case OrderActions.DELETE_ORDER_SUCCESS: {
            return state.filter(menu => {
                return menu.id !== action.payload.id;
            });
        }
        default: {
            return state;
        }
    }
}