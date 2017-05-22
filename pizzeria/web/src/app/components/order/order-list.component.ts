import { Component,
         Input,
         Output,
         EventEmitter,
         ChangeDetectionStrategy } from '@angular/core';

import { Order } from '../../models';

@Component({
    selector: 'order-list',
    changeDetection: ChangeDetectionStrategy.OnPush,
    template:
`
<div class="modal fade" id="confirm-delete-dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Confirmation</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close" (click)="cancelEvent.emit()">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p>Are you sure you want to delete this order?</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal" (click)="deleteOrder()">OK</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal" (click)="doNotDeleteOrder()">Cancel</button>
      </div>
    </div>
  </div>
</div>

<table class="table">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Code</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <tr *ngFor="let order of orders"
             (click)="toggleSelected(order.id);selectedEvent.emit(order)" 
             class="clickable"
             [class.selected]="order.id === selectedOrder.id" 
             [ngClass]="{'selected': selectedId === order.id}">
        
            <td>{{order.id}}</td>
            <td>{{order.name}}</td>
            <td>{{order.code}}</td>
            <td><button class="btn btn-sm btn-danger" data-toggle="modal" data-target="#confirm-delete-dialog"><i class="fa fa-fw fa-trash"></i></button></td>
        </tr>
    </tbody>
<table>
`
})
export class OrderListComponent {

    @Input() orders: Order[];
    @Input() selectedOrder: Order;
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

    deleteOrder(){
        this.deletedEvent.emit(this.selectedOrder)
    }

    doNotDeleteOrder(){}
}