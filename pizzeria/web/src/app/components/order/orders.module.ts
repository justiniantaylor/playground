import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule,
         ReactiveFormsModule } from '@angular/forms';
import { Select2Module } from 'ng2-select2';

import { OrdersRoutingModule} from './orders-routing.module';

import { OrdersComponent } from './orders.component';
import { OrderDetailComponent } from './order-detail.component';
import { OrderListComponent } from './order-list.component';
import { OrderItemDetailComponent } from './item/order-item-detail.component';
import { OrderItemListComponent } from './item/order-item-list.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        Select2Module,

        OrdersRoutingModule
    ],
    declarations: [
        OrderDetailComponent,
        OrderListComponent,
        OrderItemDetailComponent,
        OrderItemListComponent,
        OrdersComponent
    ],
    exports: [
        OrderDetailComponent,
        OrderListComponent,
        OrderItemDetailComponent,
        OrderItemListComponent,
        OrdersComponent
    ],
    providers: []
})
export class OrdersModule { }
