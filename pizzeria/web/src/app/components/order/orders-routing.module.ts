import { NgModule } from '@angular/core';
import { RouterModule,
    Routes } from '@angular/router';

import { OrdersComponent } from './orders.component';

const ordersRoutes: Routes = [
    {
        path: 'order',
        children: [
            {
                path: '',
                component: OrdersComponent
            },
            {
                path: ':id',
                component: OrdersComponent
            }
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(ordersRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class OrdersRoutingModule { }