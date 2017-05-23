import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { Notification,
         NotificationType} from '../models/notification.model';

@Injectable()
export class ErrorService {

    handleError(response: Response | any) {

        let notification: Notification = { status: null,
                                           description: '',
                                           type: NotificationType,
                                           header: '',
                                           message: ''
        };
        notification.status = response.status;

        if (response._body != "" && response._body != null) {

            const body = response.json() || '';
            if (body.messages != null) {
                for (let e of body.messages) {
                    notification.message = `${notification.message || ''}${e} `;
                }
            } else {
                notification.message = "Unknown Error"
            }
            notification.description = response.message ? response.message : response.toString();
        } else {
            notification.message = response.message ? response.message : response.toString();
            notification.description = response.message ? response.message : response.toString();
        }

        return Observable.throw(notification);
    }
}