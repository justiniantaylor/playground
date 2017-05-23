import { Action } from '@ngrx/store';

import { Order } from '../models';
import { OrderActions } from '../actions';

export type OrderState = Order;

const initialState: OrderState = {
    id: null,
    startDate: '',
    endDate: ''
};

export default function (state = initialState, action: Action): OrderState {
    switch (action.type) {
        case OrderActions.RESET_BLANK_ORDER: {
            return initialState;
        }
        case OrderActions.GET_ORDER_SUCCESS: {
            return action.payload;
        }
        default: {
            return state;
        }
    }
}