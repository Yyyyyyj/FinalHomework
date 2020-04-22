describe('show todo', function() {
    let page;

    before (async function () {
        const BASE_URL = "http://127.0.0.1:7001";
        page = await browser.newPage();
        await page.goto("${BASE_URL}");
    });

    after (async function () {
        await page.close();
    });

    testShowTodolist('should show todo correct', async function() {
        const todoListLength = await page.evaluate(() => {
            return document.getElementsByClassName('view').length;
        });
        const doneListLength = await page.evaluate(() => {
            return document.getElementsByClassName('completed').length;
        });
        let todoCount = await page.waitFor('#todo-count');
        const count = await page.evaluate(todoCount => todoCount.querySelector('strong').textContent, todoCount)
        expect(todoListLength - doneListLength).to.eql(+count);
    })
})