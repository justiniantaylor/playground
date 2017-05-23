import { Injectable } from '@angular/core';
import { Action } from '@ngrx/store';

@Injectable()
export class MenuActions {
    static LOAD_MENUS = '[Menu] Load Menus';
    loadMenus(): Action {
        return {
            type: MenuActions.LOAD_MENUS
        };
    }

    static LOAD_MENUS_SUCCESS = '[Menu] Load Menus Success';
    loadMenusSuccess(menus): Action {
        return {
            type: MenuActions.LOAD_MENUS_SUCCESS,
            payload: menus
        };
    }

    static GET_MENU = '[Menu] Get Menu';
    getMenu(id): Action {
        return {
            type: MenuActions.GET_MENU,
            payload: id
        };
    }

    static GET_MENU_SUCCESS = '[Menu] Get Menu Success';
    getMenuSuccess(menu): Action {
        return {
            type: MenuActions.GET_MENU_SUCCESS,
            payload: menu
        };
    }

    static RESET_BLANK_MENU = '[Menu] Reset Blank Menu';
    resetBlankMenu(): Action {
        return {
            type: MenuActions.RESET_BLANK_MENU
        };
    }

    static SAVE_MENU = '[Menu] Save Menu';
    saveMenu(menu): Action {
        return {
            type: MenuActions.SAVE_MENU,
            payload: menu
        };
    }

    static SAVE_MENU_SUCCESS = '[Menu] Save Menu Success';
    saveMenuSuccess(menu): Action {
        return {
            type: MenuActions.SAVE_MENU_SUCCESS,
            payload: menu
        };
    }


    static ADD_MENU = '[Menu] Add Menu';
    addMenu(menu): Action {
        return {
            type: MenuActions.ADD_MENU,
            payload: menu
        };
    }

    static ADD_MENU_SUCCESS = '[Menu] Add Menu Success';
    addMenuSuccess(menu): Action {
        return {
            type: MenuActions.ADD_MENU_SUCCESS,
            payload: menu
        };
    }

    static DELETE_MENU = '[Menu] Delete Menu';
    deleteMenu(menu): Action {
        return {
            type: MenuActions.DELETE_MENU,
            payload: menu
        };
    }

    static DELETE_MENU_SUCCESS = '[Menu] Delete Menu Success';
    deleteMenuSuccess(menu): Action {
        return {
            type: MenuActions.DELETE_MENU_SUCCESS,
            payload: menu
        };
    }
}