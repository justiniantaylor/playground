import { Action } from '@ngrx/store';

import { OrderItem } from '../models';
import { OrderItemActions } from '../actions';

export type OrderItemState = OrderItem;

const initialState: OrderItemState = {
    id: null,
    code: '',
    description: '',
    unitPriceInCents: null,
    available: null,
    orderId: null
};

export default function (state = initialState, action: Action): OrderItemState {
    switch (action.type) {
        case OrderItemActions.RESET_BLANK_ORDER_ITEM: {
            return initialState;
        }
        case OrderItemActions.GET_ORDER_ITEM_SUCCESS: {
            return action.payload;
        }
        default: {
            return state;
        }
    }
}