const baseUrl = "http://localhost:8080/api/v1/exchanger";

fetch(baseUrl)
    .then(response => response.json())
    .then(json => {
        displayRates(json);
    });

function displayRates(currencyArr) {
    console.log('currencyArr = ' + currencyArr);
    const tbody = document.querySelector('#body');
    tbody.innerHTML = '';
    currencyArr.forEach(currency => {
        let tr = document.createElement('tr');
        for (const prop in currency) {
            if (prop !== 'id') {
                let td = document.createElement('td');
                td.append(document.createTextNode(currency[prop]));
                tr.append(td);
            }
        }
        tr.append(addButtonExchange(currency.cc));
        tbody.append(tr);
    });
}

function addButtonExchange(cc) {
    let td = document.createElement('td');
    let buttonElement = document.createElement("button");
    buttonElement.className = "buy";
    buttonElement.setAttribute("rateCc", cc);
    const text = document.createTextNode("Заявка на обмен " + cc);
    buttonElement.append(text);
    td.append(buttonElement)
    return td;
}