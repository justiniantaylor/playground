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

import { Menu,
         MenuItem } from '../../../models';

@Component({
    selector: 'menu-item-detail',
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
        <h4 class="card-title">Menu Item</h4>
        <h6 class="card-subtitle mb-2 text-muted" *ngIf="menuItem.id">Editing Menu Item</h6>
        <h6 class="card-subtitle mb-2 text-muted" *ngIf="!menuItem.id">New Menu Item</h6>

        <form [formGroup]="menuItemForm" (ngSubmit)="save(menuItemForm.value)" novalidate>
            <input type="hidden" name="id" formControlName="id">
               
            <div class="form-group">
                <label for="">Code</label>
                <input type="text" class="form-control" formControlName="code">
            
                <small [hidden]="menuItemForm.controls.code.valid || menuItemForm.controls.code.pristine" class="text-danger">
                    Code is required (maximum 50 characters).
                </small>
            </div>
            <div class="form-group">
                <label for="">Description</label>
                <input type="text" class="form-control" formControlName="description">
            
                <small [hidden]="menuItemForm.controls.description.valid || menuItemForm.controls.description.pristine" class="text-danger">
                    Description is required (maximum 50 characters).
                </small>
            </div>
            <div class="form-group">
                <label for="">Unit Price In Cents</label>
                <input type="number" class="form-control" formControlName="unitPriceInCents">
            
                <small [hidden]="menuItemForm.controls.unitPriceInCents.valid || menuItemForm.controls.unitPriceInCents.pristine" class="text-danger">
                    Unit Price is required.
                </small>
            </div>
            <div class="form-group">
                <label for="">Available</label>
                <input type="number" class="form-control" formControlName="available">
            
                <small [hidden]="menuItemForm.controls.available.valid || menuItemForm.controls.available.pristine" class="text-danger">
                   Available is required.
                </small>
            </div>
            <button type="submit" class="btn btn-primary" [disabled]="!menuItemForm.valid">{{menuItem.id ? "Update" : "Create"}}</button>
            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#confirm-cancel-item-dialog">Cancel</button>
        </form>       
    </div>
</div>
`
})
export class MenuItemDetailComponent implements OnChanges {
    @Input() menuItem: MenuItem;
    @Input() menuItems: MenuItem[];

    @Output() savedEvent = new EventEmitter();
    @Output() cancelledEvent = new EventEmitter();

    menuItemForm: FormGroup;
    submitted: boolean;

    constructor(private _fb: FormBuilder) { }

    ngOnChanges(changes: SimpleChanges): void {
        this.menuItemForm = this._fb.group({
            "id": ["", []],
            "code": ["", [<any>Validators.required, <any>Validators.maxLength(50)]],
            "description": ["", [<any>Validators.required, <any>Validators.maxLength(50)]],
            "unitPriceInCents": ["", []],
            "available": ["", [<any>Validators.required]],
            "menuId": ["", [<any>Validators.required]]
        });
        this.reset();
    }

    reset() {
        if (this.menuItem != null) {
            this.menuItemForm.reset({
                id: this.menuItem.id,
                code: this.menuItem.code,
                description: this.menuItem.description,
                unitPriceInCents: this.menuItem.unitPriceInCents,
                available: this.menuItem.available,
                menuId: this.menuItem.menuId
            });
        }
    }

    save(model: MenuItem): void {
        this.submitted = true;
        this.savedEvent.emit(model);
    }

    cancel(): void {
        this.submitted = false;
        this.menuItemForm.reset();
        this.cancelledEvent.emit();
    }

    doNotCancel() { }
}