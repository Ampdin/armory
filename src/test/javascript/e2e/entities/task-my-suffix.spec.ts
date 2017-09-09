import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Task e2e test', () => {

    let navBarPage: NavBarPage;
    let taskDialogPage: TaskDialogPage;
    let taskComponentsPage: TaskComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Tasks', () => {
        navBarPage.goToEntity('task-my-suffix');
        taskComponentsPage = new TaskComponentsPage();
        expect(taskComponentsPage.getTitle()).toMatch(/armoryApp.task.home.title/);

    });

    it('should load create Task dialog', () => {
        taskComponentsPage.clickOnCreateButton();
        taskDialogPage = new TaskDialogPage();
        expect(taskDialogPage.getModalTitle()).toMatch(/armoryApp.task.home.createOrEditLabel/);
        taskDialogPage.close();
    });

    it('should create and save Tasks', () => {
        taskComponentsPage.clickOnCreateButton();
        taskDialogPage.setTaskIDInput('taskID');
        expect(taskDialogPage.getTaskIDInput()).toMatch('taskID');
        taskDialogPage.setTaskNameInput('taskName');
        expect(taskDialogPage.getTaskNameInput()).toMatch('taskName');
        taskDialogPage.setScaleIDInput('scaleID');
        expect(taskDialogPage.getScaleIDInput()).toMatch('scaleID');
        taskDialogPage.setScaleNameInput('scaleName');
        expect(taskDialogPage.getScaleNameInput()).toMatch('scaleName');
        taskDialogPage.setDataValueInput('5');
        expect(taskDialogPage.getDataValueInput()).toMatch('5');
        taskDialogPage.setNInput('5');
        expect(taskDialogPage.getNInput()).toMatch('5');
        taskDialogPage.setStandardErrorInput('5');
        expect(taskDialogPage.getStandardErrorInput()).toMatch('5');
        taskDialogPage.setLowerClBoundInput('5');
        expect(taskDialogPage.getLowerClBoundInput()).toMatch('5');
        taskDialogPage.setUpperClBoundInput('5');
        expect(taskDialogPage.getUpperClBoundInput()).toMatch('5');
        taskDialogPage.setRecommendSuppressInput('recommendSuppress');
        expect(taskDialogPage.getRecommendSuppressInput()).toMatch('recommendSuppress');
        taskDialogPage.setNotRelevantInput('notRelevant');
        expect(taskDialogPage.getNotRelevantInput()).toMatch('notRelevant');
        taskDialogPage.setDateInput(12310020012301);
        expect(taskDialogPage.getDateInput()).toMatch('2001-12-31T02:30');
        taskDialogPage.setDomainSourceInput('domainSource');
        expect(taskDialogPage.getDomainSourceInput()).toMatch('domainSource');
        taskDialogPage.setDescriptionInput('description');
        expect(taskDialogPage.getDescriptionInput()).toMatch('description');
        taskDialogPage.save();
        expect(taskDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class TaskComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-task-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TaskDialogPage {
    modalTitle = element(by.css('h4#myTaskLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    taskIDInput = element(by.css('input#field_taskID'));
    taskNameInput = element(by.css('input#field_taskName'));
    scaleIDInput = element(by.css('input#field_scaleID'));
    scaleNameInput = element(by.css('input#field_scaleName'));
    dataValueInput = element(by.css('input#field_dataValue'));
    nInput = element(by.css('input#field_n'));
    standardErrorInput = element(by.css('input#field_standardError'));
    lowerClBoundInput = element(by.css('input#field_lowerClBound'));
    upperClBoundInput = element(by.css('input#field_upperClBound'));
    recommendSuppressInput = element(by.css('input#field_recommendSuppress'));
    notRelevantInput = element(by.css('input#field_notRelevant'));
    dateInput = element(by.css('input#field_date'));
    domainSourceInput = element(by.css('input#field_domainSource'));
    descriptionInput = element(by.css('input#field_description'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTaskIDInput = function (taskID) {
        this.taskIDInput.sendKeys(taskID);
    }

    getTaskIDInput = function () {
        return this.taskIDInput.getAttribute('value');
    }

    setTaskNameInput = function (taskName) {
        this.taskNameInput.sendKeys(taskName);
    }

    getTaskNameInput = function () {
        return this.taskNameInput.getAttribute('value');
    }

    setScaleIDInput = function (scaleID) {
        this.scaleIDInput.sendKeys(scaleID);
    }

    getScaleIDInput = function () {
        return this.scaleIDInput.getAttribute('value');
    }

    setScaleNameInput = function (scaleName) {
        this.scaleNameInput.sendKeys(scaleName);
    }

    getScaleNameInput = function () {
        return this.scaleNameInput.getAttribute('value');
    }

    setDataValueInput = function (dataValue) {
        this.dataValueInput.sendKeys(dataValue);
    }

    getDataValueInput = function () {
        return this.dataValueInput.getAttribute('value');
    }

    setNInput = function (n) {
        this.nInput.sendKeys(n);
    }

    getNInput = function () {
        return this.nInput.getAttribute('value');
    }

    setStandardErrorInput = function (standardError) {
        this.standardErrorInput.sendKeys(standardError);
    }

    getStandardErrorInput = function () {
        return this.standardErrorInput.getAttribute('value');
    }

    setLowerClBoundInput = function (lowerClBound) {
        this.lowerClBoundInput.sendKeys(lowerClBound);
    }

    getLowerClBoundInput = function () {
        return this.lowerClBoundInput.getAttribute('value');
    }

    setUpperClBoundInput = function (upperClBound) {
        this.upperClBoundInput.sendKeys(upperClBound);
    }

    getUpperClBoundInput = function () {
        return this.upperClBoundInput.getAttribute('value');
    }

    setRecommendSuppressInput = function (recommendSuppress) {
        this.recommendSuppressInput.sendKeys(recommendSuppress);
    }

    getRecommendSuppressInput = function () {
        return this.recommendSuppressInput.getAttribute('value');
    }

    setNotRelevantInput = function (notRelevant) {
        this.notRelevantInput.sendKeys(notRelevant);
    }

    getNotRelevantInput = function () {
        return this.notRelevantInput.getAttribute('value');
    }

    setDateInput = function (date) {
        this.dateInput.sendKeys(date);
    }

    getDateInput = function () {
        return this.dateInput.getAttribute('value');
    }

    setDomainSourceInput = function (domainSource) {
        this.domainSourceInput.sendKeys(domainSource);
    }

    getDomainSourceInput = function () {
        return this.domainSourceInput.getAttribute('value');
    }

    setDescriptionInput = function (description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function () {
        return this.descriptionInput.getAttribute('value');
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
