import { Component,
         Input,
         Output,
         EventEmitter,
         ChangeDetectionStrategy } from '@angular/core';

import { MenuItem } from '../../../models';

@Component({
    selector: 'menu-item-list',
    changeDetection: ChangeDetectionStrategy.OnPush,
    template:
`
<div class="modal fade" id="confirm-delete-item-dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Confirmation</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" (click)="cancelEvent.emit()">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>Are you sure you want to delete this menu item?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal" (click)="deleteMenuItem()">OK</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal" (click)="doNotDeleteMenuItem()">Cancel</button>
            </div>
        </div>
    </div>
</div>

<table class="table">
    <thead>
        <tr>
            <th>ID</th>
            <th>Code</th>
            <th>Unit Price in Cents</th>
            <th>Available</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <tr *ngFor="let menuItem of menuItems"
             (click)="toggleSelected(menuItem.id);selectedEvent.emit(menuItem)" 
             class="clickable"
             [ngClass]="{'selected': selectedId === menuItem.id}">
        
            <td>{{menuItem.id}}</td>
            <td>{{menuItem.code}}</td>
            <td>{{menuItem.description}}</td>
            <td>{{menuItem.unitPriceInCents}}</td>
            <td>{{menuItem.available}}</td>
            <td><button class="btn btn-sm btn-danger" data-toggle="modal" data-target="#confirm-delete-item-dialog"><i class="fa fa-fw fa-trash"></i></button></td>
        </tr>
    </tbody>
</table>
`
})
export class MenuItemListComponent {

    @Input() menuItems: MenuItem[];
    @Input() selectedMenuItem: MenuItem;
    selectedId: number;

    @Output() selectedEvent = new EventEmitter();
    @Output() deletedEvent = new EventEmitter();

    toggleSelected(newValue: number) {
        if (this.selectedId === newValue) {
            this.selectedId = 0;
        }
        else {
            this.selectedId = newValue;
        }
    }

    deleteMenuItem(){
        this.deletedEvent.emit(this.selectedMenuItem)
    }

    doNotDeleteMenuItem(){}
}