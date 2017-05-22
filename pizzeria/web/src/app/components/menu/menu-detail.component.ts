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

import { Menu } from '../../models';

@Component({
    selector: 'menu-detail',
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
    <h4 class="card-title">Menu</h4>
    <h6 class="card-subtitle mb-2 text-muted" *ngIf="menu.id">Editing Menu</h6>
    <h6 class="card-subtitle mb-2 text-muted" *ngIf="!menu.id">New Menu</h6>

    <form [formGroup]="menuForm" (ngSubmit)="save(menuForm.value)" novalidate>
        <input type="hidden" name="id" formControlName="id">
    
        <div class="form-group">
            <label for="">Start Date</label>
            <input type="text" class="form-control" formControlName="startDate">
            
            <small [hidden]="menuForm.controls.endDate.valid || menuForm.controls.startDate.pristine" class="text-danger">
                Start Date is required (maximum 50 characters).
            </small>
        </div>
        <div class="form-group">
            <label for="">End Date</label>
            <input type="text" class="form-control" formControlName="endDate">
            
            <small [hidden]="menuForm.controls.endDate.valid || menuForm.controls.endDate.pristine" class="text-danger">
                End Date is required (maximum 50 characters).
            </small>
        </div>
        <button type="submit" class="btn btn-primary" [disabled]="!menuForm.valid">{{menu.id ? "Update" : "Create"}}</button>
        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#confirm-cancel-dialog">Cancel</button>
    </form>
  </div>
</div>
`
})
export class MenuDetailComponent implements OnChanges {
    
    @Input() menu: Menu;

    @Output() savedEvent = new EventEmitter();
    @Output() cancelledEvent = new EventEmitter();

    menuForm: FormGroup;
    submitted: boolean;

    constructor(private _fb: FormBuilder) { }

    ngOnChanges(changes: SimpleChanges): void {
        this.menuForm = this._fb.group({
            "id": ["", []],
            "endDate": ["", [<any>Validators.required]],
            "startDate": ["", [<any>Validators.required]],
        });

        this.reset();
    }

    reset() {
        if (this.menu != null) {
            this.menuForm.reset({
                id: this.menu.id,
                endDate: this.menu.endDate,
                startDate: this.menu.startDate
            });
        }
    }

    save(model: Menu):void {
        this.submitted = true;
        this.savedEvent.emit(model);
    }

    cancel():void {
        this.submitted = false;
        this.menuForm.reset();
        this.cancelledEvent.emit();
    }

    doNotCancel(){}
}