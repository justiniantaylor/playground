import { NgModule } from '@angular/core';
import { RouterModule,
         Routes } from '@angular/router';

import { MenusComponent } from './menus.component';

const menusRoutes: Routes = [
    {
        path: 'menu',
        children: [
            {
                path: '',
                component: MenusComponent
            },
            {
                path: ':id',
                component: MenusComponent
            }
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(menusRoutes)
    ],
    exports: [
        RouterModule
    ]
})
export class MenusRoutingModule {}