import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule,
         ReactiveFormsModule } from '@angular/forms';
import { Select2Module } from 'ng2-select2';

import { OrdersRoutingModule} from './orders-routing.module';

import { OrdersComponent } from './orders.component';
import { OrderDetailComponent } from './order-detail.component';
import { OrderListComponent } from './order-list.component';

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
        OrdersComponent
    ],
    exports: [
        OrderDetailComponent,
        OrderListComponent,
        OrdersComponent
    ],
    providers: []
})
export class OrdersModule { }
