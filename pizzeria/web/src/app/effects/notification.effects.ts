import { Injectable } from '@angular/core';
import { Effect, Actions } from '@ngrx/effects';
import { Observable } from 'rxjs/Observable'
import { NotificationActions } from '../../actions';

import { NotificationType } from '../models/notification/notification.model';

@Injectable()
export class NotificationEffects {
    constructor ( private _update$: Actions,
                  private _notificationActions: NotificationActions
) {}

@Effect() showError$ = this._update$
    .ofType(NotificationActions.SHOW_ERROR)
    .map(action => action.payload)
    .switchMap(payload => {
        if(payload.header == null || payload.header == "") {
            payload.header = "Error";
        }
        payload.type = NotificationType.ERROR;
        this._notificationActions.showError(payload);

        return Observable.empty();
    });

@Effect() showWarning$ = this._update$
    .ofType(NotificationActions.SHOW_WARNING)
    .map(action => action.payload)
    .switchMap(payload => {
        if(payload.header == null || payload.header == "") {
            payload.header = "Warning";
        }
        payload.type = NotificationType.WARNING;

        this._notificationActions.showWarning(payload);

        return Observable.empty();
    });

@Effect() showInfo$ = this._update$
    .ofType(NotificationActions.SHOW_INFO)
    .map(action => action.payload)
    .switchMap(payload => {
        if(payload.header == null || payload.header == "") {
            payload.header = "Info";
        }
        payload.type = NotificationType.INFO;
        this._notificationActions.showInfo(payload);

        return Observable.empty();
    });

@Effect() showSuccess$ = this._update$
    .ofType(NotificationActions.SHOW_SUCCESS)
    .map(action => action.payload)
    .switchMap(payload => {
        if(payload.header == null || payload.header == "") {
            payload.header = "Success";
        }
        payload.type = NotificationType.SUCCESS;
        this._notificationActions.showSuccess(payload);

        return Observable.empty();
    });
}