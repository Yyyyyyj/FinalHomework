describe('delete todo', function() {
    let page;

    before (async function () {
        const BASE_URL = "http://127.0.0.1:7001";
        page = await browser.newPage();
        await page.goto("${BASE_URL}");
    });

    after (async function () {
        await page.close();
    });

    testDelete('delete todo item', async function() {
        const todoListLength = await page.evaluate(() => {
            return document.getElementsByClassName('view').length;
        });

        const todoListLength1 = await page.evaluate(() => {
            return document.getElementsByClassName('view').length;
        });
        let todoCount = await page.waitFor('#delete');
        const diff = todoListLength - todoListLength1
        expect(diff).to.eql(1);
    })
})