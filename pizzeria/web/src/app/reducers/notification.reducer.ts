import { Action } from '@ngrx/store';

import { Notification,
         NotificationType } from '../models';
import { NotificationActions } from '../actions';

export type NotificationState = Notification;

const initialState: NotificationState = {
    status: null,
    description: '',
    type: NotificationType,
    header: '',
    message: ''
};

export default function (state = initialState, action: Action): NotificationState {
    switch (action.type) {
        case NotificationActions.SHOW_ERROR: {
            action.payload.type = NotificationType.ERROR;
            if(action.payload.header == null || action.payload.header == "") {
                action.payload.header = "Error";
            }
            return action.payload;
        }
        case NotificationActions.SHOW_WARNING: {
            action.payload.type = NotificationType.WARNING;
            if(action.payload.header == null || action.payload.header == "") {
                action.payload.header = "Warning";
            }
            return action.payload;
        }
        case NotificationActions.SHOW_INFO: {
            action.payload.type = NotificationType.INFO;
            if(action.payload.header == null || action.payload.header == "") {
                action.payload.header = "Information";
            }
            return action.payload;
        }
        case NotificationActions.SHOW_SUCCESS: {
            action.payload.type = NotificationType.SUCCESS;
            if(action.payload.header == null || action.payload.header == "") {
                action.payload.header = "Success";
            }
            return action.payload;
        }
        default: {
            return state;
        }
    }
}