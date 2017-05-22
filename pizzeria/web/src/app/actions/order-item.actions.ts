import { Injectable } from '@angular/core';
import { Action } from '@ngrx/store';

@Injectable()
export class OrderItemActions {
    static LOAD_ORDER_ITEMS = '[OrderItem] Load Order Items';
    loadOrderItems(orderId): Action {
        return {
            type: OrderItemActions.LOAD_ORDER_ITEMS,
            payload: orderId
        };
    }

    static LOAD_ORDER_ITEMS_SUCCESS = '[OrderItem] Load Order Items Success';
    loadOrderItemsSuccess(orderItems): Action {
        return {
            type: OrderItemActions.LOAD_ORDER_ITEMS_SUCCESS,
            payload: orderItems
        };
    }

    static GET_ORDER_ITEM = '[OrderItem] Get Order Item';
    getOrderItem(id): Action {
        return {
            type: OrderItemActions.GET_ORDER_ITEM,
            payload: id
        };
    }

    static GET_ORDER_ITEM_SUCCESS = '[OrderItem] Get Order Item Success';
    getOrderItemSuccess(orderItem): Action {
        return {
            type: OrderItemActions.GET_ORDER_ITEM_SUCCESS,
            payload: orderItem
        };
    }

    static GET_ORDER_ITEM_FAILURE = '[OrderItem] Get Order Item Failure';
    getOrderItemFailure(string): Action {
        return {
            type: OrderItemActions.GET_ORDER_ITEM_FAILURE,
            payload: string
        };
    }

    static RESET_BLANK_ORDER_ITEM = '[OrderItem] Reset Blank Order Item';
    resetBlankOrderItem(): Action {
        return {
            type: OrderItemActions.RESET_BLANK_ORDER_ITEM
        };
    }

    static SAVE_ORDER_ITEM = '[OrderItem] Save Order Item';
    saveOrderItem(orderItem): Action {
        return {
            type: OrderItemActions.SAVE_ORDER_ITEM,
            payload: orderItem
        };
    }

    static SAVE_ORDER_ITEM_SUCCESS = '[OrderItem] Save Order Item Success';
    saveOrderItemSuccess(orderItem): Action {
        return {
            type: OrderItemActions.SAVE_ORDER_ITEM_SUCCESS,
            payload: orderItem
        };
    }


    static ADD_ORDER_ITEM = '[OrderItem] Add Order Item';
    addOrderItem(orderItem): Action {
        return {
            type: OrderItemActions.ADD_ORDER_ITEM,
            payload: orderItem
        };
    }

    static ADD_ORDER_ITEM_SUCCESS = '[OrderItem] Add Order Item Success';
    addOrderItemSuccess(orderItem): Action {
        return {
            type: OrderItemActions.ADD_ORDER_ITEM_SUCCESS,
            payload: orderItem
        };
    }

    static DELETE_ORDER_ITEM = '[OrderItem] Delete OrderItem';
    deleteOrderItem(orderItem): Action {
        return {
            type: OrderItemActions.DELETE_ORDER_ITEM,
            payload: orderItem
        };
    }

    static DELETE_ORDER_ITEM_SUCCESS = '[OrderItem] Delete Order Item Success';
    deleteOrderItemSuccess(orderItem): Action {
        return {
            type: OrderItemActions.DELETE_ORDER_ITEM_SUCCESS,
            payload: orderItem
        };
    }
}