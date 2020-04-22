describe('done todo', function () {
    let page;

    before (async function () {
        const BASE_URL = "http://127.0.0.1:7001";
        page = await browser.newPage();
        await page.goto("${BASE_URL}");
    });

    after (async function () {
        await page.close();
    });

    testDone('should clicked correct', async function() {
        page.evaluate(() => {
            let elements = document.getElementsByClassName('toggle');
            elements[elements.length - 1].click();
            console.log(elements.length - 1);
        });
    })
    testDone('should done todo correct', async function() {
        let todoList = await page.waitFor('#todo-list');
        const expectStatus = await page.evaluate(todoList => todoList.lastChild.querySelector('input').checked, todoList);
        expect(expectStatus).to.eql(true);
    })
})