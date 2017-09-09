import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Job e2e test', () => {

    let navBarPage: NavBarPage;
    let jobDialogPage: JobDialogPage;
    let jobComponentsPage: JobComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Jobs', () => {
        navBarPage.goToEntity('job-my-suffix');
        jobComponentsPage = new JobComponentsPage();
        expect(jobComponentsPage.getTitle()).toMatch(/armoryApp.job.home.title/);

    });

    it('should load create Job dialog', () => {
        jobComponentsPage.clickOnCreateButton();
        jobDialogPage = new JobDialogPage();
        expect(jobDialogPage.getModalTitle()).toMatch(/armoryApp.job.home.createOrEditLabel/);
        jobDialogPage.close();
    });

    it('should create and save Jobs', () => {
        jobComponentsPage.clickOnCreateButton();
        jobDialogPage.setJobIdInput('5');
        expect(jobDialogPage.getJobIdInput()).toMatch('5');
        jobDialogPage.setJobTitleInput('jobTitle');
        expect(jobDialogPage.getJobTitleInput()).toMatch('jobTitle');
        jobDialogPage.setOnetCodeInput('5');
        expect(jobDialogPage.getOnetCodeInput()).toMatch('5');
        jobDialogPage.setJobDescriptionInput('jobDescription');
        expect(jobDialogPage.getJobDescriptionInput()).toMatch('jobDescription');
        jobDialogPage.setMinSalaryInput('5');
        expect(jobDialogPage.getMinSalaryInput()).toMatch('5');
        jobDialogPage.setMaxSalaryInput('5');
        expect(jobDialogPage.getMaxSalaryInput()).toMatch('5');
        jobDialogPage.employeeSelectLastOption();
        // jobDialogPage.taskSelectLastOption();
        jobDialogPage.save();
        expect(jobDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class JobComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-job-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class JobDialogPage {
    modalTitle = element(by.css('h4#myJobLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    jobIdInput = element(by.css('input#field_jobId'));
    jobTitleInput = element(by.css('input#field_jobTitle'));
    onetCodeInput = element(by.css('input#field_onetCode'));
    jobDescriptionInput = element(by.css('input#field_jobDescription'));
    minSalaryInput = element(by.css('input#field_minSalary'));
    maxSalaryInput = element(by.css('input#field_maxSalary'));
    employeeSelect = element(by.css('select#field_employee'));
    taskSelect = element(by.css('select#field_task'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setJobIdInput = function (jobId) {
        this.jobIdInput.sendKeys(jobId);
    }

    getJobIdInput = function () {
        return this.jobIdInput.getAttribute('value');
    }

    setJobTitleInput = function (jobTitle) {
        this.jobTitleInput.sendKeys(jobTitle);
    }

    getJobTitleInput = function () {
        return this.jobTitleInput.getAttribute('value');
    }

    setOnetCodeInput = function (onetCode) {
        this.onetCodeInput.sendKeys(onetCode);
    }

    getOnetCodeInput = function () {
        return this.onetCodeInput.getAttribute('value');
    }

    setJobDescriptionInput = function (jobDescription) {
        this.jobDescriptionInput.sendKeys(jobDescription);
    }

    getJobDescriptionInput = function () {
        return this.jobDescriptionInput.getAttribute('value');
    }

    setMinSalaryInput = function (minSalary) {
        this.minSalaryInput.sendKeys(minSalary);
    }

    getMinSalaryInput = function () {
        return this.minSalaryInput.getAttribute('value');
    }

    setMaxSalaryInput = function (maxSalary) {
        this.maxSalaryInput.sendKeys(maxSalary);
    }

    getMaxSalaryInput = function () {
        return this.maxSalaryInput.getAttribute('value');
    }

    employeeSelectLastOption = function () {
        this.employeeSelect.all(by.tagName('option')).last().click();
    }

    employeeSelectOption = function (option) {
        this.employeeSelect.sendKeys(option);
    }

    getEmployeeSelect = function () {
        return this.employeeSelect;
    }

    getEmployeeSelectedOption = function () {
        return this.employeeSelect.element(by.css('option:checked')).getText();
    }

    taskSelectLastOption = function () {
        this.taskSelect.all(by.tagName('option')).last().click();
    }

    taskSelectOption = function (option) {
        this.taskSelect.sendKeys(option);
    }

    getTaskSelect = function () {
        return this.taskSelect;
    }

    getTaskSelectedOption = function () {
        return this.taskSelect.element(by.css('option:checked')).getText();
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
