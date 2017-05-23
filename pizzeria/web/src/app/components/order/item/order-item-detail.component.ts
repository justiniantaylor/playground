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
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

import { Order,
         OrderItem } from '../../../models';

@Component({
    selector: 'order-item-detail',
    changeDetection: ChangeDetectionStrategy.OnPush,
    template: `
<div class="modal fade" id="confirm-cancel-item-dialog">
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
        <h4 class="card-title">Order Item</h4>
        <h6 class="card-subtitle mb-2 text-muted" *ngIf="orderItem.id">Editing Order Item</h6>
        <h6 class="card-subtitle mb-2 text-muted" *ngIf="!orderItem.id">New Order Item</h6>

        <form [formGroup]="orderItemForm" (ngSubmit)="save(orderItemForm.value)" novalidate>
            <input type="hidden" name="id" formControlName="id">
            <input type="hidden" name="orderId" formControlName="orderId">
               
            <div class="form-group">
                <label for="">Code</label>
                <input type="text" class="form-control" formControlName="code">
            
                <small [hidden]="orderItemForm.controls.code.valid || orderItemForm.controls.code.pristine" class="text-danger">
                    Code is required (maximum 50 characters).
                </small>
            </div>
            <div class="form-group">
                <label for="">Code</label>
                <select class="form-control" formControlName="menuItem">
                    <option *ngFor="let menuItem of menuItems" [value]="menuItem">{{menuItem.description}}</option>
                </select>  
            </div>
            <div class="form-group">
                <label for="">Description</label>
                <input type="text" class="form-control" formControlName="description">
            
                <small [hidden]="orderItemForm.controls.description.valid || orderItemForm.controls.description.pristine" class="text-danger">
                    Description is required (maximum 50 characters).
                </small>
            </div>
            <div class="form-group">
                <label for="">Unit Price In Cents</label>
                <input type="number" class="form-control" formControlName="unitPriceInCents">
            
                <small [hidden]="orderItemForm.controls.unitPriceInCents.valid || orderItemForm.controls.unitPriceInCents.pristine" class="text-danger">
                    Unit Price is required.
                </small>
            </div>
            <button type="submit" class="btn btn-primary" [disabled]="!orderItemForm.valid">{{orderItem.id ? "Update" : "Create"}}</button>
            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#confirm-cancel-item-dialog">Cancel</button>
        </form>       
    </div>
</div>
`
})
export class OrderItemDetailComponent implements OnChanges {
    @Input() orderItem: OrderItem;
    @Input() menuItems: OrderItem[];
    
    @Output() savedEvent = new EventEmitter();
    @Output() cancelledEvent = new EventEmitter();

    orderItemForm: FormGroup;
    submitted: boolean;

    constructor(private _fb: FormBuilder) { }

    ngOnChanges(changes: SimpleChanges): void {
        this.orderItemForm = this._fb.group({
            "id": ["", []],
            "quantity": ["", [<any>Validators.required]],
            "unitPriceInCents": ["", []],
            "orderId": ["", [<any>Validators.required]],
            "menuItemId": ["", [<any>Validators.required]]
        });
        this.reset();
    }

    reset() {
        if (this.orderItem != null) {
            this.orderItemForm.reset({
                id: this.orderItem.id,
                quantity: this.orderItem.quantity,
                unitPriceInCents: this.orderItem.unitPriceInCents,
                orderId: this.orderItem.orderId,
                menuItemId: this.orderItem.menuItemId
            });
        }
    }

    save(model: OrderItem): void {
        this.submitted = true;
        this.savedEvent.emit(model);
    }

    cancel(): void {
        this.submitted = false;
        this.orderItemForm.reset();
        this.cancelledEvent.emit();
    }

    doNotCancel() { }
}