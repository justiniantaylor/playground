import { Component,
         Input,
         Output,
         EventEmitter,
         ChangeDetectionStrategy } from '@angular/core';

import { OrderItem } from '../../../models';

@Component({
    selector: 'order-item-list',
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
                <p>Are you sure you want to delete this order item?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal" (click)="deleteOrderItem()">OK</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal" (click)="doNotDeleteOrderItem()">Cancel</button>
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
        <tr *ngFor="let orderItem of orderItems"
             (click)="toggleSelected(orderItem.id);selectedEvent.emit(orderItem)" 
             class="clickable"
             [ngClass]="{'selected': selectedId === orderItem.id}">
        
            <td>{{orderItem.id}}</td>
            <td>{{orderItem.code}}</td>
            <td>{{orderItem.description}}</td>
            <td>{{orderItem.unitPriceInCents}}</td>
            <td>{{orderItem.available}}</td>
            <td><button class="btn btn-sm btn-danger" data-toggle="modal" data-target="#confirm-delete-item-dialog"><i class="fa fa-fw fa-trash"></i></button></td>
        </tr>
    </tbody>
</table>
`
})
export class OrderItemListComponent {

    @Input() orderItems: OrderItem[];
    @Input() selectedOrderItem: OrderItem;
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

    deleteOrderItem(){
        this.deletedEvent.emit(this.selectedOrderItem)
    }

    doNotDeleteOrderItem(){}
}