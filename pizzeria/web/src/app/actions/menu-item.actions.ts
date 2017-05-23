import { Injectable } from '@angular/core';
import { Action } from '@ngrx/store';

@Injectable()
export class MenuItemActions {
    static LOAD_MENU_ITEMS = '[MenuItem] Load Menu Items';
    loadMenuItems(menuId): Action {
        return {
            type: MenuItemActions.LOAD_MENU_ITEMS,
            payload: menuId
        };
    }

    static LOAD_MENU_ITEMS_SUCCESS = '[MenuItem] Load Menu Items Success';
    loadMenuItemsSuccess(menuItems): Action {
        return {
            type: MenuItemActions.LOAD_MENU_ITEMS_SUCCESS,
            payload: menuItems
        };
    }

    static GET_MENU_ITEM = '[MenuItem] Get Menu Item';
    getMenuItem(id): Action {
        return {
            type: MenuItemActions.GET_MENU_ITEM,
            payload: id
        };
    }

    static GET_MENU_ITEM_SUCCESS = '[MenuItem] Get Menu Item Success';
    getMenuItemSuccess(menuItem): Action {
        return {
            type: MenuItemActions.GET_MENU_ITEM_SUCCESS,
            payload: menuItem
        };
    }

    static RESET_BLANK_MENU_ITEM = '[MenuItem] Reset Blank Menu Item';
    resetBlankMenuItem(): Action {
        return {
            type: MenuItemActions.RESET_BLANK_MENU_ITEM
        };
    }

    static SAVE_MENU_ITEM = '[MenuItem] Save Menu Item';
    saveMenuItem(menuItem): Action {
        return {
            type: MenuItemActions.SAVE_MENU_ITEM,
            payload: menuItem
        };
    }

    static SAVE_MENU_ITEM_SUCCESS = '[MenuItem] Save Menu Item Success';
    saveMenuItemSuccess(menuItem): Action {
        return {
            type: MenuItemActions.SAVE_MENU_ITEM_SUCCESS,
            payload: menuItem
        };
    }


    static ADD_MENU_ITEM = '[MenuItem] Add Menu Item';
    addMenuItem(menuItem): Action {
        return {
            type: MenuItemActions.ADD_MENU_ITEM,
            payload: menuItem
        };
    }

    static ADD_MENU_ITEM_SUCCESS = '[MenuItem] Add Menu Item Success';
    addMenuItemSuccess(menuItem): Action {
        return {
            type: MenuItemActions.ADD_MENU_ITEM_SUCCESS,
            payload: menuItem
        };
    }

    static DELETE_MENU_ITEM = '[MenuItem] Delete MenuItem';
    deleteMenuItem(menuItem): Action {
        return {
            type: MenuItemActions.DELETE_MENU_ITEM,
            payload: menuItem
        };
    }

    static DELETE_MENU_ITEM_SUCCESS = '[MenuItem] Delete Menu Item Success';
    deleteMenuItemSuccess(menuItem): Action {
        return {
            type: MenuItemActions.DELETE_MENU_ITEM_SUCCESS,
            payload: menuItem
        };
    }
}