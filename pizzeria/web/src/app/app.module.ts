import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { SlimLoadingBarModule } from 'ng2-slim-loading-bar';
import { ToastModule,
         ToastOptions } from 'ng2-toastr/ng2-toastr';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { MenusModule } from './components/menu/menus.module';
import { OrdersModule } from './components/order/orders.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { ToastrOptions,
         PageNotFoundComponent,
         NavbarComponent } from './shared';

import { ErrorService,
         MenuService,
         MenuItemService,
         OrderService,
         OrderItemService } from './services';

/* NGRX */
import reducer from './reducers';

import { NotificationActions,
         MenuActions,
         MenuItemActions,
         OrderActions,
         OrderItemActions } from './actions';

import { NotificationEffects,
         MenuEffects,
         MenuItemEffects,
         OrderEffects,
         OrderItemEffects } from './effects';

@NgModule({
    declarations: [
        AppComponent,
        PageNotFoundComponent,
        NavbarComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        HttpModule,
        SlimLoadingBarModule.forRoot(),
        ToastModule.forRoot(),
        ToastModule,
        NgbModule.forRoot(),

        StoreModule.provideStore(reducer),

        EffectsModule.run(NotificationEffects),
        EffectsModule.run(MenuEffects),
        EffectsModule.run(MenuItemEffects),
        EffectsModule.run(OrderEffects),
        EffectsModule.run(OrderItemEffects),

        MenusModule,
        OrdersModule,

        AppRoutingModule
    ],
    providers: [{ provide: ToastOptions, useClass: ToastrOptions },
        NotificationActions,
        ErrorService,
        MenuActions, MenuService,
        MenuItemActions, MenuItemService,
        OrderActions, OrderService,
        OrderItemActions, OrderItemService
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
