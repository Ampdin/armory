import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { DepartmentMySuffix } from './department-my-suffix.model';
import { DepartmentMySuffixService } from './department-my-suffix.service';

@Component({
    selector: 'jhi-department-my-suffix-detail',
    templateUrl: './department-my-suffix-detail.component.html'
})
export class DepartmentMySuffixDetailComponent implements OnInit, OnDestroy {

    department: DepartmentMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private departmentService: DepartmentMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDepartments();
    }

    load(id) {
        this.departmentService.find(id).subscribe((department) => {
            this.department = department;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDepartments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'departmentListModification',
            (response) => this.load(this.department.id)
        );
    }
}
