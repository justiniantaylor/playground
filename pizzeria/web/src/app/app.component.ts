import { Component,
         OnInit,
         ViewContainerRef } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Store } from '@ngrx/store';
import { ToastsManager } from 'ng2-toastr/ng2-toastr';
import { SlimLoadingBarService } from 'ng2-slim-loading-bar';

import { AppState } from './reducers';

import { Notification,
         NotificationType } from './models';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
    notification$: Observable<Notification>;

    constructor(private _store: Store<AppState>,
                private _slimLoadingBarService: SlimLoadingBarService,
                public toastr: ToastsManager,
                public vcr: ViewContainerRef) {
                this.toastr.setRootViewContainerRef(vcr);
    }

    ngOnInit(): void {
        this.notification$ = this._store.select('notification');
        this.notification$.subscribe(n => {
            this._slimLoadingBarService.complete();

            if (n.type == NotificationType.ERROR) {
                this.toastr.error(n.message, n.header);
            }
            if (n.type == NotificationType.WARNING) {
                this.toastr.warning(n.message, n.header);
            }
            if (n.type == NotificationType.INFO) {
                this.toastr.info(n.message, n.header);
            }
            if (n.type == NotificationType.SUCCESS) {
                this.toastr.success(n.message, n.header);
            }
        });
    }
}
