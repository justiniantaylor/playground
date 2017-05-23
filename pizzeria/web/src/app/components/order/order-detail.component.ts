import { Component,
         Input,
         Output,
         OnChanges,
         SimpleChanges,
         EventEmitter,
         ChangeDetectionStrategy } from '@angular/core';
import { FormGroup,
         Validators,
         FormBuilder } from '@angular/forms';

import { Order } from '../../models';

@Component({
    selector: 'order-detail',
    changeDetection: ChangeDetectionStrategy.OnPush,
    template: `
<div class="modal fade" id="confirm-cancel-dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Confirmation</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" (click)="cancelEvent.emit()">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>Are you sure you want to cancel your changes?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal" (click)="cancel()">OK</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal" (click)="doNotCancel()">Cancel</button>
            </div>
        </div>
    </div>
</div>

<div class="card">
    <div class="card-block">
        <h4 class="card-title">Order</h4>
        <h6 class="card-subtitle mb-2 text-muted" *ngIf="order.id">Editing Order</h6>
        <h6 class="card-subtitle mb-2 text-muted" *ngIf="!order.id">New Order</h6>

        <form [formGroup]="orderForm" (ngSubmit)="save(orderForm.value)" novalidate>
            <input type="hidden" name="id" formControlName="id">
            <input type="hidden" name="orderedDate" formControlName="orderedDate">
            <input type="hidden" name="fulfilledDate" formControlName="fulfilledDate">
    
            <div class="form-group">
                <label for="">Deliver?</label>
                <input type="text" class="form-control" formControlName="deliver">
            
                <small [hidden]="orderForm.controls.deliver.valid || orderForm.controls.deliver.pristine" class="text-danger">
                    Deliver is required.
                </small>
            </div>
            <div class="form-group">
                <label for="">Cashier</label>
                <input type="text" class="form-control" formControlName="cashierId">               
            </div>
            <div class="form-group">
                <label for="">Customer</label>
                <input type="text" class="form-control" formControlName="customerId">
            </div>
            <div class="form-group">
                <label for="">Address</label>
                <input type="text" class="form-control" formControlName="addressId">           
            </div>      
            <button type="submit" class="btn btn-primary" [disabled]="!orderForm.valid">{{order.id ? "Update" : "Create"}}</button>
            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#confirm-cancel-dialog">Cancel</button>
        </form>
    </div>
</div>
`
})
export class OrderDetailComponent implements OnChanges {
    
    @Input() order: Order;

    @Output() savedEvent = new EventEmitter();
    @Output() cancelledEvent = new EventEmitter();

    orderForm: FormGroup;
    submitted: boolean;

    constructor(private _fb: FormBuilder) { }

    ngOnChanges(changes: SimpleChanges): void {
        this.orderForm = this._fb.group({
            "id": ["", []],
            "deliver": ["", [<any>Validators.required]],
            "cashierId": ["", []],
            "customerId": ["", []],
            "addressId": ["", []],
            "orderedDate": ["", []],
            "fulfilledDate": ["", []]
        });

        this.reset();
    }

    reset() {
        if (this.order != null) {
            this.orderForm.reset({
                id: this.order.id,
                deliver: this.order.deliver,
                cashierId: this.order.cashierId,
                customerId: this.order.customerId,
                addressId: this.order.addressId,
                orderedDate: this.order.orderedDate,
                fulfilledDate: this.order.fulfilledDate
            });
        }
    }

    save(model: Order):void {
        this.submitted = true;
        this.savedEvent.emit(model);
    }

    cancel():void {
        this.submitted = false;
        this.orderForm.reset();
        this.cancelledEvent.emit();
    }

    doNotCancel(){}
}