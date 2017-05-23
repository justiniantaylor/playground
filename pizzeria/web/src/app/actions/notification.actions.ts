import { Injectable } from '@angular/core';
import { Action } from '@ngrx/store';

@Injectable()
export class NotificationActions {
    static SHOW_ERROR = '[Notification] Show Error';
    showError(notification): Action {
        return {
            type: NotificationActions.SHOW_ERROR,
            payload: notification
        };
    }

    static SHOW_WARNING = '[Notification] Show Warning';
    showWarning(notification): Action {
        return {
            type: NotificationActions.SHOW_WARNING,
            payload: notification
        };
    }

    static SHOW_INFO = '[Notification] Show Info';
    showInfo(notification): Action {
        return {
            type: NotificationActions.SHOW_INFO,
            payload: notification
        };
    }

    static SHOW_SUCCESS = '[Notification] Show Success';
    showSuccess(notification): Action {
        return {
            type: NotificationActions.SHOW_SUCCESS,
            payload: notification
        };
    }
}