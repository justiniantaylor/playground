import { Injectable } from '@angular/core';
import { Action } from '@ngrx/store';

@Injectable()
export class OrderActions {
    static LOAD_ORDERS = '[Order] Load Orders';
    loadOrders(): Action {
        return {
            type: OrderActions.LOAD_ORDERS,
        };
    }

    static LOAD_ORDERS_SUCCESS = '[Order] Load Orders Success';
    loadOrdersSuccess(orders): Action {
        return {
            type: OrderActions.LOAD_ORDERS_SUCCESS,
            payload: orders
        };
    }

    static GET_ORDER = '[Order] Get Order';
    getOrder(id): Action {
        return {
            type: OrderActions.GET_ORDER,
            payload: id
        };
    }

    static GET_ORDER_SUCCESS = '[Order] Get Order Success';
    getOrderSuccess(order): Action {
        return {
            type: OrderActions.GET_ORDER_SUCCESS,
            payload: order
        };
    }

    static RESET_BLANK_ORDER = '[Order] Reset Blank Order';
    resetBlankOrder(): Action {
        return {
            type: OrderActions.RESET_BLANK_ORDER
        };
    }

    static SAVE_ORDER = '[Order] Save Order';
    saveOrder(order): Action {
        return {
            type: OrderActions.SAVE_ORDER,
            payload: order
        };
    }

    static SAVE_ORDER_SUCCESS = '[Order] Save Order Success';
    saveOrderSuccess(order): Action {
        return {
            type: OrderActions.SAVE_ORDER_SUCCESS,
            payload: order
        };
    }


    static ADD_ORDER = '[Order] Add Order';
    addOrder(order): Action {
        return {
            type: OrderActions.ADD_ORDER,
            payload: order
        };
    }

    static ADD_ORDER_SUCCESS = '[Order] Add Order Success';
    addOrderSuccess(order): Action {
        return {
            type: OrderActions.ADD_ORDER_SUCCESS,
            payload: order
        };
    }

    static DELETE_ORDER = '[Order] Delete Order';
    deleteOrder(order): Action {
        return {
            type: OrderActions.DELETE_ORDER,
            payload: order
        };
    }

    static DELETE_ORDER_SUCCESS = '[Order] Delete Order Success';
    deleteOrderSuccess(order): Action {
        return {
            type: OrderActions.DELETE_ORDER_SUCCESS,
            payload: order
        };
    }
}