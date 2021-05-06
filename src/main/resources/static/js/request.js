// Отображение заявки

$('tbody').on('click', '.buy', function () {
    let cc = $(this).attr("rateCc");
    console.log(cc);
    fetch(baseUrl + "/" + cc, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => response.json())
        .then(json => {
            document.getElementById("calculateForm").hidden = false;
            document.querySelector('#buyCc').value = json.cc;
        })
})

const baseUrlRequest = "http://localhost:8080/api/v1/exchanger/request";

document.querySelector("#calculate")
    .addEventListener('click', event => {
        let ccValue = document.querySelector('#buyCc');
        let actionValue = document.querySelector('#act');
        let sumValue = document.querySelector('#sumBuy');
        let phoneNumberValue = document.querySelector('#phone');
        const request = {
            cc: ccValue.value,
            action: actionValue.value,
            sumCurrency: sumValue.value,
            phoneNumber: phoneNumberValue.value
        };
        fetch(baseUrlRequest, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(request)
        }).then(function (response) {
            if (response.status !== 200) {
                return Promise.reject(new Error())
            }
            return Promise.resolve(response)
        }).then(response => response.json())
            .then(json => {
                ccValue.value = sumValue.value = phoneNumberValue.value = '';
                document.getElementById("requestSave").hidden = false;
                displayRequest(json);
            })
            .catch(function () {
                alert('Заявка не сохранена, ошибка заполнения данных')
            })
    });


function displayRequest(request) {
    console.log("Request = " + request)
    const form = document.querySelector('#requestSave');
    form.innerHTML = '';
    let div = document.createElement('div');
    for (const prop in request) {
        if (prop === 'sumPayment') {
            let p = document.createElement('p');
            p.append(document.createTextNode("Сумма к оплате " + request[prop] + " грн."));
            p.className = 'sumPayment';
            div.append(p);
        } else if (prop === 'phoneNumber') {
            let p = document.createElement('p');
            p.append(document.createTextNode("СМС отправлено на номер " + request[prop]));
            p.className = prop;
            div.append(p);
        } else if (prop === 'id') {
            console.log(request[prop]);
            document.querySelector('#buyId').value = request[prop];
        }
    }
    let label = document.createElement('label');
    label.append(document.createTextNode('Код: '));
    let input = document.createElement('input');
    let buttonElement = document.createElement("button");
    buttonElement.className = "save";
    const text = document.createTextNode("Подтвердить заявку");
    buttonElement.append(text);
    div.append(label, input, buttonElement);
    form.append(div);
}

$('div').on('click', '.save', function () {
    const form = document.querySelector('#requestSave');
    let code = form.getElementsByTagName('input')[0].value
    let id = document.querySelector('#buyId').value;

    fetch(baseUrlRequest + "/" + id + "/" + code, {

        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(function (response) {
        if (response.status !== 200) {
            return Promise.reject(new Error())
        }
        return Promise.resolve(response)
    }).then(function() {
        alert('Ваша заявка успешно выполнена.')
    }).catch(function () {
        alert('Неверный код подтверждения, заявка отклонена.')
    }).finally(function () {
        document.getElementById("calculateForm").hidden = true;
        document.getElementById("requestSave").hidden = true;
        form.innerHTML = '';
    })
});