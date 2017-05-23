import { Action } from '@ngrx/store';

import { OrderItem } from '../models';
import { OrderItemActions } from '../actions';

import * as _ from 'lodash';

export type OrderItemListState = OrderItem[];

const initialState: OrderItemListState = [];

export default function (state = initialState, action: Action): OrderItemListState {
    switch (action.type) {
        case OrderItemActions.LOAD_ORDER_ITEMS_SUCCESS: {
            return action.payload;
        }
        case OrderItemActions.ADD_ORDER_ITEM_SUCCESS: {
            return [...state, action.payload];
        }
        case OrderItemActions.SAVE_ORDER_ITEM_SUCCESS: {
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
        case OrderItemActions.DELETE_ORDER_ITEM_SUCCESS: {
            return state.filter(orderItem => {
                return orderItem.id !== action.payload.id;
            });
        }
        default: {
            return state;
        }
    }
}