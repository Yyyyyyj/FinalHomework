describe('add todo', function () {
    let page;

    before (async function () {
        const BASE_URL = "http://127.0.0.1:7001";
        page = await browser.newPage();
        await page.goto("${BASE_URL}");
    });

    after (async function () {
        await page.close();
    });

    testTitle('should have correct title', async function() {
        expect(await page.title()).to.eql("Koa • Todo");
    })
    testAddTodoItem('should new todo correct', async function() {
        await page.click('#new-todo', {delay: 1000});
        await page.type('#new-todo', 'create a todo item', {delay: 1000});
        await page.keyboard.press("Enter");
        let todoList = await page.waitFor('#todo-list');
        const expectInputContent = await page.evaluate(todoList => todoList.lastChild.querySelector('label').textContent, todoList);
        expect(expectInputContent).to.eql('create a todo item');
    })

});