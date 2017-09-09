import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Book e2e test', () => {

    let navBarPage: NavBarPage;
    let bookDialogPage: BookDialogPage;
    let bookComponentsPage: BookComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Books', () => {
        navBarPage.goToEntity('book-my-suffix');
        bookComponentsPage = new BookComponentsPage();
        expect(bookComponentsPage.getTitle()).toMatch(/armoryApp.book.home.title/);

    });

    it('should load create Book dialog', () => {
        bookComponentsPage.clickOnCreateButton();
        bookDialogPage = new BookDialogPage();
        expect(bookDialogPage.getModalTitle()).toMatch(/armoryApp.book.home.createOrEditLabel/);
        bookDialogPage.close();
    });

    it('should create and save Books', () => {
        bookComponentsPage.clickOnCreateButton();
        bookDialogPage.setBookIdInput('5');
        expect(bookDialogPage.getBookIdInput()).toMatch('5');
        bookDialogPage.setBookNameInput('bookName');
        expect(bookDialogPage.getBookNameInput()).toMatch('bookName');
        bookDialogPage.setBookPriceInput('5');
        expect(bookDialogPage.getBookPriceInput()).toMatch('5');
        bookDialogPage.setPublisherInput('publisher');
        expect(bookDialogPage.getPublisherInput()).toMatch('publisher');
        bookDialogPage.languageSelectLastOption();
        bookDialogPage.setIsbn10Input('isbn10');
        expect(bookDialogPage.getIsbn10Input()).toMatch('isbn10');
        bookDialogPage.setIsbn13Input('isbn13');
        expect(bookDialogPage.getIsbn13Input()).toMatch('isbn13');
        bookDialogPage.setProductDimensionsInput('productDimensions');
        expect(bookDialogPage.getProductDimensionsInput()).toMatch('productDimensions');
        bookDialogPage.setShippingWeightInput('shippingWeight');
        expect(bookDialogPage.getShippingWeightInput()).toMatch('shippingWeight');
        bookDialogPage.setRankingInput('ranking');
        expect(bookDialogPage.getRankingInput()).toMatch('ranking');
        bookDialogPage.setAverageRankingInput('averageRanking');
        expect(bookDialogPage.getAverageRankingInput()).toMatch('averageRanking');
        bookDialogPage.setAuthorInput('author');
        expect(bookDialogPage.getAuthorInput()).toMatch('author');
        bookDialogPage.setSubjectInput('subject');
        expect(bookDialogPage.getSubjectInput()).toMatch('subject');
        bookDialogPage.setBookDescriptionInput('bookDescription');
        expect(bookDialogPage.getBookDescriptionInput()).toMatch('bookDescription');
        bookDialogPage.save();
        expect(bookDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BookComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-book-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BookDialogPage {
    modalTitle = element(by.css('h4#myBookLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    bookIdInput = element(by.css('input#field_bookId'));
    bookNameInput = element(by.css('input#field_bookName'));
    bookPriceInput = element(by.css('input#field_bookPrice'));
    publisherInput = element(by.css('input#field_publisher'));
    languageSelect = element(by.css('select#field_language'));
    isbn10Input = element(by.css('input#field_isbn10'));
    isbn13Input = element(by.css('input#field_isbn13'));
    productDimensionsInput = element(by.css('input#field_productDimensions'));
    shippingWeightInput = element(by.css('input#field_shippingWeight'));
    rankingInput = element(by.css('input#field_ranking'));
    averageRankingInput = element(by.css('input#field_averageRanking'));
    authorInput = element(by.css('input#field_author'));
    subjectInput = element(by.css('input#field_subject'));
    bookDescriptionInput = element(by.css('input#field_bookDescription'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setBookIdInput = function (bookId) {
        this.bookIdInput.sendKeys(bookId);
    }

    getBookIdInput = function () {
        return this.bookIdInput.getAttribute('value');
    }

    setBookNameInput = function (bookName) {
        this.bookNameInput.sendKeys(bookName);
    }

    getBookNameInput = function () {
        return this.bookNameInput.getAttribute('value');
    }

    setBookPriceInput = function (bookPrice) {
        this.bookPriceInput.sendKeys(bookPrice);
    }

    getBookPriceInput = function () {
        return this.bookPriceInput.getAttribute('value');
    }

    setPublisherInput = function (publisher) {
        this.publisherInput.sendKeys(publisher);
    }

    getPublisherInput = function () {
        return this.publisherInput.getAttribute('value');
    }

    setLanguageSelect = function (language) {
        this.languageSelect.sendKeys(language);
    }

    getLanguageSelect = function () {
        return this.languageSelect.element(by.css('option:checked')).getText();
    }

    languageSelectLastOption = function () {
        this.languageSelect.all(by.tagName('option')).last().click();
    }
    setIsbn10Input = function (isbn10) {
        this.isbn10Input.sendKeys(isbn10);
    }

    getIsbn10Input = function () {
        return this.isbn10Input.getAttribute('value');
    }

    setIsbn13Input = function (isbn13) {
        this.isbn13Input.sendKeys(isbn13);
    }

    getIsbn13Input = function () {
        return this.isbn13Input.getAttribute('value');
    }

    setProductDimensionsInput = function (productDimensions) {
        this.productDimensionsInput.sendKeys(productDimensions);
    }

    getProductDimensionsInput = function () {
        return this.productDimensionsInput.getAttribute('value');
    }

    setShippingWeightInput = function (shippingWeight) {
        this.shippingWeightInput.sendKeys(shippingWeight);
    }

    getShippingWeightInput = function () {
        return this.shippingWeightInput.getAttribute('value');
    }

    setRankingInput = function (ranking) {
        this.rankingInput.sendKeys(ranking);
    }

    getRankingInput = function () {
        return this.rankingInput.getAttribute('value');
    }

    setAverageRankingInput = function (averageRanking) {
        this.averageRankingInput.sendKeys(averageRanking);
    }

    getAverageRankingInput = function () {
        return this.averageRankingInput.getAttribute('value');
    }

    setAuthorInput = function (author) {
        this.authorInput.sendKeys(author);
    }

    getAuthorInput = function () {
        return this.authorInput.getAttribute('value');
    }

    setSubjectInput = function (subject) {
        this.subjectInput.sendKeys(subject);
    }

    getSubjectInput = function () {
        return this.subjectInput.getAttribute('value');
    }

    setBookDescriptionInput = function (bookDescription) {
        this.bookDescriptionInput.sendKeys(bookDescription);
    }

    getBookDescriptionInput = function () {
        return this.bookDescriptionInput.getAttribute('value');
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
